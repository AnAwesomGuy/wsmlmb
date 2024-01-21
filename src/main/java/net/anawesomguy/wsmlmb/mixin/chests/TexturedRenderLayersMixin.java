package net.anawesomguy.wsmlmb.mixin.chests;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.anawesomguy.wsmlmb.block.chest.ChestTriple;
import net.anawesomguy.wsmlmb.block.chest.TexturedChestBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TexturedRenderLayers.class)
public abstract class TexturedRenderLayersMixin {
    @ModifyReturnValue(at = @At("TAIL"), method = "getChestTextureId(Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/block/enums/ChestType;Z)Lnet/minecraft/client/util/SpriteIdentifier;")
    private static SpriteIdentifier wsmlmb$addChestTexture(SpriteIdentifier original, BlockEntity blockEntity, ChestType type, boolean christmas) {
        // used so chests can have custom textures when rendering
        if (blockEntity instanceof TexturedChestBlockEntity) {
            ChestTriple.Sprite triple = ((TexturedChestBlockEntity)blockEntity).getTextures(christmas);
            return switch (type) {
                case LEFT -> triple.getLeft();
                case RIGHT -> triple.getRight();
                default -> triple.getSingle();
            };
        }
        return original;
    }
}
