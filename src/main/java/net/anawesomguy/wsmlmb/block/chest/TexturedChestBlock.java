package net.anawesomguy.wsmlmb.block.chest;

import net.anawesomguy.wsmlmb.WSMLMB;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class TexturedChestBlock extends ChestBlock {
    private ChestTriple normalTextures = ChestTriple.getDefault();
    private ChestTriple christmasTextures = ChestTriple.getDefaultChristmas();

    public TexturedChestBlock(Settings settings) {
        super(settings, () -> WSMLMB.TEXTURED_CHEST_ENTITY_TYPE);
        WSMLMB.TEXTURED_CHESTS.add(this); // TODO: find a better way to run stuff on this on the client
    }

    @Override 
    public TexturedChestBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TexturedChestBlockEntity(pos, state, normalTextures, christmasTextures);
    }

    /**
     * Sets the textures of this chest (for when it is not Christmas). This will also set the {@link TexturedChestBlock#christmasTextures} if they have not been set already.
     * @param single an {@link Identifier} for the texture for this chest when it is by itself.
     * @param left an {@link Identifier} for the texture for the left side of the double chest.
     * @param right an {@link Identifier} for the texture for the right side of the double chest.
     * @return {@code this}, after the textures have been set.
     */
    public TexturedChestBlock setTextures(Identifier single, Identifier left, Identifier right) {
        normalTextures = new ChestTriple(single, left, right);
        if (christmasTextures.isDefaultChristmas())
            christmasTextures = new ChestTriple(single, left, right);
        return this;
    }

    /**
     * Sets the textures of this chest for when it is Christmas. This will also set the {@link TexturedChestBlock#normalTextures} if they have not been set already.
     * @param single an {@link Identifier} for the texture for this chest when it is by itself.
     * @param left an {@link Identifier} for the texture for the left side of the double chest.
     * @param right an {@link Identifier} for the texture for the right side of the double chest.
     * @return {@code this}, after the textures have been set.
     */
    public TexturedChestBlock setChristmasTextures(Identifier single, Identifier left, Identifier right) {
        christmasTextures = new ChestTriple(single, left, right);
        if (normalTextures.isDefault())
            normalTextures = new ChestTriple(single, left, right);
        return this;
    }

    public TexturedChestBlock setTextures(Identifier single, Identifier left, Identifier right, boolean christmas) {
        return christmas ? setChristmasTextures(single, left, right) : setTextures(single, left, right);
    }

    /**
     * @return the textures for this chest.
     */
    public ChestTriple getTextures() {
        return normalTextures;
    }

    /**
     * @return the textures for this chest, when it is Christmas.
     */
    public ChestTriple getChristmasTextures() {
        return christmasTextures;
    }

    public ChestTriple getTextures(boolean christmas) {
        return christmas ? christmasTextures : normalTextures;
    }
}