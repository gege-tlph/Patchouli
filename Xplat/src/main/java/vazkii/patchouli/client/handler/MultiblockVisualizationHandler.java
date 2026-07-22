package vazkii.patchouli.client.handler;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import org.joml.Matrix4f;

import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.client.base.ClientTicker;
import vazkii.patchouli.client.base.PersistentData.Bookmark;
import vazkii.patchouli.common.multiblock.StateMatcher;
import vazkii.patchouli.common.util.RotationUtil;
import vazkii.patchouli.mixin.client.AccessorMultiBufferSource;

import java.util.Collection;
import java.util.Map;
import java.util.SequencedMap;
import java.util.function.Function;

public final class MultiblockVisualizationHandler {
	public static final MultiblockVisualizationHandler INSTANCE = new MultiblockVisualizationHandler();

	private boolean hasMultiblock;
	private Bookmark bookmark;
	private IMultiblock multiblock;
	private Component name;
	private BlockPos pos;
	private boolean isAnchored;
	private Rotation facingRotation;
	private Function<BlockPos, BlockPos> offsetApplier;
	private int blocks, blocksDone, airFilled;
	private int timeComplete;
	private BlockState lookingState;
	private BlockPos lookingPos;
	private GhostBuffers buffers = null;

	public boolean hasMultiblock() {
		return hasMultiblock;
	}

	public void setHasMultiblock(boolean hasMultiblock) {
		this.hasMultiblock = hasMultiblock;
	}

	public Bookmark bookmark() {
		return bookmark;
	}

	public Component name() {
		return name;
	}

	public BlockState getLookingState() {
		return lookingState;
	}

	public BlockPos getLookingPos() {
		return lookingPos;
	}

	public int getTimeComplete() {
		return timeComplete;
	}

	public float getProgress() {
		return (float) blocksDone / Math.max(1, blocks);
	}

	public String getProgressString() {
		return blocksDone + "/" + blocks;
	}

	public boolean isComplete() {
		return blocksDone == blocks && airFilled > 0;
	}

	public void setMultiblock(IMultiblock multiblock, Component name, Bookmark bookmark, boolean flip) {
		setMultiblock(multiblock, name, bookmark, flip, pos -> pos);
	}

	public void setMultiblock(IMultiblock multiblock, Component name, Bookmark bookmark, boolean flip, Function<BlockPos, BlockPos> offsetApplier) {
		if (flip && hasMultiblock) {
			hasMultiblock = false;
		} else {
			this.multiblock = multiblock;
			this.name = name;
			this.bookmark = bookmark;
			this.offsetApplier = offsetApplier;
			pos = null;
			hasMultiblock = multiblock != null;
			isAnchored = false;
		}
	}

	public void onWorldRenderLast(PoseStack ms, Matrix4f pose) {
		if (hasMultiblock && multiblock != null) {
			renderMultiblock(Minecraft.getInstance().level, ms, pose);
		}
	}

	public void anchorTo(BlockPos target, Rotation rot) {
		pos = target;
		facingRotation = rot;
		isAnchored = true;
	}

	public InteractionResult onPlayerInteract(Player player, Level world, InteractionHand hand, BlockHitResult hit) {
		if (hasMultiblock && !isAnchored && player == Minecraft.getInstance().player) {
			anchorTo(hit.getBlockPos(), getRotation(player));
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

	public void onClientTick(Minecraft mc) {
		if (Minecraft.getInstance().level == null) {
			hasMultiblock = false;
		} else if (isAnchored && blocks == blocksDone && airFilled == 0) {
			timeComplete++;
			if (timeComplete == 14) {
				Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0F));
			}
		} else {
			timeComplete = 0;
		}
	}

	public void renderMultiblock(Level world, PoseStack ms, Matrix4f pose) {
		ms.mulPose(pose);
		Minecraft mc = Minecraft.getInstance();
		if (!isAnchored) {
			facingRotation = getRotation(mc.player);
			if (mc.hitResult instanceof BlockHitResult) {
				pos = ((BlockHitResult) mc.hitResult).getBlockPos();
			}
		} else if (pos.distToCenterSqr(mc.player.position()) > 64 * 64) {
			return;
		}

		if (pos == null) {
			return;
		}
		if (multiblock.isSymmetrical()) {
			facingRotation = Rotation.NONE;
		}

		EntityRenderDispatcher erd = mc.getEntityRenderDispatcher();
		double renderPosX = erd.camera.position().x();
		double renderPosY = erd.camera.position().y();
		double renderPosZ = erd.camera.position().z();
		ms.pushPose();
		ms.translate(-renderPosX, -renderPosY, -renderPosZ);

		if (buffers == null) {
			buffers = initBuffers(mc.renderBuffers().bufferSource());
		}

		BlockPos checkPos = null;
		if (mc.hitResult instanceof BlockHitResult blockRes) {
			checkPos = blockRes.getBlockPos().relative(blockRes.getDirection());
		}

		blocks = blocksDone = airFilled = 0;
		lookingState = null;
		lookingPos = checkPos;

		Pair<BlockPos, Collection<IMultiblock.SimulateResult>> sim = multiblock.simulate(world, getStartPos(), getFacingRotation(), true);
		for (IMultiblock.SimulateResult r : sim.getSecond()) {
			float alpha = 0.3F;
			if (r.getWorldPosition().equals(checkPos)) {
				lookingState = r.getStateMatcher().getDisplayedState(ClientTicker.ticksInGame);
				alpha = 0.6F + (float) (Math.sin(ClientTicker.total * 0.3F) + 1F) * 0.1F;
			}

			if (r.getStateMatcher() != StateMatcher.ANY) {
				boolean air = r.getStateMatcher() == StateMatcher.AIR;
				if (!air) {
					blocks++;
				}

				if (!r.test(world, facingRotation)) {
					BlockState renderState = r.getStateMatcher().getDisplayedState(ClientTicker.ticksInGame).rotate(facingRotation);
					renderBlock(world, renderState, r.getWorldPosition(), alpha, ms);

					if (air) {
						airFilled++;
					}
				} else if (!air) {
					blocksDone++;
				}
			}
		}

		buffers.endBatch();
		ms.popPose();

		if (!isAnchored) {
			blocks = blocksDone = 0;
		}
	}

