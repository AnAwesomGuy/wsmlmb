package net.anawesomguy.wsmlmb.util;

import net.anawesomguy.wsmlmb.WSMLMB;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.mixin.registry.sync.RegistriesAccessor;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import static net.minecraft.registry.Registries.*;

/**
 * This class contains some utility methods used for support over multiple versions.
 */
@SuppressWarnings("UnstableApiUsage")
public final class CrossVersionUtil {
    static final Map<RegistryKey<ItemGroup>, Event<Consumer<FabricItemGroupEntriesWrapper>>> ITEM_GROUP_EVENT_MAP = new HashMap<>();
    /**
     * An empty modify entries event that is never {@link Event#invoker() invoked}.
     * <p>
     * This is returned when {@link CrossVersionUtil#getModifyEntriesEvent} cannot find a suitable event for {@code itemGroup}.
     */
    private static final Event<Consumer<FabricItemGroupEntriesWrapper>> EMPTY_EVENT = EventFactory.createArrayBacked(Consumer.class, callbacks -> entries -> {});

    /**
     * Creates a translatable text out of the given language key.
     * <p>
     * This is used for support between different versions.
     *
     * @param langKey the language key to create a translatable text out of
     * @return a translatable text representing the given {@code langKey}.
     */
    public static MutableText toTranslatable(String langKey) {
        return Text.translatable(langKey);
    }

