package net.anawesomguy.wsmlmb.block.chest;

import net.anawesomguy.wsmlmb.WSMLMB;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.ApiStatus.Internal;

public class TexturedChestBlockEntity extends ChestBlockEntity {
    private final ChestTriple.Sprite christmasTextures;
    private final ChestTriple.Sprite normalTextures;

    protected TexturedChestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, ChestTriple.Sprite normalTextures, ChestTriple.Sprite christmasTextures) {
        super(type, pos, state);
        this.normalTextures = normalTextures;
        this.christmasTextures = christmasTextures;
    }

    @Internal // don't use this unless you really know what you're doing
    public TexturedChestBlockEntity(BlockPos pos, BlockState state, ChestTriple.Sprite normalTextures, ChestTriple.Sprite christmasTextures) {
        this(WSMLMB.TEXTURED_CHEST_ENTITY_TYPE, pos, state, normalTextures, christmasTextures);
    }

    /**
     * @return the textures used for this chest when it is Christmas.
     */
    public ChestTriple.Sprite getChristmasTextures() {
        return christmasTextures;
    }

    /**
     * @return the textures used for this chest when it is not Christmas.
     */
    public ChestTriple.Sprite getTextures() {
        return normalTextures;
    }

    /**
     * @param christmas if the textures returned will be for Christmas.
     * @return a {@link ChestTriple} of the textures used for this chest.
     */
    public ChestTriple.Sprite getTextures(boolean christmas) {
        return christmas ? christmasTextures : normalTextures;
    }
}
