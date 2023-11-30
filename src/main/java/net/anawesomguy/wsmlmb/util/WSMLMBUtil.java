package net.anawesomguy.wsmlmb.util;

import net.anawesomguy.wsmlmb.WSMLMB;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * This class contains some utility methods used in the library.
 */
public final class WSMLMBUtil {
    private static final Map<String, Object> NAME_TO_GROUP_MAP = new HashMap<>();

    /**
     * Creates a translatable text out of the given language key.
     * <p>
     * This is used for support between different versions.
     *
     * @param langKey the language key to create a translatable text out of.
     * @return a translatable text representing the given {@code langKey}.
     */
    public static MutableText toTranslatable(String langKey) {
        return Text.translatable(langKey);
    }

    /**
     * Adds items to an item group (creative tab).
     * <p>
     * This is used for support between different versions.
     *
     * @param itemGroup the group of the item you want to add the item to. Must be of the correct type for your Minecraft version, or a string for the name of the {@link ItemGroups} field you want to add it to.
     * @param items the items to add to the group.
     */
    public static void addToGroup(Object itemGroup, ItemConvertible... items) {
        Event<ModifyEntries> event = getModifyEntriesEvent(itemGroup);
        if (event == null) {
            WSMLMB.LOGGER.error("Failed to add items to an unknown itemGroup! Skipping!");
            return;
        }
        event.register(entries -> {
            for (ItemConvertible item : items)
                entries.add(item);
        });
    }

    /**
     * Adds items to an item group (creative tab).
     * <p>
     * This is used for support between different versions.
     *
     * @param itemGroup the group of the item you want to add the item to. Must be of the correct type for your Minecraft version, a string for the name of the {@link ItemGroups} field you want to add it to, or an {@link Identifier}.
     * @param items the items to add to the group.
     */
    public static void addToGroup(Object itemGroup, ItemStack... items) {
        Event<ModifyEntries> event = getModifyEntriesEvent(itemGroup);
        if (event == null) {
            WSMLMB.LOGGER.error("Failed to add items to an unknown itemGroup! Skipping!");
            return;
        }
        event.register(entries -> {
            for (ItemStack item : items)
                entries.add(item);
        });
    }

    // can be null if there is no Event for the specified `itemGroup`
    public static Event<ModifyEntries> getModifyEntriesEvent(Object itemGroup) {
        Objects.requireNonNull(itemGroup, "itemGroup is null");
        RegistryKey<ItemGroup> group;
        if (itemGroup instanceof Identifier)
            group = Registries.ITEM_GROUP.getKey(Registries.ITEM_GROUP.get((Identifier)itemGroup)).orElse(null);
        else if (itemGroup instanceof Number)
            group = Registries.ITEM_GROUP.getKey(Registries.ITEM_GROUP.get(((Number)itemGroup).intValue())).orElse(null);
        else if (itemGroup instanceof ItemGroup)
            group = Registries.ITEM_GROUP.getKey((ItemGroup)itemGroup).orElse(null);
        else {
            //noinspection unchecked
            group = (RegistryKey<ItemGroup>)(itemGroup instanceof String ? getItemGroup((String)itemGroup) : itemGroup);
        }
        if (group == null) {
            WSMLMB.LOGGER.error("Could not get ModifyEntries event for unknown itemGroup \"" + itemGroup + "\"!",
                new IllegalArgumentException(itemGroup.toString()));
            return null;
        }
        return ItemGroupEvents.modifyEntriesEvent(group);
    }

    public static Object getItemGroup(String name) {
        name = Objects.requireNonNull(name).toLowerCase();
        Object o = NAME_TO_GROUP_MAP.get(name);
        if (o != null) return o;
        switch (name) {
            case "building_blocks" -> o = ItemGroups.BUILDING_BLOCKS;
            case "colored_blocks" -> o = ItemGroups.COLORED_BLOCKS;
            case "natural" -> o = ItemGroups.NATURAL;
            case "functional" -> o = ItemGroups.FUNCTIONAL;
            case "redstone" -> o = ItemGroups.REDSTONE;
            case "tools" -> o = ItemGroups.TOOLS;
            case "combat" -> o = ItemGroups.COMBAT;
            case "food_and_drink" -> o = ItemGroups.FOOD_AND_DRINK;
            case "ingredients" -> o = ItemGroups.INGREDIENTS;
            case "spawn_eggs" -> o = ItemGroups.SPAWN_EGGS;
            case "operator" -> o = ItemGroups.OPERATOR;
            default -> {
                Optional<RegistryKey<ItemGroup>> itemGroup = StringUtils.isNumeric(name) ?
                    Registries.ITEM_GROUP.getKey(Registries.ITEM_GROUP.get(Integer.parseInt(name))) :
                    Registries.ITEM_GROUP.getKey(Registries.ITEM_GROUP.get(new Identifier(name)));
                o = itemGroup.orElse(null);
            }
        }
        NAME_TO_GROUP_MAP.put(name, o);
        return o;
    }

    private WSMLMBUtil() {
        throw new AssertionError("Cannot instantiate WSMLMBUtil!");
    }
}