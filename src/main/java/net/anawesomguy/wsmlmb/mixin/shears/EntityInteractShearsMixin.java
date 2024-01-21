package net.anawesomguy.wsmlmb.mixin.shears;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({SheepEntity.class, SnowGolemEntity.class, MooshroomEntity.class})
public abstract class EntityInteractShearsMixin {
    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"), method = "interactMob")
    private boolean wsmlmb$isShears(ItemStack stack, Item item, Operation<Boolean> original) {
        // allows anything in c:shears to shear pumpkins and beehives
        return original.call(stack, item) || (item == Items.SHEARS && stack.isIn(ConventionalItemTags.SHEARS));
    }
}