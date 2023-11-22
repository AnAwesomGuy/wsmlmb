package net.anawesomguy.wsmlmb.util;

import net.anawesomguy.wsmlmb.WSMLMB;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries;
import net.fabricmc.fabric.impl.itemgroup.ItemGroupEventsImpl;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * This class contains some utility methods used in the library.
 */
public final class WSMLMBUtil {
    private static final Map<?, Event<ModifyEntries>> ITEM_GROUP_EVENT_MAP;

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
     * @param itemGroup the group of the item you want to add the item to. Must be of the correct type for your Minecraft version, or a string.
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
     * @param itemGroup the group of the item you want to add the item to. Must be of the correct type for your Minecraft version, or a string.
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
        try {
            Object group = itemGroup instanceof String ? getItemGroup((String)itemGroup) : itemGroup;
            if (group == null) {
                WSMLMB.LOGGER.error("Could not get ModifyEntries event for unknown itemGroup \"" + itemGroup + "\"!",
                    new IllegalArgumentException("Not a valid itemGroup!"));
                return null;
            }
            Event<ModifyEntries> event = ITEM_GROUP_EVENT_MAP.get(group);
            if (event == null) {
                System.out.println("done");
                //noinspection unchecked,JavaReflectionMemberAccess
                event = (Event<ModifyEntries>)ItemGroupEvents.class
                        .getMethod("modifyEntriesEvent", group.getClass())
                        .invoke(null, group);
            }
            return event;
        } catch (ReflectiveOperationException e) {
            WSMLMB.LOGGER.error("\"{}\" is not a valid item group!", itemGroup);
            throw new RuntimeException(e);
        }
    }

    public static Object getItemGroup(String name) {
        try {
            return ItemGroups.class.getField(name.toUpperCase()).get(null);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    private WSMLMBUtil() {
        throw new AssertionError("Cannot instantiate WSMLMBUtil!");
    }

    static {
        try {
            @SuppressWarnings("UnstableApiUsage")
            Field itemGroupEventMap = ItemGroupEventsImpl.class.getDeclaredField("ITEM_GROUP_EVENT_MAP");
            itemGroupEventMap.setAccessible(true);
            //noinspection unchecked
            ITEM_GROUP_EVENT_MAP = (Map<?, Event<ModifyEntries>>)itemGroupEventMap.get(null);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}