package net.anawesomguy.wsmlmb.mixin.shears;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.PumpkinBlock;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({PumpkinBlock.class, BeehiveBlock.class})
public abstract class BlockInteractShearsMixin {
    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"), method = "onUse")
    private boolean wsmlmb$isShears(boolean original, @Local ItemStack stack) {
        // allows anything in c:shears to shear pumpkins and beehives
        return original || stack.isIn(ConventionalItemTags.SHEARS);
    }
}