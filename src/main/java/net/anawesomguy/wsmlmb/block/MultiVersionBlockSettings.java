package net.anawesomguy.wsmlmb.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.mixin.object.builder.AbstractBlockAccessor;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.function.Function;
import java.util.function.ToIntFunction;

/**
 * A custom class for {@link AbstractBlock.Settings} for support over multiple versions. If the method doesn't exist for that version, nothing will happen.
 */
@SuppressWarnings({"deprecation", "UnstableApiUsage"})
public final class MultiVersionBlockSettings extends FabricBlockSettings {
    /**
     * @param material the material to make this settings out of. Must be a field of the {@code Material}s class. If the {@code Material}s class doesn't exist, this parameter is ignored.
     * @return a new instance of {@link MultiVersionBlockSettings}.
     */
    public static MultiVersionBlockSettings create(String material) {
        String materialString = material.replace(' ', '_').toUpperCase();
        Material materialMaterial = switch (materialString) {
            case "AIR" -> Material.AIR;
            case "STRUCTURE_VOID" -> Material.STRUCTURE_VOID;
            case "PORTAL" -> Material.PORTAL;
            case "CARPET" -> Material.CARPET;
            case "PLANT" -> Material.PLANT;
            case "UNDERWATER_PLANT" -> Material.UNDERWATER_PLANT;
            case "REPLACEABLE_PLANT" -> Material.REPLACEABLE_PLANT;
            case "NETHER_SHOOTS" -> Material.NETHER_SHOOTS;
            case "REPLACEABLE_UNDERWATER_PLANT" -> Material.REPLACEABLE_UNDERWATER_PLANT;
            case "WATER" -> Material.WATER;
            case "BUBBLE_COLUMN" -> Material.BUBBLE_COLUMN;
            case "LAVA" -> Material.LAVA;
            case "SNOW_LAYER" -> Material.SNOW_LAYER;
            case "FIRE" -> Material.FIRE;
            case "DECORATION" -> Material.DECORATION;
            case "COBWEB" -> Material.COBWEB;
            case "SCULK" -> Material.SCULK;
            case "REDSTONE_LAMP" -> Material.REDSTONE_LAMP;
            case "ORGANIC_PRODUCT" -> Material.ORGANIC_PRODUCT;
            case "SOIL" -> Material.SOIL;
            case "SOLID_ORGANIC" -> Material.SOLID_ORGANIC;
            case "DENSE_ICE" -> Material.DENSE_ICE;
            case "AGGREGATE" -> Material.AGGREGATE;
            case "SPONGE" -> Material.SPONGE;
            case "SHULKER_BOX" -> Material.SHULKER_BOX;
            case "WOOD" -> Material.WOOD;
            case "NETHER_WOOD" -> Material.NETHER_WOOD;
            case "BAMBOO_SAPLING" -> Material.BAMBOO_SAPLING;
            case "BAMBOO" -> Material.BAMBOO;
            case "WOOL" -> Material.WOOL;
            case "TNT" -> Material.TNT;
            case "LEAVES" -> Material.LEAVES;
            case "GLASS" -> Material.GLASS;
            case "ICE" -> Material.ICE;
            case "CACTUS" -> Material.CACTUS;
            case "STONE" -> Material.STONE;
            case "METAL" -> Material.METAL;
            case "SNOW_BLOCK" -> Material.SNOW_BLOCK;
            case "REPAIR_STATION" -> Material.REPAIR_STATION;
            case "BARRIER" -> Material.BARRIER;
            case "PISTON" -> Material.PISTON;
            case "MOSS_BLOCK" -> Material.MOSS_BLOCK;
            case "GOURD" -> Material.GOURD;
            case "EGG" -> Material.EGG;
            case "CAKE" -> Material.CAKE;
            case "AMETHYST" -> Material.AMETHYST;
            case "POWDER_SNOW" -> Material.POWDER_SNOW;
            case "FROGSPAWN" -> Material.FROGSPAWN;
            case "FROGLIGHT" -> Material.FROGLIGHT;
            case "DECORATED_POT" -> Material.DECORATED_POT;
            default -> throw new IllegalArgumentException(material);
        };
        return new MultiVersionBlockSettings(materialMaterial, materialMaterial.getColor());
    }

    /**
     * @see MultiVersionBlockSettings#create(String)
     */
    public static MultiVersionBlockSettings of(String material) {
        return create(material);
    }

    public static MultiVersionBlockSettings copyOf(AbstractBlock block) {
        return new MultiVersionBlockSettings(((AbstractBlockAccessor)block).getSettings());
    }

    public static MultiVersionBlockSettings copyOf(AbstractBlock.Settings settings) {
        return new MultiVersionBlockSettings(settings);
    }

    /**
     * Be careful when using this constructor, as it does not exist on versions above 1.19.4. (23w16a to be exact)
     */
    public MultiVersionBlockSettings(Material material, MapColor mapColor) {
        super(material, mapColor);
    }

    /**
     * Be careful when using this constructor, as it does not exist on versions above 1.19.4. (23w16a to be exact)
     */
    public MultiVersionBlockSettings(Material material, Function<BlockState, MapColor> mapColorProvider) {
        super(material, mapColorProvider);
    }

    private MultiVersionBlockSettings(AbstractBlock.Settings settings) {
        super(settings);
    }

    public MultiVersionBlockSettings noCollision() {
        super.noCollision();
        return this;
    }

    public MultiVersionBlockSettings nonOpaque() {
        super.nonOpaque();
        return this;
    }

    public MultiVersionBlockSettings slipperiness(float value) {
        super.slipperiness(value);
        return this;
    }

