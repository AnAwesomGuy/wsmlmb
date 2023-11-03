![stickman v2.png](src/main/resources/assets/wsmlmb/icon.png)
# what should my library mod be?

### THIS IS A FABRIC MOD, PLEASE DON'T ASK ABOUT PORTING TO (NEO)FORGE!

This is just another library mod, nothing special.

You can download this Minecraft library mod at [Modrinth](https://modrinth.com/mod/wsmlmb), [Curseforge](https://curseforge.com/minecraft/mc-mods/wsmlmb) or here on [GitHub](https://github.com/AnAwesomGuy/wsmlmb).

This library depends on [Fabric API](https://modrinth.com/mod/fabric-api), but you should already have that installed.

## 🔽 If you want to use this library in your mod, see below! 🔽
### IF YOU ARE NOT A MOD DEV USING THIS LIBRARY, THIS ***DOES NOT*** AFFECT YOU! YOU CAN IGNORE THIS!

This mod is a library mod with 3 main features:

 - the ability to create functional custom chests with custom textures.
 - the ability to create functional custom shears by adding your item into the `#fabric:shears` tag.
 - the ability to create functional custom crafting tables.

To use this mod as a library, you can use [Modrinth's maven](https://docs.modrinth.com/maven):
```groovy
repositories {
    maven { url "https://api.modrinth.com/maven" }
}

dependencies {
    implementation "maven.modrinth:wsmlmb:${wsmlmb_version}"
}
```

Many of the classes and methods in this mod are already documented in the test mod and JavaDocs.

For adding custom chests, see the [TexturedChestBlock](src/main/java/net/anawesomguy/wsmlmb/block/chest/TexturedChestBlock.java) class and method, and read their JavaDocs.<br>
You can do that same for custom crafting tables at [CustomCraftingTableBlock](src/main/java/net/anawesomguy/wsmlmb/block/CustomCraftingTableBlock.java).

To add custom shears, simply add your `ShearsItem` to the `#fabric:shears` tag.

If you need any help, you can contact me at `@anawesomguy` on Discord in the ["The Fabric Project"](https://discord.gg/v6v4pMv) server.