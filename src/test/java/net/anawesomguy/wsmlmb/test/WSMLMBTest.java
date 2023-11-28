package net.anawesomguy.wsmlmb.test;

import net.anawesomguy.wsmlmb.block.CustomCraftingTableBlock;
import net.anawesomguy.wsmlmb.block.MultiVersionBlockSettings;
import net.anawesomguy.wsmlmb.block.chest.TexturedChestBlock;
import net.anawesomguy.wsmlmb.item.MultiVersionItemSettings;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class WSMLMBTest implements ModInitializer {
    public static final String MOD_ID = "wsmlmb-test";
    public static final Block STONE_CHEST = Registry.register(
        Registry.BLOCK,
        new Identifier(MOD_ID, "stone_chest"),
        new TexturedChestBlock.Builder(MultiVersionBlockSettings.of("stone").strength(1.5F, 6).requiresTool())
            .setTextures(new Identifier("block/andesite"), new Identifier("block/diorite"), new Identifier("block/stone")) // sets the normal textures (when it's not Christmas) (i also can't be bothered to make real textures lol)
            .setChristmasTextures(new Identifier("block/granite"), new Identifier("block/deepslate"), new Identifier("block/mossy_cobblestone")) // sets the texture of the chest when it is Christmas
            .build()
    );
    public static final Item STONE_CHEST_ITEM = Registry.register(
        Registry.ITEM,
        new Identifier(MOD_ID, "stone_chest"),
        new BlockItem(STONE_CHEST, new MultiVersionItemSettings().group(ItemGroup.DECORATIONS))
    );
    public static final Block CUSTOM_CRAFTING_TABLE = Registry.register(
        Registry.BLOCK,
        new Identifier(MOD_ID, "custom_crafting_table"),
        new CustomCraftingTableBlock(MultiVersionBlockSettings.of("wood").strength(1))
    );
    public static final Item CUSTOM_CRAFTING_TABLE_ITEM = Registry.register(
        Registry.ITEM,
        new Identifier(MOD_ID, "custom_crafting_table"),
        new BlockItem(CUSTOM_CRAFTING_TABLE, new MultiVersionItemSettings().group(ItemGroup.DECORATIONS))
    );
    // will be added to c:shears
    public static final Item TEST_SHEARS = Registry.register(
        Registry.ITEM,
        new Identifier(MOD_ID, "test_shears"),
        new ShearsItem(new MultiVersionItemSettings().group(ItemGroup.TOOLS))
    );

    @Override
    public void onInitialize() {
        // nothing here
    }
}
