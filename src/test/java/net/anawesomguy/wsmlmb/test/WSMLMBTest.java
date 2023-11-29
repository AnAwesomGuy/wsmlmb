package net.anawesomguy.wsmlmb.test;

import net.anawesomguy.wsmlmb.block.CustomCraftingTableBlock;
import net.anawesomguy.wsmlmb.block.MultiVersionBlockSettings;
import net.anawesomguy.wsmlmb.block.chest.TexturedChestBlock;
import net.anawesomguy.wsmlmb.item.MultiVersionItemSettings;
import net.anawesomguy.wsmlmb.util.WSMLMBUtil;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ShearsItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class WSMLMBTest implements ModInitializer {
    public static final String MOD_ID = "wsmlmb-test";
    public static final Block STONE_CHEST = Registry.register(
        Registries.BLOCK,
        new Identifier(MOD_ID, "stone_chest"),
        new TexturedChestBlock.Builder(MultiVersionBlockSettings.of("stone").strength(1.5F, 6).requiresTool())
            .setTextures(chestTexture("stone"), chestTexture("stone_left"), chestTexture("stone_right")) // sets the textures
            .build()
    );
    public static final Item STONE_CHEST_ITEM = Registry.register(
        Registries.ITEM,
        new Identifier(MOD_ID, "stone_chest"),
        new BlockItem(STONE_CHEST, new MultiVersionItemSettings())
    );
    public static final Block CUSTOM_CRAFTING_TABLE = Registry.register(
        Registries.BLOCK,
        new Identifier(MOD_ID, "custom_crafting_table"),
        new CustomCraftingTableBlock(MultiVersionBlockSettings.of("stone"))
    );
    public static final Item CUSTOM_CRAFTING_TABLE_ITEM = Registry.register(
        Registries.ITEM,
        new Identifier(MOD_ID, "custom_crafting_table"),
        new BlockItem(CUSTOM_CRAFTING_TABLE, new MultiVersionItemSettings())
    );
    // will be added to c:shears
    public static final Item TEST_SHEARS = Registry.register(
        Registries.ITEM,
        new Identifier(MOD_ID, "test_shears"),
        new ShearsItem(new MultiVersionItemSettings())
    );

    private static Identifier chestTexture(String textureName) {
        return new Identifier(MOD_ID, "entity/chest/" + textureName);
    }

    @Override
    public void onInitialize() {
        WSMLMBUtil.addToGroup("functional", CUSTOM_CRAFTING_TABLE_ITEM, STONE_CHEST_ITEM);
        WSMLMBUtil.addToGroup("tools", TEST_SHEARS);
    }
}