	public void renderBlock(Level world, BlockState state, BlockPos pos, float alpha, PoseStack ms) {
		if (pos != null) {
			ms.pushPose();
			ms.translate(pos.getX(), pos.getY(), pos.getZ());

			if (state.getBlock() == Blocks.AIR) {
				float scale = 0.3F;
				float off = (1F - scale) / 2;
				ms.translate(off, off, -off);
				ms.scale(scale, scale, scale);

				state = Blocks.RED_CONCRETE.defaultBlockState();
			}

			buffers.setAlpha(alpha);
			Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, ms, buffers, 0xF000F0, OverlayTexture.NO_OVERLAY);

			ms.popPose();
		}
	}

	public IMultiblock getMultiblock() {
		return multiblock;
	}

	public boolean isAnchored() {
		return isAnchored;
	}

	public Rotation getFacingRotation() {
		return multiblock.isSymmetrical() ? Rotation.NONE : facingRotation;
	}

	public BlockPos getStartPos() {
		return offsetApplier.apply(pos);
	}

	/**
	 * Returns the Rotation of a multiblock structure based on the given entity's facing direction.
	 */
	private Rotation getRotation(Entity entity) {
		return RotationUtil.rotationFromFacing(entity.getDirection());
	}

	private GhostBuffers initBuffers(MultiBufferSource.BufferSource original) {
		ByteBufferBuilder fallback = ((AccessorMultiBufferSource) original).getFallbackBuffer();
		SequencedMap<RenderType, ByteBufferBuilder> layerBuffers = ((AccessorMultiBufferSource) original).getFixedBuffers();
		SequencedMap<RenderType, ByteBufferBuilder> remapped = new Object2ObjectLinkedOpenHashMap<>();
		for (Map.Entry<RenderType, ByteBufferBuilder> e : layerBuffers.entrySet()) {
			remapped.put(e.getKey(), e.getValue());
		}
		return new GhostBuffers(fallback, remapped);
	}

	private static class GhostBuffers extends MultiBufferSource.BufferSource {
		private float alpha = 1F;

		protected GhostBuffers(ByteBufferBuilder fallback, SequencedMap<RenderType, ByteBufferBuilder> layerBuffers) {
			super(fallback, layerBuffers);
		}

		private void setAlpha(float alpha) {
			this.alpha = alpha;
		}

		@Override
		public VertexConsumer getBuffer(RenderType type) {
			// All ghost geometry is drawn through the translucent moving-block layer so the
			// preview can blend; the per-quad alpha is forced by GhostAlphaVertexConsumer.
			return new GhostAlphaVertexConsumer(super.getBuffer(RenderTypes.translucentMovingBlock()), alpha);
		}
	}

	private record GhostAlphaVertexConsumer(VertexConsumer prior, float alpha) implements VertexConsumer {
		@Override
		public VertexConsumer addVertex(float x, float y, float z) {
			return prior.addVertex(x, y, z);
		}

		@Override
		public VertexConsumer setColor(int r, int g, int b, int a) {
			return prior.setColor(r, g, b, a);
		}

		@Override
		public VertexConsumer setColor(int color) {
			return prior.setColor(color);
		}

		@Override
		public VertexConsumer setUv(float u, float v) {
			return prior.setUv(u, v);
		}

		@Override
		public VertexConsumer setUv1(int u, int v) {
			return prior.setUv1(u, v);
		}

		@Override
		public VertexConsumer setUv2(int u, int v) {
			return prior.setUv2(u, v);
		}

		@Override
		public VertexConsumer setNormal(float x, float y, float z) {
			return prior.setNormal(x, y, z);
		}

		@Override
		public VertexConsumer setLineWidth(float width) {
			return prior.setLineWidth(width);
		}

		@Override
		public void putBulkData(PoseStack.Pose pose, BakedQuad quad, float r, float g, float b, float a, int light, int overlay) {
			prior.putBulkData(pose, quad, r, g, b, alpha, light, overlay);
		}

		@Override
		public void putBulkData(PoseStack.Pose pose, BakedQuad quad, float[] brightness, float r, float g, float b, float a, int[] lights, int overlay) {
			prior.putBulkData(pose, quad, brightness, r, g, b, alpha, lights, overlay);
		}
	}
}
