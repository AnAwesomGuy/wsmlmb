package net.anawesomguy.wsmlmb.mixin.chests;

import net.anawesomguy.wsmlmb.block.chest.ChestTriple;
import net.anawesomguy.wsmlmb.block.chest.TexturedChestBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TexturedRenderLayers.class)
public abstract class TexturedRenderLayersMixin {
    @Inject(at = @At("HEAD"), method = "getChestTexture(Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/block/enums/ChestType;Z)Lnet/minecraft/client/util/SpriteIdentifier;", cancellable = true)
    private static void stone_utils$addChestTexture(BlockEntity blockEntity, ChestType type, boolean christmas, CallbackInfoReturnable<SpriteIdentifier> cir) {
        // used so chests can have custom textures when rendering
        if (blockEntity instanceof TexturedChestBlockEntity chestBlockEntity) {
            ChestTriple triple = chestBlockEntity.getTextures(christmas);
            switch (type) {
                case LEFT ->
                    cir.setReturnValue(new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, triple.getLeft()));
                case RIGHT ->
                    cir.setReturnValue(new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, triple.getRight()));
                default ->
                    cir.setReturnValue(new SpriteIdentifier(TexturedRenderLayers.CHEST_ATLAS_TEXTURE, triple.getSingle()));
            }
        }
    }
}
