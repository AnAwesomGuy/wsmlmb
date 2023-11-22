package net.anawesomguy.wsmlmb.mixin.shears;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(MatchToolLootCondition.class)
public abstract class MatchToolLootConditionMixin implements LootCondition {
    @SuppressWarnings("ShadowModifiers")
    @Shadow @Final private ItemPredicate predicate;
    @Unique private static final List<ItemPredicate> MATCH_TOOL_PREDICATES = new ArrayList<>();

    @Inject(at = @At("RETURN"), method = "<init>")
    private void stone_utils$shearsLoot(CallbackInfo ci) {
        // allows anything in c:shears to mine grass (and other stuff) and it will drop
        if (predicate.items != null) MATCH_TOOL_PREDICATES.add(predicate);
    }

    static {
        // loot is loaded before tags, so this is required
        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> {
            if (!client) {
                List<Item> shears = new ArrayList<>();
                for (RegistryEntry<Item> entry :
                        Registries.ITEM.getOrCreateEntryList(ConventionalItemTags.SHEARS))
                    shears.add(entry.value());

                for (ItemPredicate p : MATCH_TOOL_PREDICATES) {
                    //noinspection DataFlowIssue
                    if (p.items.contains(Items.SHEARS)) {
                        ImmutableSet.Builder<Item> builder = new ImmutableSet.Builder<>();
                        builder.addAll(p.items);
                        builder.addAll(shears);
                        p.items = builder.build();
                    }
                }

                shears.clear();
            }
            MATCH_TOOL_PREDICATES.clear();
        });
    }
}