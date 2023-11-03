package net.anawesomguy.wsmlmb;

import com.ibm.icu.impl.CollectionSet;
import net.anawesomguy.wsmlmb.block.chest.ChestTriple;
import net.anawesomguy.wsmlmb.block.chest.TexturedChestBlock;
import net.anawesomguy.wsmlmb.block.chest.TexturedChestBlockEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Stands for "what should my library mod be?". This is this library mod's main class.
 */
public final class WSMLMB implements ModInitializer {
    public static final String MOD_ID = "wsmlmb";
    /**
     * An {@link ArrayList} containing all instances of {@link MatchToolLootCondition} (done using a mixin to its {@link MatchToolLootCondition#MatchToolLootCondition(ItemPredicate) constructor}).
     * <p>
     * This will later be iterated over in {@link WSMLMB#onInitialize()} so that everything in the {@link ConventionalItemTags#SHEARS fabric:shears} tag will be added to the condition's {@link MatchToolLootCondition#predicate predicate}'s {@link net.minecraft.predicate.item.ItemPredicate#items items} if it contains {@link Items#SHEARS}.
     */
    @Internal // don't use this
    public static final List<MatchToolLootCondition> MATCH_TOOL_INSTANCES = new ArrayList<>();
    @Internal // don't use this
    public static final List<TexturedChestBlock> TEXTURED_CHESTS = new ArrayList<>();
    public static final BlockEntityType<TexturedChestBlockEntity> TEXTURED_CHEST_ENTITY_TYPE = Registry.register(
        Registry.BLOCK_ENTITY_TYPE,
        new Identifier(MOD_ID, "textured_chest"),
        new BlockEntityType<>(null, new CollectionSet<>(Collections.unmodifiableCollection(TEXTURED_CHESTS)), null) {
            @Override
            public boolean supports(BlockState state) {
                return state.getBlock() instanceof TexturedChestBlock;
            }

            @Override
            public TexturedChestBlockEntity instantiate(BlockPos pos, BlockState state) {
                return new TexturedChestBlockEntity(pos, state, ChestTriple.getDefault(), ChestTriple.getDefaultChristmas());
            }
        }
    );

    @Override
    public void onInitialize() {
        // loot is loaded before tags, so this is required
        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> {
            if (!client) {
                List<Item> shears = new ArrayList<>();
                for (RegistryEntry<Item> entry :
                        Registry.ITEM.getOrCreateEntryList(ConventionalItemTags.SHEARS))
                    shears.add(entry.value());

                for (MatchToolLootCondition c : MATCH_TOOL_INSTANCES)
                    // access widened predicate to accessible and
                    // predicate.items to mutable and accessible
                    if (c.predicate.items != null && c.predicate.items.contains(Items.SHEARS)) {
                        (c.predicate.items = new HashSet<>(c.predicate.items)).addAll(shears);
                        c.predicate.items = Set.copyOf(c.predicate.items);
                    }

                shears.clear();
            }
            MATCH_TOOL_INSTANCES.clear();
        });
    }
}