package net.anawesomguy.wsmlmb.util;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.MutableText;
import net.minecraft.text.TextContent;
import net.minecraft.text.TranslatableTextContent;

import java.lang.reflect.Method;

/**
 * This class contains some utility methods used in the library.
 */
public final class WSMLMBUtil {
    private static final Method TRANSLATABLE_TEXT;

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

    static {
        Method translatable_text;
        try {
            translatable_text = MutableText.class.getMethod(
                FabricLoader.getInstance()
                    .getMappingResolver()
                    .mapMethodName(
                        "intermediary",
                        "class_5250",
                        "method_43477",
                        "(net.minecraft.class_7417)Lnet.minecraft.class_5250;"
                    )
            );
        } catch (NoSuchMethodException e) {
            translatable_text = null;
        }
        TRANSLATABLE_TEXT = translatable_text;
    }
}
