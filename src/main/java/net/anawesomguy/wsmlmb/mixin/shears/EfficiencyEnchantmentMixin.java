package net.anawesomguy.wsmlmb.mixin.shears;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.enchantment.EfficiencyEnchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EfficiencyEnchantment.class)
public abstract class EfficiencyEnchantmentMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"), method = "isAcceptableItem")
    private boolean wsmlmb$isShears(ItemStack stack, Item item) {
        // allows anything in c:shears to be enchanted with efficiency
        return stack.isOf(item) || (item == Items.SHEARS && stack.isIn(ConventionalItemTags.SHEARS));
    }
}
