package com.buuz135.industrialforegoingsouls.tag;

import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import com.hrznstudio.titanium.util.TagUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class SoulTags {

    public static class Blocks {

        public static final TagKey<Block> CANT_ACCELERATE = TagUtil.getBlockTag(ResourceLocation.fromNamespaceAndPath(IndustrialForegoingSouls.MOD_ID, "cant_accelerate"));
        public static final TagKey<Block> FORGE_CANT_ACCELERATE = TagUtil.getBlockTag(ResourceLocation.fromNamespaceAndPath("c", "tick_acceleration_disallowed"));
    }

    public static class Entities {

        public static final TagKey<EntityType<?>> CANT_ACCELERATE = TagUtil.getEntityTypeTag(ResourceLocation.fromNamespaceAndPath(IndustrialForegoingSouls.MOD_ID, "cant_accelerate"));
    }

    public static class Biomes {

        public static final TagKey<Biome> DEEP_DARK = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(IndustrialForegoingSouls.MOD_ID, "deep_dark"));
    }
}
