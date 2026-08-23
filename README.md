# Patchouli

**简体中文** | [English](README_en.md)

[![CurseForge](https://img.shields.io/curseforge/dt/1664943?logo=curseforge&logoColor=white&label=CurseForge&color=F16436)](https://www.curseforge.com/minecraft/mc-mods/patchouli-fabric-edition)
[![Release](https://img.shields.io/github/v/release/gege-tlph/Patchouli?logo=github&label=Release)](https://github.com/gege-tlph/Patchouli/releases/latest)
[![GitHub downloads](https://img.shields.io/github/downloads/gege-tlph/Patchouli/total?logo=github&label=Downloads)](https://github.com/gege-tlph/Patchouli/releases)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.11-62B47A)](https://www.minecraft.net/)
[![Mod Loader](https://img.shields.io/badge/Mod%20Loader-Fabric-DBD0B4)](https://fabricmc.net/)
[![License](https://img.shields.io/badge/License-CC%20BY--NC--SA%203.0-blue)](LICENSE)

> [!IMPORTANT]
> 本仓库是 [VazkiiMods/Patchouli](https://github.com/VazkiiMods/Patchouli) 的非官方维护 fork，面向 Minecraft 1.21.11 与 Fabric。本 fork 不代表 Patchouli 官方版本；其他 Minecraft 版本请使用上游发行版。

Patchouli 是一个数据驱动的游戏内手册系统，供模组作者和整合包作者制作易于阅读、可交互且无需自行编写界面代码的说明书。

## 功能

- 使用数据包定义游戏内手册、章节、条目与页面
- 支持文本、图片、配方、多方块结构和进度展示
- 为模组与整合包提供公开 API
- 支持客户端与服务端的手册和配方数据同步
- 保留世界内多方块结构的半透明预览

通用使用方法和书本格式请参阅 [Patchouli 官方文档](https://vazkiimods.github.io/Patchouli/docs/intro)。

## 兼容性

| 组件 | 要求 |
|---|---|
| Minecraft | 1.21.11 |
| Fabric Loader | 0.15.11 或更高版本 |
| Fabric API | 0.139.4 或更高的 1.21.11 版本 |
| Java | 21 |
| 安装位置 | 客户端与服务端 |

## 安装

1. 安装适用于 Minecraft 1.21.11 的 Fabric Loader 和 Fabric API。
2. 从 [GitHub Releases](https://github.com/gege-tlph/Patchouli/releases/latest) 下载名称中不含 `sources` 的 `Patchouli-*-FABRIC.jar`。
3. 将 Patchouli 与 Fabric API 放入客户端和服务端的 `mods` 目录。
4. 确保加入同一服务器的客户端使用兼容的 Patchouli 版本。

## 作为开发依赖

本 fork 的 Fabric 产物发布在仓库的 [`maven`](https://github.com/gege-tlph/Patchouli/tree/maven) 分支。Gradle 配置示例：

```gradle
repositories {
    maven {
        name = "Patchouli 1.21.11 fork"
        url = "https://raw.githubusercontent.com/gege-tlph/Patchouli/maven/"
    }
}

dependencies {
    modImplementation "vazkii.patchouli:Patchouli:1.21.11-94.4-FABRIC"
}
```

请根据 [Releases](https://github.com/gege-tlph/Patchouli/releases) 中的版本更新依赖坐标。不要将 Patchouli 以 Jar-in-Jar 方式重复打包进模组；`vazkii.patchouli.api` 包之外的代码属于实现细节，不保证跨版本兼容。

## 从源码构建

需要 JDK 21。克隆仓库后运行：

```bash
./gradlew :Fabric:build
./gradlew :Fabric:runGameTest
```

Windows PowerShell：

```powershell
.\gradlew.bat :Fabric:build
.\gradlew.bat :Fabric:runGameTest
```

可安装的 Fabric JAR 位于 `Fabric/build/libs/`。

## 相关项目

| 项目 | 关系 |
|---|---|
| [VazkiiMods/Patchouli](https://github.com/VazkiiMods/Patchouli) | 上游项目与官方发行版 |
| [Patchouli Documentation](https://vazkiimods.github.io/Patchouli/docs/intro) | 官方使用与开发文档 |
| [Maid Restaurant](https://github.com/gege-tlph/MaidRestaurant) | 使用本 fork 提供可选的游戏内餐厅手册 |
| [东方小女仆 Tsumugi](https://github.com/gege-tlph/TouhouLittleMaid-Tsumugi) | Maid Restaurant 的主体依赖 |
| [OpenYSM-Updated](https://github.com/gege-tlph/OpenYSM-Updated) | 为 Tsumugi 提供 YSM 模型兼容 |

## 许可证

Patchouli 的原始代码与资源采用 [CC BY-NC-SA 3.0 Unported](LICENSE) 许可证。
