package net.anawesomguy.wsmlmb.mixin.shears;

import net.anawesomguy.wsmlmb.WSMLMB;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MatchToolLootCondition.class)
public abstract class MatchToolLootConditionMixin implements LootCondition {
    @Inject(at = @At("RETURN"), method = "<init>")
    private void stone_utils$shearsLoot(CallbackInfo ci) {
        // allows anything in fabric:shears to mine grass (and other stuff) and it will drop
        WSMLMB.MATCH_TOOL_INSTANCES.add((MatchToolLootCondition)(Object)this);
    }
}