package net.anawesomguy.wsmlmb.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This is an implementation of {@link CraftingTableBlock} that overrides {@link CraftingTableBlock#createScreenHandlerFactory(BlockState, World, BlockPos) createScreenHandlerFactory} to return a custom {@link CraftingScreenHandler} that always works for this block.
 * <p>
 * So, you can just use this block to create a functional custom crafting table.
 */
public class CustomCraftingTableBlock extends CraftingTableBlock {
    protected final TranslatableText titleText;

    public CustomCraftingTableBlock(Settings settings) {
        this(settings, new TranslatableText("container.crafting"));
    }

    public CustomCraftingTableBlock(Settings settings, String titleText) {
        this(settings, new TranslatableText(titleText));
    }

    public CustomCraftingTableBlock(Settings settings, TranslatableText titleText) {
        super(settings);
        this.titleText = titleText;
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
