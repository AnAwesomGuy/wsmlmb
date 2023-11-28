package net.anawesomguy.wsmlmb.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroup;

/**
 * A custom class for {@link net.minecraft.item.Item.Settings} for support over multiple versions. If the method doesn't exist for that version, nothing will happen.
 */
public class MultiVersionItemSettings extends FabricItemSettings {
    public MultiVersionItemSettings group(ItemGroup group) {
        super.group(group);
        return this;
    }

    public MultiVersionItemSettings requires(Object... features) {
        return this;
    }
}
