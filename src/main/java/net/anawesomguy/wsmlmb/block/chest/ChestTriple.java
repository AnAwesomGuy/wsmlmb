package net.anawesomguy.wsmlmb.block.chest;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus.Internal;

import static java.util.Objects.requireNonNull;
import static net.minecraft.client.render.TexturedRenderLayers.*;

/**
 * Used for storing {@link Identifier}s for {@link TexturedChestBlock chest} textures.
 * <p>
 * The textures must be in the chest atlas, or in the {@code entity/chest} directory.
 *
 * @see TexturedChestBlock#getTextures(boolean)
 */
public record ChestTriple(Identifier singleTexture, Identifier leftTexture, Identifier rightTexture) {
    public static final Codec<ChestTriple> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Identifier.CODEC.fieldOf("single").forGetter(ChestTriple::getSingle),
            Identifier.CODEC.fieldOf("left").forGetter(ChestTriple::getLeft),
            Identifier.CODEC.fieldOf("right").forGetter(ChestTriple::getRight)
        ).apply(instance, ChestTriple::new)
    );
    /**
     * A {@link ChestTriple} storing the default textures for a chest.
     */
    public static final ChestTriple DEFAULT_TEXTURES = new ChestTriple(
        new Identifier("entity/chest/normal"),
        new Identifier("entity/chest/normal_left"),
        new Identifier("entity/chest/normal_right")
    );
    /**
     * A {@link ChestTriple} storing the default textures for a chest when it is Christmas.
     */
    public static final ChestTriple DEFAULT_CHRISTMAS_TEXTURES = new ChestTriple(
        new Identifier("entity/chest/christmas"),
        new Identifier("entity/chest/christmas_left"),
        new Identifier("entity/chest/christmas_right")
    );

    public static ChestTriple getDefault() {
        return DEFAULT_TEXTURES;
    }

    public static ChestTriple getDefaultChristmas() {
        return DEFAULT_CHRISTMAS_TEXTURES;
    }

    public static ChestTriple getDefault(boolean christmas) {
        return christmas ? DEFAULT_CHRISTMAS_TEXTURES : DEFAULT_TEXTURES;
    }

    @Internal
    public ChestTriple(Identifier singleTexture, Identifier leftTexture, Identifier rightTexture) {
        this.singleTexture = requireNonNull(singleTexture, "singleTexture cannot be null");
        this.leftTexture = requireNonNull(leftTexture, "leftTexture cannot be null");
        this.rightTexture = requireNonNull(rightTexture, "rightTexture cannot be null");
    }

    public Identifier getSingle() {
        return this.singleTexture;
    }

    public Identifier getLeft() {
        return this.leftTexture;
    }

    public Identifier getRight() {
        return this.rightTexture;
    }

    public boolean isDefault() {
        return this.equals(DEFAULT_TEXTURES);
    }

    public boolean isDefaultChristmas() {
        return this.equals(DEFAULT_CHRISTMAS_TEXTURES);
    }

    public boolean isDefault(boolean christmas) {
        return this.equals(christmas ? DEFAULT_TEXTURES : DEFAULT_CHRISTMAS_TEXTURES);
    }

    public Sprite toSpriteIdentifiers() {
        return new Sprite(singleTexture, leftTexture, rightTexture);
    }

    /**
     * Used for storing {@link SpriteIdentifier}s for {@linkplain TexturedChestBlockEntity chest block entity} textures.
     * <p>
     * The textures should be in the {@linkplain net.minecraft.client.render.TexturedRenderLayers#CHEST_ATLAS_TEXTURE chest atlas}.
     */
    public record Sprite(SpriteIdentifier singleTexture, SpriteIdentifier leftTexture, SpriteIdentifier rightTexture) {
        public static final Sprite DEFAULT_TEXTURES =
            new Sprite(NORMAL, NORMAL_LEFT, NORMAL_RIGHT);
        public static final Sprite DEFAULT_CHRISTMAS_TEXTURES =
            new Sprite(CHRISTMAS, CHRISTMAS_LEFT, CHRISTMAS_RIGHT);

        public static Sprite getDefault() {
            return DEFAULT_TEXTURES;
        }

        public static Sprite getDefaultChristmas() {
            return DEFAULT_CHRISTMAS_TEXTURES;
        }

        public static Sprite getDefault(boolean christmas) {
            return christmas ? DEFAULT_CHRISTMAS_TEXTURES : DEFAULT_TEXTURES;
        }

        @Internal
        public Sprite(SpriteIdentifier singleTexture, SpriteIdentifier leftTexture, SpriteIdentifier rightTexture) {
            this.singleTexture = requireNonNull(singleTexture, "singleTexture cannot be null");
            this.leftTexture = requireNonNull(leftTexture, "leftTexture cannot be null");
            this.rightTexture = requireNonNull(rightTexture, "rightTexture cannot be null");
        }

        @Internal
        public Sprite(Identifier singleTexture, Identifier leftTexture, Identifier rightTexture) {
            this(
                new SpriteIdentifier(CHEST_ATLAS_TEXTURE, requireNonNull(singleTexture, "singleTexture cannot be null")),
                new SpriteIdentifier(CHEST_ATLAS_TEXTURE, requireNonNull(leftTexture, "leftTexture cannot be null")),
                new SpriteIdentifier(CHEST_ATLAS_TEXTURE, requireNonNull(rightTexture, "rightTexture cannot be null"))
            );
        }

        public SpriteIdentifier getSingle() {
            return this.singleTexture;
        }

        public SpriteIdentifier getLeft() {
            return this.leftTexture;
        }

        public SpriteIdentifier getRight() {
            return this.rightTexture;
        }

        public boolean isDefault() {
            return this.equals(DEFAULT_TEXTURES);
        }

        public boolean isDefaultChristmas() {
            return this.equals(DEFAULT_CHRISTMAS_TEXTURES);
        }

        public boolean isDefault(boolean christmas) {
            return this.equals(christmas ? DEFAULT_TEXTURES : DEFAULT_CHRISTMAS_TEXTURES);
        }

        public ChestTriple toIdentifiers() {
            return new ChestTriple(singleTexture.getTextureId(), leftTexture.getTextureId(), rightTexture.getTextureId());
        }
    }
}