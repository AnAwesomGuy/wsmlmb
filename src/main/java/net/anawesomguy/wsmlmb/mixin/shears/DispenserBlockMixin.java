package net.anawesomguy.wsmlmb.mixin.shears;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ShearsDispenserBehavior;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DispenserBlock.class)
public abstract class DispenserBlockMixin {
    @Inject(at = @At("TAIL"), method = "getBehaviorForItem", cancellable = true)
    private void wsmlmb$registerShearsBehavior(ItemStack stack, CallbackInfoReturnable<DispenserBehavior> cir) {
        // allows anything in c:shears to have the dispenser behavior of shears
        // if there isn't a dispenser behavior already registered
        if ((cir.getReturnValue() == DispenserBehavior.NOOP ||
             cir.getReturnValue() == null) &&
            stack.isIn(ConventionalItemTags.SHEARS)
        ) cir.setReturnValue(new ShearsDispenserBehavior());
    }
}
