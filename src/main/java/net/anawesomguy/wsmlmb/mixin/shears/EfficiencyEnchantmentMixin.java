package net.anawesomguy.wsmlmb.mixin.shears;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.enchantment.EfficiencyEnchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EfficiencyEnchantment.class)
public abstract class EfficiencyEnchantmentMixin {
    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"), method = "isAcceptableItem")
    private boolean wsmlmb$isShears(boolean original, @Local ItemStack stack) {
        // allows anything in c:shears to be enchanted with efficiency
        return original || stack.isIn(ConventionalItemTags.SHEARS);
    }
}
