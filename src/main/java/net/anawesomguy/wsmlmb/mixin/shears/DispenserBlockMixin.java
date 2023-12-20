package net.anawesomguy.wsmlmb.mixin.shears;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ShearsDispenserBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;

@Mixin(DispenserBlock.class)
public abstract class DispenserBlockMixin {
    @Shadow @Final private static Map<Item, DispenserBehavior> BEHAVIORS;

    @ModifyReturnValue(at = @At("TAIL"), method = "getBehaviorForItem")
    private DispenserBehavior wsmlmb$registerShearsBehavior(DispenserBehavior original, @Local ItemStack stack) {
        // allows anything in c:shears to have the dispenser behavior of shears,
        // but only if there isn't a dispenser behavior already registered
        if (!BEHAVIORS.containsKey(stack.getItem()) &&
            stack.isIn(ConventionalItemTags.SHEARS)
        ) return new ShearsDispenserBehavior();
        return original;
    }
}