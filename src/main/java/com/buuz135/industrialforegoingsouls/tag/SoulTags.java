package com.buuz135.industrialforegoingsouls.tag;

import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import com.hrznstudio.titanium.util.TagUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

public class SoulTags {

    public static class Blocks {

        public static final TagKey<Block> CANT_ACCELERATE = TagUtil.getBlockTag(new ResourceLocation(IndustrialForegoingSouls.MOD_ID, "cant_accelerate"));
        public static final TagKey<Block> FORGE_CANT_ACCELERATE = TagUtil.getBlockTag(new ResourceLocation("forge", "tick_acceleration_disallowed"));
    }

    public static class Entities {

        public static final TagKey<EntityType<?>> CANT_ACCELERATE = TagUtil.getEntityTypeTag(new ResourceLocation(IndustrialForegoingSouls.MOD_ID, "cant_accelerate"));
    }
}
