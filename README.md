# Patchouli static Maven repository (1.21.11 Fabric fork)

Static Maven repository for the [`port/1.21.11-fabric`](https://github.com/gege-tlph/Patchouli/tree/port/1.21.11-fabric) branch, served via raw.githubusercontent.com. Published automatically by the `publish-maven` workflow on `release-1.21.11-*` tags.

```gradle
repositories {
    maven {
        name = "Patchouli 1.21.11 fork"
        url = "https://raw.githubusercontent.com/gege-tlph/Patchouli/maven/"
    }
}

dependencies {
    modImplementation "vazkii.patchouli:Patchouli:1.21.11-94-FABRIC"
}
```
