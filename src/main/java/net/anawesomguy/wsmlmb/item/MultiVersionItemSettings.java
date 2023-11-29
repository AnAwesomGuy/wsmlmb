package net.anawesomguy.wsmlmb.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroup;
import net.minecraft.resource.featuretoggle.FeatureFlag;

/**
 * A custom class for {@link net.minecraft.item.Item.Settings} for support over multiple versions. If the method doesn't exist for that version, nothing will happen.
 */
public class MultiVersionItemSettings extends FabricItemSettings {
    public MultiVersionItemSettings group(ItemGroup group) {
        return this;
    }

    public MultiVersionItemSettings requires(Object... features) {
        super.requires((FeatureFlag[])features);
        return this;
    }
}
