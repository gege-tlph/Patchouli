package vazkii.patchouli.client.base;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;

import vazkii.patchouli.client.book.ClientBookRegistry;

import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class ClientRecipes {
	public static final ClientRecipes INSTANCE = new ClientRecipes();

	private final Map<RecipeType<?>, Collection<RecipeHolder<?>>> recipesByType = new HashMap<>();
	private final Map<ResourceKey<Recipe<?>>, RecipeHolder<?>> recipesById = new HashMap<>();

	private ClientRecipes() {}

	public void receivedRecipes(Collection<RecipeHolder<?>> recipes) {
		recipesByType.clear();
		recipesById.clear();
		for (RecipeHolder<?> recipe : recipes) {
			recipesByType.computeIfAbsent(recipe.value().getType(), rt -> new java.util.ArrayList<>()).add(recipe);
			recipesById.put(recipe.id(), recipe);
		}
		// Books are built when the first advancement packet arrives. If that already
		// happened, they were built without recipes and need a rebuild; if not, the
		// upcoming advancement-triggered build will see the cache filled here.
		if (ClientAdvancements.hasReceivedFirstAdvPacket()) {
			ClientBookRegistry.INSTANCE.reload();
		}
	}

	@SuppressWarnings("unchecked")
	public <R extends Recipe<?>> @Nullable RecipeHolder<R> getRecipeById(ResourceKey<Recipe<?>> key) {
		RecipeHolder<?> holder = recipesById.get(key);
		if (holder == null) {
			return null;
		}
		return (RecipeHolder<R>) holder;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T extends Recipe<?>> Collection<RecipeHolder<? extends T>> getRecipesByType(RecipeType<T> type) {
		return (Collection<RecipeHolder<? extends T>>) (Collection) recipesByType.getOrDefault(type, List.of());
	}

	public <R extends Recipe<?>> @Nullable RecipeHolder<R> getRecipeById(Identifier id) {
		return getRecipeById(ResourceKey.create(Registries.RECIPE, id));
	}
}
