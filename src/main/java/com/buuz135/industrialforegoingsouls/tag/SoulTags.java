package com.buuz135.industrialforegoingsouls.tag;

import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import com.hrznstudio.titanium.util.TagUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class SoulTags {

    public static class Blocks {

        public static final TagKey<Block> CANT_ACCELERATE = TagUtil.getBlockTag(new ResourceLocation(IndustrialForegoingSouls.MOD_ID, "cant_accelerate"));
    }
}
