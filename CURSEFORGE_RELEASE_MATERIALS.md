# CurseForge 发布材料

## 项目基本信息

- **项目名称**：Patchouli
- **项目类型**：Mod
- **游戏**：Minecraft: Java Edition
- **首发加载器**：Fabric
- **Minecraft 版本**：1.21.11
- **首发版本**：1.21.11-94.4-FABRIC
- **Mod ID**：`patchouli`
- **许可证**：CC BY-NC-SA 3.0
- **源码**：https://github.com/gege-tlph/Patchouli
- **上游项目**：https://github.com/VazkiiMods/Patchouli
- **文档**：https://vazkiimods.github.io/Patchouli/docs/intro

## 简短描述

Unofficial Minecraft 1.21.11 Fabric port of Patchouli, a data-driven in-game documentation system for mod and modpack authors.

## 详细描述

## Patchouli: Fabric Edition

Patchouli: Fabric Edition is an unofficial community-maintained port of Patchouli for Minecraft 1.21.11 and Fabric. Patchouli is a data-driven in-game documentation system that lets mod and modpack authors create interactive guide books.

### Original project and credit

All credit for the original Patchouli project, design, code, and assets belongs to its original authors and contributors, including Vazkii and williewillus.

- Original CurseForge project: https://www.curseforge.com/minecraft/mc-mods/patchouli
- Original source repository: https://github.com/VazkiiMods/Patchouli
- Official documentation: https://vazkiimods.github.io/Patchouli/docs/intro

This port is not an official VazkiiMods release and is not endorsed by, sponsored by, or affiliated with the original authors. For Minecraft versions supported by the original project, please use the official Patchouli releases.

### How this project differs from the original

The official Patchouli project does not currently provide this maintained Minecraft 1.21.11 Fabric build. This fork ports the Minecraft 1.21.1 implementation to Minecraft 1.21.11 and publishes only the Fabric artifact for this version.

The port includes:

- Minecraft 1.21.11 and Java 21 API migrations.
- Fabric Loader and Fabric API support for Minecraft 1.21.11.
- Migration of book item rendering to the newer data-driven item model system.
- Fabric recipe synchronization and book rebuilding after synchronized recipes arrive.
- Migration of in-book multiblock previews to Minecraft's picture-in-picture rendering system.
- Restored translucent world multiblock previews.
- Compatibility fixes for 1.21.11 text color rendering, networking, mixins, and NBT APIs.

Source code for this maintained port: https://github.com/gege-tlph/Patchouli

### License

The original Patchouli code and assets are licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported license. This fork and its modifications are distributed under the same license and retain the original license file and attribution.

- Full license text: https://creativecommons.org/licenses/by-nc-sa/3.0/legalcode
- Repository license file: https://github.com/gege-tlph/Patchouli/blob/port/1.21.11-fabric/LICENSE

## 建议分类与标签

- **Category**：API and Library
- **Secondary category**：Cosmetic
- **Environment**：Client and Server
- **Tags**：Fabric, Library, API, Documentation, Guide Book, Modding Library

## 依赖关系

- Fabric Loader `>=0.15.11`
- Fabric API `>=0.139.4` for Minecraft 1.21.11
- Minecraft `1.21.11`
- Java `21`

建议在 CurseForge 的依赖设置中将 **Fabric API** 标为 Required Dependency。

## 首发文件

- **文件**：`Fabric/build/libs/Patchouli-1.21.11-94.4-FABRIC.jar`
- **文件大小**：879,277 bytes
- **SHA-256**：`bebdabda324eab550293487f7e3600c72334ebd5b8df62b05ba0212c67262331`
- **Release type**：Release
- **Supported version**：1.21.11
- **Loader**：Fabric

## 首发 Changelog

### Patchouli 1.21.11-94.4-FABRIC

- Fixed book header and body text colors becoming fully transparent when six-digit hexadecimal colors are used on Minecraft 1.21.11.
- Restored opaque text-color behavior to match the 1.21.1 implementation.
- Includes the 1.21.11 Fabric multiblock picture-in-picture rendering port.

## 审核备注

The project description has been updated to provide explicit credit to the original Patchouli authors, direct links to the original CurseForge page and source repository, a detailed list of differences between this Minecraft 1.21.11 Fabric port and the original project, and confirmation that this fork follows the original CC BY-NC-SA 3.0 Unported license.

## 自定义许可证字段

- **License name**：`Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported`
- **Short name**：`CC BY-NC-SA 3.0 Unported`
- **License URL**：`https://creativecommons.org/licenses/by-nc-sa/3.0/legalcode`
- **Source license**：`https://github.com/gege-tlph/Patchouli/blob/port/1.21.11-fabric/LICENSE`

## 页面图片

- 项目图标副本：`release/curseforge/patchouli-icon.png`
- 可从 GitHub Release 页面获取首发 JAR：`release-1.21.11-94.4-FABRIC`
