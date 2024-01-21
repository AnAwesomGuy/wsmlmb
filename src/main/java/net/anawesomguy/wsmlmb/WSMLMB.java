package net.anawesomguy.wsmlmb;

import net.anawesomguy.wsmlmb.block.chest.ChestTriple.Sprite;
import net.anawesomguy.wsmlmb.block.chest.TexturedChestBlock;
import net.anawesomguy.wsmlmb.block.chest.TexturedChestBlockEntity;
import net.anawesomguy.wsmlmb.util.CrossVersionUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Stands for "what should my library mod be?". This is this library mod's main class.
 */
public final class WSMLMB implements ModInitializer {
    public static final String MOD_ID = "wsmlmb";
    @Internal
    public static final Logger LOGGER = LoggerFactory.getLogger("WSMLMB");
    /** @hidden */
    @Internal // don't use this
    public static final Set<TexturedChestBlock> TEXTURED_CHESTS = new HashSet<>();
    public static final BlockEntityType<TexturedChestBlockEntity> TEXTURED_CHEST_ENTITY_TYPE = new BlockEntityType<>(
        (pos, state) -> new TexturedChestBlockEntity(pos, state, Sprite.DEFAULT_CHRISTMAS_TEXTURES, Sprite.DEFAULT_CHRISTMAS_TEXTURES),
        Collections.unmodifiableSet(TEXTURED_CHESTS), null
    ) {
        @Override
        public boolean supports(BlockState state) {
            return state.getBlock() instanceof TexturedChestBlock;
        }
    };

    @Override
    public void onInitialize() {
        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "textured_chest"), TEXTURED_CHEST_ENTITY_TYPE);

        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((group, entries) -> {
            Optional<RegistryKey<ItemGroup>> key = Registries.ITEM_GROUP.getKey(group);
            if (key.isPresent()) {
                Event<Consumer<CrossVersionUtil.FabricItemGroupEntriesWrapper>> event = CrossVersionUtil.FabricItemGroupEntriesWrapper.getModifyEntriesEvent(key.get());
                if (event != null)
                    event.invoker().accept(new CrossVersionUtil.FabricItemGroupEntriesWrapper(entries));
            }
        });
    }
}