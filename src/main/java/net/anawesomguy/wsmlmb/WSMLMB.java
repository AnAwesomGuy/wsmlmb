package net.anawesomguy.wsmlmb;

import com.ibm.icu.impl.CollectionSet;
import net.anawesomguy.wsmlmb.block.chest.ChestTriple;
import net.anawesomguy.wsmlmb.block.chest.TexturedChestBlock;
import net.anawesomguy.wsmlmb.block.chest.TexturedChestBlockEntity;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Stands for "what should my library mod be?". This is this library mod's main class.
 */
public final class WSMLMB implements ModInitializer {
    public static final String MOD_ID = "wsmlmb";
    @Internal
    public static final Logger LOGGER = LoggerFactory.getLogger("WSMLMB");
    /** @hidden */
    @Internal // don't use this
    public static final List<TexturedChestBlock> TEXTURED_CHESTS = new ArrayList<>();
    public static final BlockEntityType<TexturedChestBlockEntity> TEXTURED_CHEST_ENTITY_TYPE = Registry.register(
        Registries.BLOCK_ENTITY_TYPE,
        new Identifier(MOD_ID, "textured_chest"),
        new BlockEntityType<>(
            (pos, state) -> new TexturedChestBlockEntity(pos, state, ChestTriple.Sprite.DEFAULT_CHRISTMAS_TEXTURES, ChestTriple.Sprite.DEFAULT_CHRISTMAS_TEXTURES),
            new CollectionSet<>(Collections.unmodifiableCollection(TEXTURED_CHESTS)), null
        ) {
            @Override
            public boolean supports(BlockState state) {
                return state.getBlock() instanceof TexturedChestBlock;
            }
        }
    );

    @Override
    public void onInitialize() {
        // nothing here
    }
}