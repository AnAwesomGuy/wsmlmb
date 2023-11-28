package net.anawesomguy.wsmlmb.block.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroup;

public class MultiVersionItemSettings extends FabricItemSettings {
    public MultiVersionItemSettings group(ItemGroup group) {
        super.group(group);
        return this;
    }

    public MultiVersionItemSettings requires(Object... features) {
        return this;
    }
}
