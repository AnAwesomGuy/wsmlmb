package net.anawesomguy.wsmlmb.mixin.shears;

import com.google.common.collect.ImmutableList;
import net.anawesomguy.wsmlmb.mixin.shears.accessors.DirectRegistryEntryListAccessor;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(MatchToolLootCondition.class)
public abstract class MatchToolLootConditionMixin implements LootCondition {
    @Unique private static final List<ItemPredicate> MATCH_TOOL_PREDICATES = new ArrayList<>();

    @Shadow public abstract Optional<ItemPredicate> predicate();

    @Inject(at = @At("RETURN"), method = "<init>")
    private void stone_utils$shearsLoot(CallbackInfo ci) {
        // allows anything in c:shears to mine grass (and other stuff) and it will drop
        predicate().ifPresent(MATCH_TOOL_PREDICATES::add);
    }

    static {
        // loot is loaded before tags, so this is required
        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> {
            if (!client) {
                List<RegistryEntry<Item>> shears = new ArrayList<>();
                for (RegistryEntry<Item> entry :
                        Registries.ITEM.getOrCreateEntryList(ConventionalItemTags.SHEARS))
                    shears.add(entry);

                for (ItemPredicate p : MATCH_TOOL_PREDICATES) {
                    if (p.items().isPresent() && p.items().get().contains(Items.SHEARS.getRegistryEntry())) {
                        @SuppressWarnings("unchecked")
                        DirectRegistryEntryListAccessor<Item> accessor = ((DirectRegistryEntryListAccessor<Item>)p.items().get());
                        ImmutableList.Builder<RegistryEntry<Item>> builder = new ImmutableList.Builder<>();
                        builder.addAll(accessor.getEntries());
                        builder.addAll(shears);
                        accessor.setEntries(builder.build());
                        accessor.setEntrySet(null);
                    }
                }

                shears.clear();
            }
            MATCH_TOOL_PREDICATES.clear();
        });
    }
}