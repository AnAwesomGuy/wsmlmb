package net.anawesomguy.wsmlmb.mixin.shears;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.anawesomguy.wsmlmb.mixin.shears.accessors.DirectRegistryEntryListAccessor;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mixin(MatchToolLootCondition.class) @SuppressWarnings("deprecation")
public abstract class MatchToolLootConditionMixin implements LootCondition {
    @Unique
    private static final List<ItemPredicate> MATCH_TOOL_PREDICATES = new ArrayList<>();

    @Inject(at = @At("RETURN"), method = "<init>")
    private void wsmlmb$shearsLoot(CallbackInfo ci) {
        // allows anything in c:shears to mine grass (and other stuff) and it will drop
        ((MatchToolLootCondition)(Object)this).predicate().ifPresent(MATCH_TOOL_PREDICATES::add);
    }

    static {
        Set<RegistryEntry<Item>> shearsItems = new HashSet<>(); // holds all the `ShearsItem`s
        RegistryEntry<Item> shearsRegistryEntry = Items.SHEARS.getRegistryEntry();

        // loot is loaded before tags, so this is required
        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> {
            if (!client)
                return;

            if (shearsItems.isEmpty())
                for (Item item : Registries.ITEM)
                    if (item instanceof ShearsItem)
                        shearsItems.add(item.getRegistryEntry());

            Set<RegistryEntry<Item>> shears = ImmutableSet.<RegistryEntry<Item>>builderWithExpectedSize(shearsItems.size())
                                                          .addAll(Registries.ITEM.iterateEntries(ConventionalItemTags.SHEARS))
                                                          .addAll(shearsItems)
                                                          .build(); // use ImmutableSet for performance when using addAll on an ImmutableList builder

            for (ItemPredicate p : MATCH_TOOL_PREDICATES) {
                if (p.items().isPresent() && p.items().get().contains(shearsRegistryEntry)) {
                    @SuppressWarnings("unchecked")
                    DirectRegistryEntryListAccessor<Item> accessor = ((DirectRegistryEntryListAccessor<Item>)p.items().get());
                    ImmutableList.Builder<RegistryEntry<Item>> builder = new ImmutableList.Builder<>();
                    builder.addAll(accessor.getEntries());
                    builder.addAll(shears);
                    accessor.setEntries(builder.build());
                    accessor.setEntrySet(null);
                }
            }
            MATCH_TOOL_PREDICATES.clear();
        });
    }
}