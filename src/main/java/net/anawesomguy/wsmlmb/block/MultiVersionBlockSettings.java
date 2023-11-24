package net.anawesomguy.wsmlmb.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.mixin.object.builder.AbstractBlockAccessor;
import net.fabricmc.fabric.mixin.object.builder.AbstractBlockSettingsAccessor;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
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
public class MultiVersionBlockSettings extends FabricBlockSettings {
    protected MultiVersionBlockSettings(String material) {
        super();
    }

    protected MultiVersionBlockSettings(AbstractBlock.Settings settings) {
        super(settings);
    }

    /**
     * @param material the material to make this settings out of. Must be a field of the {@code Materials} class. If the {@code Material}s class doesn't exist, this parameter is ignored.
     * @return a new instance of {@link MultiVersionBlockSettings}.
     */
    public static MultiVersionBlockSettings create(String material) {
        return new MultiVersionBlockSettings(material);
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

    @Override
    public MultiVersionBlockSettings noCollision() {
        super.noCollision();
        return this;
    }

    @Override
    public MultiVersionBlockSettings nonOpaque() {
        super.nonOpaque();
        return this;
    }

    @Override
    public MultiVersionBlockSettings slipperiness(float value) {
        super.slipperiness(value);
        return this;
    }

    @Override
    public MultiVersionBlockSettings velocityMultiplier(float velocityMultiplier) {
        super.velocityMultiplier(velocityMultiplier);
        return this;
    }

    @Override
    public MultiVersionBlockSettings jumpVelocityMultiplier(float jumpVelocityMultiplier) {
        super.jumpVelocityMultiplier(jumpVelocityMultiplier);
        return this;
    }

    @Override
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

    @Override
    public MultiVersionBlockSettings luminance(ToIntFunction<BlockState> luminanceFunction) {
        super.luminance(luminanceFunction);
        return this;
    }

    @Override
    public MultiVersionBlockSettings strength(float hardness, float resistance) {
        super.strength(hardness, resistance);
        return this;
    }

    @Override
    public MultiVersionBlockSettings breakInstantly() {
        super.breakInstantly();
        return this;
    }

    public MultiVersionBlockSettings strength(float strength) {
        super.strength(strength);
        return this;
    }

    @Override
    public MultiVersionBlockSettings ticksRandomly() {
        super.ticksRandomly();
        return this;
    }

    @Override
    public MultiVersionBlockSettings dynamicBounds() {
        super.dynamicBounds();
        return this;
    }

    @Override
    public MultiVersionBlockSettings dropsNothing() {
        super.dropsNothing();
        return this;
    }

    @Override
    public MultiVersionBlockSettings dropsLike(Block block) {
        super.dropsLike(block);
        return this;
    }

    @Override
    public MultiVersionBlockSettings air() {
        super.air();
        return this;
    }

    @Override
    public MultiVersionBlockSettings allowsSpawning(AbstractBlock.TypedContextPredicate<EntityType<?>> predicate) {
        super.allowsSpawning(predicate);
        return this;
    }

    @Override
    public MultiVersionBlockSettings solidBlock(AbstractBlock.ContextPredicate predicate) {
        super.solidBlock(predicate);
        return this;
    }

    @Override
    public MultiVersionBlockSettings suffocates(AbstractBlock.ContextPredicate predicate) {
        super.suffocates(predicate);
        return this;
    }

    @Override
    public MultiVersionBlockSettings blockVision(AbstractBlock.ContextPredicate predicate) {
        super.blockVision(predicate);
        return this;
    }

    @Override
    public MultiVersionBlockSettings postProcess(AbstractBlock.ContextPredicate predicate) {
        super.postProcess(predicate);
        return this;
    }

    @Override
    public MultiVersionBlockSettings emissiveLighting(AbstractBlock.ContextPredicate predicate) {
        super.emissiveLighting(predicate);
        return this;
    }

    /**
     * Make the block require tool to drop and slows down mining speed if the incorrect tool is used.
     */
    @Override
    public MultiVersionBlockSettings requiresTool() {
        super.requiresTool();
        return this;
    }

    @Override
    public MultiVersionBlockSettings mapColor(MapColor color) {
        super.mapColor(color);
        return this;
    }

    @Override
    public MultiVersionBlockSettings hardness(float hardness) {
        super.hardness(hardness);
        return this;
    }

    @Override
    public MultiVersionBlockSettings resistance(float resistance) {
        super.resistance(resistance);
        return this;
    }

    @Override
    public MultiVersionBlockSettings offset(AbstractBlock.OffsetType offsetType) {
        super.offset(offsetType);
        return this;
    }

    @Override
    public MultiVersionBlockSettings noBlockBreakParticles() {
        super.noBlockBreakParticles();
        return this;
    }

    @Override
    public MultiVersionBlockSettings requires(FeatureFlag... features) {
        super.requires(features);
        return this;
    }

    @Override
    public MultiVersionBlockSettings mapColor(Function<BlockState, MapColor> mapColorProvider) {
        super.mapColor(mapColorProvider);
        return this;
    }

    @Override
    public MultiVersionBlockSettings burnable() {
        super.burnable();
        return this;
    }

    @Override
    public MultiVersionBlockSettings liquid() {
        super.liquid();
        return this;
    }

    @Override
    public MultiVersionBlockSettings solid() {
        super.solid();
        return this;
    }

    @Override
    public MultiVersionBlockSettings notSolid() {
        super.notSolid();
        return this;
    }

    @Override
    public MultiVersionBlockSettings pistonBehavior(PistonBehavior pistonBehavior) {
        super.pistonBehavior(pistonBehavior);
        return this;
    }

    @Override
    public MultiVersionBlockSettings instrument(Instrument instrument) {
        super.instrument(instrument);
        return this;
    }

    @Override
    public MultiVersionBlockSettings replaceable() {
        super.replaceable();
        return this;
    }

    /* FABRIC ADDITIONS */

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
        ((AbstractBlockSettingsAccessor)this).setLootTableId(dropTableId);
        return this;
    }

    /* FABRIC DELEGATE WRAPPERS */

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
        ((AbstractBlockSettingsAccessor)this).setCollidable(collidable);
        return this;
    }
}