package net.anawesomguy.wsmlmb.block.chest;

import net.minecraft.util.Identifier;

import static java.util.Objects.requireNonNull;

/**
 * Used for storing {@link Identifier}s for {@link TexturedChestBlock chest} textures.
 *
 * @see TexturedChestBlock#getTextures(boolean)
 * @see TexturedChestBlockEntity#getTextures(boolean)
 */
public record ChestTriple(Identifier singleTexture, Identifier leftTexture, Identifier rightTexture) {
    /**
     * A {@link ChestTriple} storing the default textures for a chest.
     */
    private static final ChestTriple DEFAULT_TEXTURES = new ChestTriple(
        new Identifier("entity/chest/normal"),
        new Identifier("entity/chest/left"),
        new Identifier("entity/chest/right")
    );
    /**
     * A {@link ChestTriple} storing the default textures for a chest when it is christmas.
     */
    private static final ChestTriple DEFAULT_CHRISTMAS_TEXTURES = new ChestTriple(
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

    public ChestTriple(Identifier singleTexture, Identifier leftTexture, Identifier rightTexture) {
        this.singleTexture = requireNonNull(singleTexture, "singleTexture cannot be null");
        this.leftTexture = requireNonNull(leftTexture, "leftTexture cannot be null");
        this.rightTexture = requireNonNull(rightTexture, "rightTexture cannot be null");
    }

    public Identifier getSingle() {
        return singleTexture;
    }

    public Identifier getLeft() {
        return leftTexture;
    }

    public Identifier getRight() {
        return rightTexture;
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
}