    /**
     * Returns a registry with name {@code registry} found by searching through the fields in {@link Registries} and registered registries.
     * <p>
     * {@code registry} can either be the name of a field in {@link Registries} (on yarn), an {@link Identifier} {@linkplain Identifier#isValid in string form}, or a {@link StringUtils#isNumeric number string}.
     * @param registry the name of the registry to be returned
     * @return the registry, or {@code null} if none can be found for type {@code <T>}
     *
     * @see Registries#REGISTRIES
     */
    @SuppressWarnings("unchecked")
    public static <T> Registry<? extends T> getRegistry(String registry) {
        try {
            return (Registry<T>)switch (registry = registry.toLowerCase()) {
                case "root" -> RegistriesAccessor.getROOT();
                case "game_event" -> GAME_EVENT;
                case "sound_event" -> SOUND_EVENT;
                case "fluid" -> FLUID;
                case "status_effect" -> STATUS_EFFECT;
                case "block" -> BLOCK;
                case "enchantment" -> ENCHANTMENT;
                case "entity_type" -> ENTITY_TYPE;
                case "item" -> ITEM;
                case "potion" -> POTION;
                case "particle_type" -> PARTICLE_TYPE;
                case "block_entity_type" -> BLOCK_ENTITY_TYPE;
                case "painting_variant" -> PAINTING_VARIANT;
                case "custom_stat" -> CUSTOM_STAT;
                case "chunk_status" -> CHUNK_STATUS;
                case "rule_test" -> RULE_TEST;
                case "rule_block_entity_modifier" -> RULE_BLOCK_ENTITY_MODIFIER;
                case "pos_rule_test" -> POS_RULE_TEST;
                case "screen_handler" -> SCREEN_HANDLER;
                case "recipe_type" -> RECIPE_TYPE;
                case "recipe_serializer" -> RECIPE_SERIALIZER;
                case "attribute" -> ATTRIBUTE;
                case "position_source_type" -> POSITION_SOURCE_TYPE;
                case "command_argument_type" -> COMMAND_ARGUMENT_TYPE;
                case "stat_type" -> STAT_TYPE;
                case "villager_type" -> VILLAGER_TYPE;
                case "villager_profession" -> VILLAGER_PROFESSION;
                case "point_of_interest_type" -> POINT_OF_INTEREST_TYPE;
                case "memory_module_type" -> MEMORY_MODULE_TYPE;
                case "sensor_type" -> SENSOR_TYPE;
                case "schedule" -> SCHEDULE;
                case "activity" -> ACTIVITY;
                case "loot_pool_entry_type" -> LOOT_POOL_ENTRY_TYPE;
                case "loot_function_type" -> LOOT_FUNCTION_TYPE;
                case "loot_condition_type" -> LOOT_CONDITION_TYPE;
                case "loot_number_provider_type" -> LOOT_NUMBER_PROVIDER_TYPE;
                case "loot_nbt_provider_type" -> LOOT_NBT_PROVIDER_TYPE;
                case "loot_score_provider_type" -> LOOT_SCORE_PROVIDER_TYPE;
                case "float_provider_type" -> FLOAT_PROVIDER_TYPE;
                case "int_provider_type" -> INT_PROVIDER_TYPE;
                case "height_provider_type" -> HEIGHT_PROVIDER_TYPE;
                case "block_predicate_type" -> BLOCK_PREDICATE_TYPE;
                case "carver" -> CARVER;
                case "feature" -> FEATURE;
                case "structure_placement" -> STRUCTURE_PLACEMENT;
                case "structure_piece" -> STRUCTURE_PIECE;
                case "structure_type" -> STRUCTURE_TYPE;
                case "placement_modifier_type" -> PLACEMENT_MODIFIER_TYPE;
                case "block_state_provider_type" -> BLOCK_STATE_PROVIDER_TYPE;
                case "foliage_placer_type" -> FOLIAGE_PLACER_TYPE;
                case "trunk_placer_type" -> TRUNK_PLACER_TYPE;
                case "root_placer_type" -> ROOT_PLACER_TYPE;
                case "tree_decorator_type" -> TREE_DECORATOR_TYPE;
                case "feature_size_type" -> FEATURE_SIZE_TYPE;
                case "biome_source" -> BIOME_SOURCE;
                case "chunk_generator" -> CHUNK_GENERATOR;
                case "material_condition" -> MATERIAL_CONDITION;
                case "material_rule" -> MATERIAL_RULE;
                case "density_function_type" -> DENSITY_FUNCTION_TYPE;
                case "block_type" -> BLOCK_TYPE;
                case "structure_processor" -> STRUCTURE_PROCESSOR;
                case "structure_pool_element" -> STRUCTURE_POOL_ELEMENT;
                case "pool_alias_binding" -> POOL_ALIAS_BINDING;
                case "cat_variant" -> CAT_VARIANT;
                case "frog_variant" -> FROG_VARIANT;
                case "banner_pattern" -> BANNER_PATTERN;
                case "instrument" -> INSTRUMENT;
                case "decorated_pot_pattern" -> DECORATED_POT_PATTERN;
                case "item_group" -> ITEM_GROUP;
                case "criterion" -> CRITERION;
                case "number_format_type" -> NUMBER_FORMAT_TYPE;
                case "registries" -> REGISTRIES;
                default -> (StringUtils.isNumeric(registry) ?
                    REGISTRIES.get(Integer.parseInt(registry)) :
                    REGISTRIES.get(new Identifier(registry)));
            };
        } catch (ClassCastException e) {
            return null;
        }
    }

    /*
     * ITEM GROUP STUFF BELOW!!!
     */

    /**
     * Returns the modify entries event for a specific item group.
     * If none can be found for {@code itemGroup}, an empty event is returned.
     * <p>
     * The returned uses a wrapper for {@link FabricItemGroupEntries}, {@link FabricItemGroupEntriesWrapper}, which allows for cross-version support.
     *
     * @param itemGroup the object to get the event from
     * @return the event
     * @see ItemGroupEvents#modifyEntriesEvent
     */
    @NotNull @SuppressWarnings("unchecked")
    public static Event<Consumer<FabricItemGroupEntriesWrapper>> getModifyEntriesEvent(Object itemGroup) {
        Objects.requireNonNull(itemGroup, "itemGroup is null");
        RegistryKey<ItemGroup> group;
        if (itemGroup instanceof String)
            group = (RegistryKey<ItemGroup>)getItemGroup((String)itemGroup);
        else if (itemGroup instanceof Identifier)
            group = ITEM_GROUP.getKey(ITEM_GROUP.get((Identifier)itemGroup)).orElse(null);
        else if (itemGroup instanceof RegistryKey<?>)
            group = (RegistryKey<ItemGroup>)itemGroup;
        else if (itemGroup instanceof ItemGroup)
            group = ITEM_GROUP.getKey((ItemGroup)itemGroup).orElse(null);
        else if (itemGroup instanceof Number)
            group = ITEM_GROUP.getKey(ITEM_GROUP.get(((Number)itemGroup).intValue())).orElse(null);
        else group = null;

        if (group == null) {
            WSMLMB.LOGGER.error("Could not get ModifyEntries event for unknown itemGroup \"" + itemGroup + "\", returning empty event instead!",
                new IllegalArgumentException(itemGroup.toString()));
            return EMPTY_EVENT;
        }

        return FabricItemGroupEntriesWrapper.modifyEntriesEvent(group);
    }

