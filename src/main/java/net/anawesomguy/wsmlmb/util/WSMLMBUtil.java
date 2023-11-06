package net.anawesomguy.wsmlmb.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.TextContent;
import net.minecraft.text.TranslatableTextContent;

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
    // this is kinda stupid lol
    @SuppressWarnings("all") // but if it works it works ig
    public static MutableText toTranslatable(String langKey) {
        Object temp = new TranslatableTextContent(langKey);
        if (temp instanceof MutableText)
            return (MutableText)temp;
        else {
            return MutableText.of((TextContent)temp);
        }
    }
}
