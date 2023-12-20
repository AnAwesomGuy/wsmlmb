package net.anawesomguy.wsmlmb.mixin.shears;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({SheepEntity.class, SnowGolemEntity.class, MooshroomEntity.class})
public abstract class EntityInteractShearsMixin {
    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"), method = "interactMob")
    private boolean wsmlmb$isShears(boolean original, @Local ItemStack stack) {
        // allows anything in c:shears to shear sheep and snow golems
        return original || stack.isIn(ConventionalItemTags.SHEARS);
    }
}