    public MultiVersionBlockSettings velocityMultiplier(float velocityMultiplier) {
        super.velocityMultiplier(velocityMultiplier);
        return this;
    }

    public MultiVersionBlockSettings jumpVelocityMultiplier(float jumpVelocityMultiplier) {
        super.jumpVelocityMultiplier(jumpVelocityMultiplier);
        return this;
    }

    public MultiVersionBlockSettings sounds(BlockSoundGroup group) {
        super.sounds(group);
        return this;
    }

    /**
     * @deprecated Please use {@link MultiVersionBlockSettings#luminance(ToIntFunction)}.
     */
    @Deprecated
    public MultiVersionBlockSettings lightLevel(ToIntFunction<BlockState> levelFunction) {
        return this.luminance(levelFunction);
    }


    public MultiVersionBlockSettings luminance(ToIntFunction<BlockState> luminanceFunction) {
        super.luminance(luminanceFunction);
        return this;
    }


    public MultiVersionBlockSettings strength(float hardness, float resistance) {
        super.strength(hardness, resistance);
        return this;
    }


    public MultiVersionBlockSettings breakInstantly() {
        super.breakInstantly();
        return this;
    }

    public MultiVersionBlockSettings strength(float strength) {
        super.strength(strength);
        return this;
    }

    public MultiVersionBlockSettings ticksRandomly() {
        super.ticksRandomly();
        return this;
    }

    public MultiVersionBlockSettings dynamicBounds() {
        super.dynamicBounds();
        return this;
    }

    public MultiVersionBlockSettings dropsNothing() {
        super.dropsNothing();
        return this;
    }

    public MultiVersionBlockSettings dropsLike(Block block) {
        super.dropsLike(block);
        return this;
    }

    public MultiVersionBlockSettings air() {
        super.air();
        return this;
    }

    public MultiVersionBlockSettings allowsSpawning(AbstractBlock.TypedContextPredicate<EntityType<?>> predicate) {
        super.allowsSpawning(predicate);
        return this;
    }

    public MultiVersionBlockSettings solidBlock(AbstractBlock.ContextPredicate predicate) {
        super.solidBlock(predicate);
        return this;
    }

    public MultiVersionBlockSettings suffocates(AbstractBlock.ContextPredicate predicate) {
        super.suffocates(predicate);
        return this;
    }

    public MultiVersionBlockSettings blockVision(AbstractBlock.ContextPredicate predicate) {
        super.blockVision(predicate);
        return this;
    }

    public MultiVersionBlockSettings postProcess(AbstractBlock.ContextPredicate predicate) {
        super.postProcess(predicate);
        return this;
    }

    public MultiVersionBlockSettings emissiveLighting(AbstractBlock.ContextPredicate predicate) {
        super.emissiveLighting(predicate);
        return this;
    }

    /**
     * Make the block require tool to drop and slows down mining speed if the incorrect tool is used.
     */
    public MultiVersionBlockSettings requiresTool() {
        super.requiresTool();
        return this;
    }

    public MultiVersionBlockSettings mapColor(MapColor color) {
        super.mapColor(color);
        return this;
    }

    public MultiVersionBlockSettings hardness(float hardness) {
        super.hardness(hardness);
        return this;
    }

    public MultiVersionBlockSettings resistance(float resistance) {
        super.resistance(resistance);
        return this;
    }

    public MultiVersionBlockSettings offset(AbstractBlock.OffsetType offsetType) {
        super.offset(offsetType);
        return this;
    }


    public MultiVersionBlockSettings noBlockBreakParticles() {
        super.noBlockBreakParticles();
        return this;
    }

    /**
     * @deprecated Please use {@link MultiVersionBlockSettings#luminance(int)}.
     */
    @Deprecated
    public MultiVersionBlockSettings lightLevel(int lightLevel) {
        this.luminance(lightLevel);
        return this;
    }

    public MultiVersionBlockSettings luminance(int luminance) {
        this.luminance(ignored -> luminance);
        return this;
    }

    public MultiVersionBlockSettings drops(Identifier dropTableId) {
        super.drops(dropTableId);
        return this;
    }

    /**
     * @deprecated Please migrate to {@link MultiVersionBlockSettings#mapColor(MapColor)}
     */
    @Deprecated
    public MultiVersionBlockSettings materialColor(MapColor color) {
        return this.mapColor(color);
    }

    /**
     * @deprecated Please migrate to {@link MultiVersionBlockSettings#mapColor(DyeColor)}
     */
    @Deprecated
    public MultiVersionBlockSettings materialColor(DyeColor color) {
        return this.mapColor(color);
    }

    public MultiVersionBlockSettings mapColor(DyeColor color) {
        return this.mapColor(color.getMapColor());
    }

    public MultiVersionBlockSettings collidable(boolean collidable) {
        super.collidable(collidable);
        return this;
    }

    public MultiVersionBlockSettings requires(Object... features) {
        super.requires((FeatureFlag[])features);
        return this;
    }

    public MultiVersionBlockSettings mapColor(Function<BlockState, MapColor> mapColorProvider) {
        return this;
    }

    /* FABRIC ADDITIONS */

    public MultiVersionBlockSettings burnable() {
        return this;
    }

    public MultiVersionBlockSettings liquid() {
        return this;
    }

    public MultiVersionBlockSettings solid() {
        return this;
    }

    /* FABRIC DELEGATE WRAPPERS */

    public MultiVersionBlockSettings notSolid() {
        return this;
    }

    public MultiVersionBlockSettings pistonBehavior(PistonBehavior pistonBehavior) {
        return this;
    }

    public MultiVersionBlockSettings instrument(Instrument instrument) {
        return this;
    }

    public MultiVersionBlockSettings replaceable() {
        return this;
    }
}