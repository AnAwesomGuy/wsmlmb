package net.anawesomguy.wsmlmb.mixin.shears;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({SheepEntity.class, SnowGolemEntity.class})
public abstract class EntityInteractShearsMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"), method = "interactMob")
    private boolean stone_utils$isShears(ItemStack stack, Item item) {
        // allows anything in fabric:shears to shear sheep and snow golems
        return stack.isOf(item) || stack.isIn(ConventionalItemTags.SHEARS);
    }
}