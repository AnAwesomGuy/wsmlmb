package net.anawesomguy.wsmlmb.block;

import net.anawesomguy.wsmlmb.util.WSMLMBUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This is an implementation of {@link CraftingTableBlock} that overrides {@link CraftingTableBlock#createScreenHandlerFactory(BlockState, World, BlockPos) createScreenHandlerFactory} to return a custom {@link CraftingScreenHandler} that always works for this block.
 * <p>
 * So, you can just use this block to create a functional custom crafting table.
 */
public class CustomCraftingTableBlock extends CraftingTableBlock {
    protected final MutableText titleText;

    /**
     * Creates a new instance of {@link CustomCraftingTableBlock} with the specified block settings and a default {@link CustomCraftingTableBlock#titleText} of "{@code container.crafting}".
     * @param settings the block settings to make this block have.
     */
    public CustomCraftingTableBlock(Settings settings) {
        this(settings, "container.crafting");
    }

    /**
     * Creates a new instance of {@link CustomCraftingTableBlock} with the specified block settings and {@link CustomCraftingTableBlock#titleText}.
     * @param settings the block settings to make this block have.
     * @param titleText the lang key of this block's screen's title.
     */
    public CustomCraftingTableBlock(Settings settings, String titleText) {
        this(settings, WSMLMBUtil.toTranslatable(titleText));
    }

    /**
     * Creates a new instance of {@link CustomCraftingTableBlock} with the specified block settings and {@link CustomCraftingTableBlock#titleText}.
     * @param settings the block settings to make this block have.
     * @param titleText the text of this block's screen's title.
     */
    public CustomCraftingTableBlock(Settings settings, MutableText titleText) {
        super(settings);
        this.titleText = titleText;
    }

    public MutableText getTitleText() {
        return this.titleText;
    }

    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory(
            (syncId, inventory, player) -> {
                ScreenHandlerContext context = ScreenHandlerContext.create(world, pos);
                return new CraftingScreenHandler(syncId, inventory, context) {
                    @Override
                    public boolean canUse(PlayerEntity player) {
                        return canUse(context, player, CustomCraftingTableBlock.this);
                    }
                };
            }, titleText
        );
    }
}
