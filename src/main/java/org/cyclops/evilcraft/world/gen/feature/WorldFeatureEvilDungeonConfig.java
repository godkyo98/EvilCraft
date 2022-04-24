package org.cyclops.evilcraft.world.gen.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import org.cyclops.cyclopscore.config.extendedconfig.WorldFeatureConfig;
import org.cyclops.evilcraft.EvilCraft;

import java.util.List;

/**
 * Config for the evil dungeon.
 * @author rubensworks
 *
 */
public class WorldFeatureEvilDungeonConfig extends WorldFeatureConfig {

    public static Holder<ConfiguredFeature<?, ?>> CONFIGURED_FEATURE;
    public static Holder<PlacedFeature> PLACED_FEATURE;

    public WorldFeatureEvilDungeonConfig() {
        super(
                EvilCraft._instance,
                "evil_dungeon",
                eConfig -> new WorldFeatureEvilDungeon(NoneFeatureConfiguration.CODEC)
        );
        MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoadingEvent);
    }

    @Override
    public void onForgeRegistered() {
        super.onForgeRegistered();

        CONFIGURED_FEATURE = BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE,
                new ResourceLocation(getMod().getModId(), getNamedId() + "_default"),
                new ConfiguredFeature<>(((WorldFeatureEvilDungeon) getInstance()), FeatureConfiguration.NONE));
        PLACED_FEATURE = BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE,
                new ResourceLocation(getMod().getModId(), getNamedId() + "_default"),
                new PlacedFeature(CONFIGURED_FEATURE, List.of(
                        CountPlacement.of(10),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.top()),
                        BiomeFilter.biome())));
    }

    public void onBiomeLoadingEvent(BiomeLoadingEvent event) {
        if (event.getCategory() != Biome.BiomeCategory.THEEND && event.getCategory() != Biome.BiomeCategory.NETHER) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_STRUCTURES).add(PLACED_FEATURE);
        }
    }
}
