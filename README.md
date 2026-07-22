# Patchouli — Minecraft 1.21.11 Fabric 移植版

> [!IMPORTANT]
> 这是 [VazkiiMods/Patchouli](https://github.com/VazkiiMods/Patchouli) 的非官方维护 fork，
> 将上游 Minecraft 1.21.1 实现移植至 **Minecraft 1.21.11 + Fabric**。
> 本 fork 不代表 Patchouli 官方版本；其他 Minecraft 版本请使用上游发行版。

Patchouli 是一个数据驱动的游戏内手册系统，供模组作者和整合包作者制作易于阅读、可交互且无需编写界面代码的说明书。

**English:** This is an unofficial Fabric port of Patchouli for Minecraft 1.21.11.
For other Minecraft versions and the original project documentation, use the
[upstream repository](https://github.com/VazkiiMods/Patchouli) and
[official documentation](https://vazkiimods.github.io/Patchouli/docs/intro).

## 关于本次移植

移植分支为 [`port/1.21.11-fabric`](../../tree/port/1.21.11-fabric)，仅构建 Fabric 模块；
NeoForge 模块在此分支中不参与构建。

主要适配内容：

- 将书本物品迁移至 1.21.11 的数据驱动物品模型系统，通过 `ModelManager.getItemModel` 解析模型，并为内置书本颜色和完成度进度条提供新的物品模型定义。
- 将书页中的多方块预览迁移至 `PictureInPictureRenderer`，将多方块进度条迁移为 HUD 元素。
- 使用半透明移动方块渲染层与委托 `VertexConsumer`，保留世界内多方块结构的半透明“幽灵方块”预览。
- 针对 1.21.2 以后客户端不再接收完整配方的变化，为 Patchouli 配方页面启用 Fabric 配方同步，并在配方到达后重建手册内容。
- 迁移 Mixin 注入点、网络 payload、NBT `ValueInput` / `ValueOutput` 以及其他 Minecraft 1.21.11 API。
- 构建系统升级至 Java 21、Gradle 9.5 和 Fabric Loom 1.17，并提供 GitHub Actions 与静态 Maven 自动发布。

## 安装

| 组件 | 最低版本 | 下载 |
|---|---:|---|
| Minecraft | 1.21.11 | [Minecraft 官网](https://www.minecraft.net/) |
| Java | 21 | [Eclipse Temurin 21](https://adoptium.net/temurin/releases/?version=21) |
| Fabric Loader | 0.15.11 | [Fabric Installer](https://fabricmc.net/use/installer/) |
| Fabric API | 0.139.4+1.21.11 | [Modrinth](https://modrinth.com/mod/fabric-api) · [CurseForge](https://www.curseforge.com/minecraft/mc-mods/fabric-api) |

从 [GitHub Releases](../../releases) 下载名称中不含 `sources` 的 `Patchouli-...-FABRIC.jar`，
与 Fabric API 一同放入客户端和服务器的 `mods` 目录。客户端与服务器应使用相同版本。

当前维护版本为 `1.21.11-94.1-FABRIC`。最低依赖来自实际 API 使用情况，而不是开发环境版本：
本移植使用的 Fabric API 均存在于 `0.139.4+1.21.11`，该版本要求的 Fabric Loader 下限为 `0.15.11`。

## Maven（本 fork）

1.21.11 Fabric 产物由 GitHub Actions 发布到 [`maven`](../../tree/maven) 分支：

```gradle
repositories {
    maven {
        name = "Patchouli 1.21.11 fork"
        url = "https://raw.githubusercontent.com/gege-tlph/Patchouli/maven/"
    }
}

dependencies {
    modImplementation "vazkii.patchouli:Patchouli:1.21.11-94.1-FABRIC"
}
```

请勿将 Patchouli 以 Jar-in-Jar 方式重复打包进模组；正常声明依赖可以避免重复安装和体积膨胀。
`vazkii.patchouli.api` 包之外的代码属于实现细节，不保证跨版本稳定。

## 从源码构建

需要 JDK 21。克隆仓库并切换到移植分支后运行：

```bash
./gradlew :Fabric:build
./gradlew :Fabric:runGameTest
```

Windows PowerShell：

```powershell
.\gradlew.bat :Fabric:build
.\gradlew.bat :Fabric:runGameTest
```

可安装 JAR 位于 `Fabric/build/libs/`。推送 `release-1.21.11-*` 标签时，自动化会执行构建、
GameTest、GitHub Release 附件上传和静态 Maven 发布。

## 问题反馈

报告问题时请附上完整日志、模组列表、复现步骤，以及问题能否在只安装 Patchouli、Fabric API 的环境中复现。
本 fork 只处理 Minecraft 1.21.11 Fabric 移植相关问题；通用功能问题请先确认能否在上游版本复现。

## 来源与许可证

- 上游项目：[VazkiiMods/Patchouli](https://github.com/VazkiiMods/Patchouli)
- 1.21.11 Fabric 维护 fork：[gege-tlph/Patchouli](https://github.com/gege-tlph/Patchouli)
- 官方文档：[Patchouli Documentation](https://vazkiimods.github.io/Patchouli/docs/intro)

Patchouli 的原始代码与资源采用 **CC BY-NC-SA 3.0 Unported** 许可证，详情见 [`LICENSE`](LICENSE)。
本项目使用 Mojang 官方映射；直接依赖发布后的 Intermediary 映射 JAR 或仅使用公开 API 不会要求下游项目采用 Mojang 映射。
