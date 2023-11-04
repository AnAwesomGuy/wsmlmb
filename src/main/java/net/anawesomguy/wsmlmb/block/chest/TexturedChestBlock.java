package net.anawesomguy.wsmlmb.block.chest;

import net.anawesomguy.wsmlmb.WSMLMB;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public class TexturedChestBlock extends ChestBlock {
    @NotNull
    private final ChestTriple normalTextures;
    @NotNull
    private final ChestTriple christmasTextures;

    public TexturedChestBlock(Settings settings, ChestTriple normalTextures, ChestTriple christmasTextures) {
        super(settings, () -> WSMLMB.TEXTURED_CHEST_ENTITY_TYPE);
        this.normalTextures = normalTextures == null ? (christmasTextures == null ? ChestTriple.getDefault() : christmasTextures) : normalTextures;
        this.christmasTextures = christmasTextures == null ? (normalTextures == null ? ChestTriple.getDefaultChristmas() : normalTextures) : christmasTextures;
        WSMLMB.TEXTURED_CHESTS.add(this); // TODO: find a better way to run stuff on this on the client
    }

    @Override 
    public TexturedChestBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TexturedChestBlockEntity(pos, state, normalTextures, christmasTextures);
    }

    /**
     * @return the textures for this chest.
     */
    public @NotNull ChestTriple getTextures() {
        return normalTextures;
    }

    /**
     * @return the textures for this chest, when it is Christmas.
     */
    public @NotNull ChestTriple getChristmasTextures() {
        return christmasTextures;
    }

    public @NotNull ChestTriple getTextures(boolean christmas) {
        return christmas ? christmasTextures : normalTextures;
    }

    /**
     * A builder class for {@link TexturedChestBlock}, since blocks should be immutable.
     * <p>
     * You can use the {@link TexturedChestBlock#TexturedChestBlock(Settings, ChestTriple, ChestTriple) constructor} manually, but this is the recommended way.
     */
    public static class Builder {
        private final Settings settings;
        private ChestTriple normalTextures;
        private ChestTriple christmasTextures;

        public Builder(Settings settings) {
            this.settings = settings;
        }

        /**
         * Sets the textures of this chest (for when it is not Christmas). This will also set the {@link TexturedChestBlock#christmasTextures} if they have not been set already.
         * @param single an {@link Identifier} for the texture for this chest when it is by itself.
         * @param left an {@link Identifier} for the texture for the left side of the double chest.
         * @param right an {@link Identifier} for the texture for the right side of the double chest.
         * @return {@code this}, after the textures have been set.
         */
        public Builder setTextures(Identifier single, Identifier left, Identifier right) {
            normalTextures = new ChestTriple(single, left, right);
            return this;
        }

        /**
         * Sets the textures of this chest for when it is Christmas. This will also set the {@link TexturedChestBlock#normalTextures} if they have not been set already.
         * @param single an {@link Identifier} for the texture for this chest when it is by itself.
         * @param left an {@link Identifier} for the texture for the left side of the double chest.
         * @param right an {@link Identifier} for the texture for the right side of the double chest.
         * @return {@code this}, after the textures have been set.
         */
        public Builder setChristmasTextures(Identifier single, Identifier left, Identifier right) {
            christmasTextures = new ChestTriple(single, left, right);
            return this;
        }

        public Builder setTextures(Identifier single, Identifier left, Identifier right, boolean christmas) {
            return christmas ? setChristmasTextures(single, left, right) : setTextures(single, left, right);
        }

        public TexturedChestBlock build() {
            return new TexturedChestBlock(settings, normalTextures, christmasTextures);
        }
    }
}