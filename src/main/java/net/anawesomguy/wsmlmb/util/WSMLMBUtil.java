package net.anawesomguy.wsmlmb.util;

import net.anawesomguy.wsmlmb.WSMLMB;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Objects;

/**
 * This class contains some utility methods used in the library.
 */
public final class WSMLMBUtil {
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
     * @param items     the items to add to the group.
     */
    public static void addToGroup(Object itemGroup, ItemConvertible... items) {
        Event<ModifyEntries> event = getModifyEntriesEvent(itemGroup);
        if (event == null) return;
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
     * @param items     the items to add to the group.
     */
    public static void addToGroup(Object itemGroup, ItemStack... items) {
        Event<ModifyEntries> event = getModifyEntriesEvent(itemGroup);
        if (event == null) return;
        event.register(entries -> {
            for (ItemStack item : items)
                entries.add(item);
        });
    }

    // can be null if there is no Event for the specified `itemGroup`
    public static Event<ModifyEntries> getModifyEntriesEvent(Object itemGroup) {
        Objects.requireNonNull(itemGroup, "itemGroup is null");
        Identifier group;
        if (itemGroup instanceof Identifier)
            group = (Identifier)itemGroup;
        else if (itemGroup instanceof ItemGroup)
            group = ((ItemGroup)itemGroup).getId();
        else if (itemGroup instanceof String) {
            ItemGroup temp = (ItemGroup)getItemGroup((String)itemGroup);
            if (temp == null)
                group = new Identifier(((String)itemGroup).toLowerCase());
            else
                group = temp.getId();
        } else {
            WSMLMB.LOGGER.error("Could not get ModifyEntries event for unknown itemGroup \"" + itemGroup + "\"!",
                new IllegalArgumentException(itemGroup.toString()));
            return null;
        }
        return ItemGroupEvents.modifyEntriesEvent(group);
    }

    /**
     * Gets an item group with the specified name from the {@link ItemGroups} class.
     *
     * @param name the name of the item group to get.
     * @return an item group with name {@code name}, or null if it cannot find one. Is always an {@link ItemGroup} in this version.
     */
    public static Object getItemGroup(String name) {
        if ((name = Objects.requireNonNull(name).toLowerCase().replace(' ', '_')).startsWith("minecraft:"))
            name = name.substring(10);
        return switch (name) {
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
            case "operator", "op" -> ItemGroups.OPERATOR;
            default -> null;
        };
    }

    private WSMLMBUtil() {
        throw new AssertionError("Cannot instantiate WSMLMBUtil!");
    }
}