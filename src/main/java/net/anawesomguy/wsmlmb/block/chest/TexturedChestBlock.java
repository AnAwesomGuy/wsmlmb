package net.anawesomguy.wsmlmb.block.chest;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.anawesomguy.wsmlmb.WSMLMB;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public class TexturedChestBlock extends ChestBlock {
    public static final MapCodec<TexturedChestBlock> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            createSettingsCodec(),
            ChestTriple.CODEC
                       .optionalFieldOf("normal_textures", null)
                       .forGetter(TexturedChestBlock::getTextures),
            ChestTriple.CODEC
                       .optionalFieldOf("christmas_textures", null)
                       .forGetter(TexturedChestBlock::getChristmasTextures)
        ).apply(instance, TexturedChestBlock::new)
    );

    @NotNull
    private final ChestTriple normalTextures;
    @NotNull
    private final ChestTriple christmasTextures;

    public TexturedChestBlock(Settings settings, ChestTriple normalTextures, ChestTriple christmasTextures) {
        super(settings, () -> WSMLMB.TEXTURED_CHEST_ENTITY_TYPE);
        boolean noNormal = (normalTextures == null), noChristmas = (christmasTextures == null);
        if (noNormal && noChristmas)
            WSMLMB.LOGGER.warn("A TexturedChestBlock was created without any textures! Applying default textures.");
        this.normalTextures = noNormal ? (noChristmas ? ChestTriple.DEFAULT_TEXTURES : christmasTextures) : normalTextures;
        this.christmasTextures = noChristmas ? (noNormal ? ChestTriple.DEFAULT_CHRISTMAS_TEXTURES : normalTextures) : christmasTextures;
        WSMLMB.TEXTURED_CHESTS.add(this); // TODO: find a better way to run stuff on this on the client
    }

    @Override 
    public TexturedChestBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TexturedChestBlockEntity(pos, state, normalTextures.toSpriteIdentifiers(), christmasTextures.toSpriteIdentifiers());
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

//    @Override // this is bcuz this doesnt override in earlier versions
    public MapCodec<? extends ChestBlock> getCodec() {
        return CODEC;
    }

    /**
     * A builder class for {@link TexturedChestBlock}, since blocks should be immutable.
     * <p>
     * You can use the {@linkplain TexturedChestBlock#TexturedChestBlock(Settings, ChestTriple, ChestTriple) constructor} manually, but this is the recommended way.
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
         * <p>
         * The textures must be in the chest atlas, or in the {@code entity/chest} directory.
         *
         * @param single an {@link Identifier} for the texture for this chest when it is by itself
         * @param left an {@link Identifier} for the texture for the left side of the double chest
         * @param right an {@link Identifier} for the texture for the right side of the double chest
         * @return {@code this}, after the textures have been set.
         */
        public Builder setTextures(Identifier single, Identifier left, Identifier right) {
            normalTextures = new ChestTriple(single, left, right);
            return this;
        }

        /**
         * Sets the textures of this chest for when it is Christmas. This will also set the {@link TexturedChestBlock#normalTextures} if they have not been set already.
         * <p>
         * The textures must be in the chest atlas, or in the {@code entity/chest} directory.
         *
         * @param single an {@link Identifier} for the texture for this chest when it is by itself
         * @param left an {@link Identifier} for the texture for the left side of the double chest
         * @param right an {@link Identifier} for the texture for the right side of the double chest
         * @return {@code this}, after the textures have been set.
         */
        public Builder setChristmasTextures(Identifier single, Identifier left, Identifier right) {
            christmasTextures = new ChestTriple(single, left, right);
            return this;
        }

        /**
         * Sets the textures of this chest for when it is Christmas. This will also set the {@link TexturedChestBlock#normalTextures} if they have not been set already.
         * <p>
         * The textures must be in the chest atlas, or in the {@code entity/chest} directory.
         *
         * @param single an {@link Identifier} for the texture for this chest when it is by itself
         * @param left an {@link Identifier} for the texture for the left side of the double chest
         * @param right an {@link Identifier} for the texture for the right side of the double chest
         * @param christmas whether the textures to be set will be the ones for on Christmas
         * @return {@code this}, after the textures have been set.
         */
        public Builder setTextures(Identifier single, Identifier left, Identifier right, boolean christmas) {
            return christmas ? setChristmasTextures(single, left, right) : setTextures(single, left, right);
        }

        /**
         * @return a new instance of {@link TexturedChestBlock} using this builder's settings and textures.
         */
        public TexturedChestBlock build() {
            return new TexturedChestBlock(settings, normalTextures, christmasTextures);
        }
    }
}