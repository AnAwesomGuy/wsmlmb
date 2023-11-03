package net.anawesomguy.wsmlmb.block.chest;

import net.anawesomguy.wsmlmb.WSMLMB;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.ApiStatus.Internal;

public class TexturedChestBlockEntity extends ChestBlockEntity {
    private final ChestTriple christmasTextures;
    private final ChestTriple normalTextures;

    protected TexturedChestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, ChestTriple normalTextures, ChestTriple christmasTextures) {
        super(type, pos, state);
        this.normalTextures = normalTextures;
        this.christmasTextures = christmasTextures;
    }

    @Internal // don't use this unless you really know what you're doing
    public TexturedChestBlockEntity(BlockPos pos, BlockState state, ChestTriple normalTextures, ChestTriple christmasTextures) {
        this(WSMLMB.TEXTURED_CHEST_ENTITY_TYPE, pos, state, normalTextures, christmasTextures);
    }

    /**
     * @return a {@link ChestTriple} of the textures used for this chest when it is Christmas.
     */
    public ChestTriple getChristmasTextures() {
        return christmasTextures;
    }

    /**
     * @return a {@link ChestTriple} of the textures used for this chest when it is not Christmas.
     */
    public ChestTriple getTextures() {
        return normalTextures;
    }

    /**
     * @param christmas if the textures returned will be for Christmas.
     * @return a {@link ChestTriple} of the textures used for this chest.
     */
    public ChestTriple getTextures(boolean christmas) {
        return christmas ? christmasTextures : normalTextures;
    }
}
