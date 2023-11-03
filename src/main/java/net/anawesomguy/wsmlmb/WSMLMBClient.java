package net.anawesomguy.wsmlmb;

import net.anawesomguy.wsmlmb.block.chest.ChestTriple;
import net.anawesomguy.wsmlmb.block.chest.TexturedChestBlock;
import net.anawesomguy.wsmlmb.block.chest.TexturedChestBlockEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for {@link WSMLMB}'s client logic, specifically, {@linkplain BuiltinItemRendererRegistry#register(ItemConvertible, BuiltinItemRendererRegistry.DynamicItemRenderer) registering chests' item renderers}, and {@linkplain ClientSpriteRegistryCallback#event(Identifier) registering the chests' textures} to the {@linkplain TexturedRenderLayers#CHEST_ATLAS_TEXTURE chest atlas}.
 */
@Environment(EnvType.CLIENT)
public final class WSMLMBClient implements ClientModInitializer {
    private static final Map<TexturedChestBlock, TexturedChestBlockEntity> BLOCK_TO_ENTITY_MAP = new HashMap<>();
    @Override
    public void onInitializeClient() {
        // register chest item renderers
        for (TexturedChestBlock chest : WSMLMB.TEXTURED_CHESTS) {
            Item item = chest.asItem();
            if (item != Items.AIR && item != null) {
                BLOCK_TO_ENTITY_MAP.put(chest, chest.createBlockEntity(BlockPos.ORIGIN, chest.getDefaultState()));
                BuiltinItemRendererRegistry.INSTANCE.register(item,
                    (stack, mode, matrices, vertexConsumers, light, overlay) ->
                        MinecraftClient.getInstance()
                            .getBlockEntityRenderDispatcher()
                            .renderEntity(BLOCK_TO_ENTITY_MAP.get(chest), matrices, vertexConsumers, light, overlay)
                );
            }
        }

        // register textures to the chest atlas
        ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE)
            .register((atlasTexture, registry) -> {
                for (TexturedChestBlock chest : WSMLMB.TEXTURED_CHESTS) {
                    ChestTriple triple = chest.getTextures();
                    registerTriple(triple, registry);
                    if (triple.equals(triple = chest.getChristmasTextures())) {
                        registerTriple(triple, registry);
                    }
                }
            });


        BlockEntityRendererRegistry.register(WSMLMB.TEXTURED_CHEST_ENTITY_TYPE, ChestBlockEntityRenderer::new);
    }

    private static void registerTriple(ChestTriple triple, ClientSpriteRegistryCallback.Registry registry) {
        registry.register(triple.getSingle());
        registry.register(triple.getLeft());
        registry.register(triple.getRight());
    }
}