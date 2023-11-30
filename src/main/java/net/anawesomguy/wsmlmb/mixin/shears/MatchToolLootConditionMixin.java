package net.anawesomguy.wsmlmb.mixin.shears;

import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mixin(MatchToolLootCondition.class)
public abstract class MatchToolLootConditionMixin implements LootCondition {
    @Unique
    private static final List<ItemPredicate> MATCH_TOOL_PREDICATES = new ArrayList<>();

    @Shadow @Final @SuppressWarnings("ShadowModifiers")
    private ItemPredicate predicate;

    @Inject(at = @At("RETURN"), method = "<init>")
    private void wsmlmb$shearsLoot(CallbackInfo ci) {
        // allows anything in c:shears to mine grass (and other stuff) and it will drop
        MATCH_TOOL_PREDICATES.add(predicate);
    }

    static {
        // loot is loaded before tags, so this is required
        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> {
            if (!client) {
                List<Item> shears = new ArrayList<>();
                for (RegistryEntry<Item> entry :
                    Registry.ITEM.getOrCreateEntryList(ConventionalItemTags.SHEARS))
                    shears.add(entry.value());

                for (ItemPredicate predicate : MATCH_TOOL_PREDICATES)
                    // access widened predicate to accessible and
                    // predicate.items to mutable and accessible
                    if (predicate.items != null && predicate.items.contains(Items.SHEARS)) {
                        (predicate.items = new HashSet<>(predicate.items)).addAll(shears);
                        predicate.items = Set.copyOf(predicate.items);
                    }

                shears.clear();
            }
            MATCH_TOOL_PREDICATES.clear();
        });
    }
}