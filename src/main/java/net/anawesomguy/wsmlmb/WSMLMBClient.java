package net.anawesomguy.wsmlmb;

import net.anawesomguy.wsmlmb.block.chest.TexturedChestBlock;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;

/**
 * Class for {@link WSMLMB}'s client logic, specifically, {@linkplain BuiltinItemRendererRegistry#register(ItemConvertible, BuiltinItemRendererRegistry.DynamicItemRenderer) registering chests' item renderers}.
 */
@Environment(EnvType.CLIENT)
public final class WSMLMBClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // register chest item renderers
        for (TexturedChestBlock chest : WSMLMB.TEXTURED_CHESTS) {
            Item item = chest.asItem();
            if (item != Items.AIR && item != null) {
                BlockEntity be = chest.createBlockEntity(BlockPos.ORIGIN, chest.getDefaultState());
                BuiltinItemRendererRegistry.INSTANCE.register(item,
                    (stack, mode, matrices, vertexConsumers, light, overlay) ->
                        MinecraftClient.getInstance()
                            .getBlockEntityRenderDispatcher()
                            .renderEntity(be, matrices, vertexConsumers, light, overlay)
                );
            }
        }

        BlockEntityRendererFactories.register(WSMLMB.TEXTURED_CHEST_ENTITY_TYPE, ChestBlockEntityRenderer::new);
    }
}