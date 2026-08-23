# Patchouli

[简体中文](README.md) | **English**

[![CurseForge](https://img.shields.io/curseforge/dt/1664943?logo=curseforge&logoColor=white&label=CurseForge&color=F16436)](https://www.curseforge.com/minecraft/mc-mods/patchouli-fabric-edition)
[![Release](https://img.shields.io/github/v/release/gege-tlph/Patchouli?logo=github&label=Release)](https://github.com/gege-tlph/Patchouli/releases/latest)
[![GitHub downloads](https://img.shields.io/github/downloads/gege-tlph/Patchouli/total?logo=github&label=Downloads)](https://github.com/gege-tlph/Patchouli/releases)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.11-62B47A)](https://www.minecraft.net/)
[![Mod Loader](https://img.shields.io/badge/Mod%20Loader-Fabric-DBD0B4)](https://fabricmc.net/)
[![License](https://img.shields.io/badge/License-CC%20BY--NC--SA%203.0-blue)](LICENSE)

> [!IMPORTANT]
> This repository is an unofficial maintained fork of [VazkiiMods/Patchouli](https://github.com/VazkiiMods/Patchouli) for Minecraft 1.21.11 and Fabric. It is not an official Patchouli release. Use the upstream releases for other Minecraft versions.

Patchouli is a data-driven in-game documentation system that lets mod and modpack authors create accessible, interactive guide books without writing custom screens.

## Features

- Define in-game books, categories, entries, and pages through data packs
- Display text, images, recipes, multiblock structures, and progress indicators
- Provide a public API for mods and modpacks
- Synchronize book and recipe data between clients and servers
- Preserve translucent multiblock previews in the world

See the [official Patchouli documentation](https://vazkiimods.github.io/Patchouli/docs/intro) for general usage and book format documentation.

## Compatibility

| Component | Requirement |
|---|---|
| Minecraft | 1.21.11 |
| Fabric Loader | 0.15.11 or newer |
| Fabric API | 0.139.4 or newer for Minecraft 1.21.11 |
| Java | 21 |
| Environment | Client and server |

## Installation

1. Install Fabric Loader and Fabric API for Minecraft 1.21.11.
2. Download the `Patchouli-*-FABRIC.jar` file without `sources` in its name from [GitHub Releases](https://github.com/gege-tlph/Patchouli/releases/latest) or [CurseForge](https://www.curseforge.com/minecraft/mc-mods/patchouli-fabric-edition).
3. Place Patchouli and Fabric API in the `mods` directory on both the client and server.
4. Make sure clients joining the same server use a compatible Patchouli version.

## Development Dependency

Fabric artifacts from this fork are published to the repository's [`maven`](https://github.com/gege-tlph/Patchouli/tree/maven) branch:

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

Update the dependency coordinate according to the version listed under [Releases](https://github.com/gege-tlph/Patchouli/releases). Do not bundle Patchouli as a duplicate Jar-in-Jar dependency. Packages outside `vazkii.patchouli.api` are implementation details and are not guaranteed to remain compatible between versions.

## Building from Source

JDK 21 is required. After cloning the repository, run:

```bash
./gradlew :Fabric:build
./gradlew :Fabric:runGameTest
```

Windows PowerShell:

```powershell
.\gradlew.bat :Fabric:build
.\gradlew.bat :Fabric:runGameTest
```

The installable Fabric JAR is generated in `Fabric/build/libs/`.

## Related Projects

| Project | Relationship |
|---|---|
| [VazkiiMods/Patchouli](https://github.com/VazkiiMods/Patchouli) | Upstream project and official releases |
| [Patchouli Documentation](https://vazkiimods.github.io/Patchouli/docs/intro) | Official usage and development documentation |
| [Maid Restaurant](https://github.com/gege-tlph/MaidRestaurant) | Uses this fork to provide an optional in-game restaurant guide |
| [Touhou Little Maid: Tsumugi](https://github.com/gege-tlph/TouhouLittleMaid-Tsumugi) | Main dependency of Maid Restaurant |
| [OpenYSM-Updated](https://github.com/gege-tlph/OpenYSM-Updated) | Provides YSM model compatibility for Tsumugi |

## License

The original Patchouli code and assets are licensed under the [CC BY-NC-SA 3.0 Unported](LICENSE) license.