    /**
     * Gets an item group from {@code itemGroup}.
     * <p>
     * {@code itemGroup} can either be the name of a field in {@link ItemGroups} (on yarn), an {@link Identifier} {@linkplain Identifier#isValid in string form}, or a {@link StringUtils#isNumeric number string}.
     * @param itemGroup the name of the item group you want to get
     * @return an {@link ItemGroup} or a {@link RegistryKey<ItemGroup>}, depending on your Minecraft version.
     */
    public static Object getItemGroup(String itemGroup) {
        itemGroup = Objects.requireNonNull(itemGroup).toLowerCase();
        return switch (itemGroup) {
            case "building_blocks" -> ItemGroups.BUILDING_BLOCKS;
            case "colored_blocks" -> ItemGroups.COLORED_BLOCKS;
            case "natural" -> ItemGroups.NATURAL;
            case "functional" -> ItemGroups.FUNCTIONAL;
            case "redstone" -> ItemGroups.REDSTONE;
            case "tools" -> ItemGroups.TOOLS;
            case "combat" -> ItemGroups.COMBAT;
            case "food_and_drink" -> ItemGroups.FOOD_AND_DRINK;
            case "ingredients" -> ItemGroups.INGREDIENTS;
            case "spawn_eggs" -> ItemGroups.SPAWN_EGGS;
            case "operator" -> ItemGroups.OPERATOR;
            default -> (StringUtils.isNumeric(itemGroup) ?
                ITEM_GROUP.getKey(ITEM_GROUP.get(Integer.parseInt(itemGroup))) :
                ITEM_GROUP.getKey(ITEM_GROUP.get(new Identifier(itemGroup)))).orElse(null);
        };
    }

    /**
     * A wrapper for {@link FabricItemGroupEntries} to provide cross-version support.
     * <p>
     * On versions below {@code 1.19.3} (specifically {@code 22w42a}), the methods in this class <i>do not</i> do anything.
     *
     * @see ItemGroupEvents
     */
    public static final class FabricItemGroupEntriesWrapper extends FabricItemGroupEntries {
        @Internal
        public FabricItemGroupEntriesWrapper(FabricItemGroupEntries fabricEntries) {
            super(fabricEntries.getContext(), fabricEntries.getDisplayStacks(), fabricEntries.getSearchTabStacks());
        }

        /**
         * @see ItemGroupEvents#modifyEntriesEvent
         */
        @Internal
        public static Event<Consumer<FabricItemGroupEntriesWrapper>> modifyEntriesEvent(RegistryKey<ItemGroup> group) {
            return ITEM_GROUP_EVENT_MAP.computeIfAbsent(group, key ->
                EventFactory.createArrayBacked(Consumer.class, callbacks -> entries -> {
                    for (Consumer<FabricItemGroupEntriesWrapper> callback : callbacks) {
                        callback.accept(entries);
                    }
                })
            );
        }

        @Internal @Nullable
        public static Event<Consumer<FabricItemGroupEntriesWrapper>> getModifyEntriesEvent(RegistryKey<ItemGroup> group) {
            return ITEM_GROUP_EVENT_MAP.get(group);
        }
    }

    private CrossVersionUtil() {
        throw new AssertionError("Cannot instantiate CrossVersionUtil!");
    }
}