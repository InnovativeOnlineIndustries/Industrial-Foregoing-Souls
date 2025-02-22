package com.buuz135.industrialforegoingsouls.data;

import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import com.buuz135.industrialforegoingsouls.tag.SoulTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class IFSoulsTagProvider extends BlockTagsProvider {
    public IFSoulsTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(IndustrialForegoingSouls.SOUL_LASER_BLOCK.block().get())
                .add(IndustrialForegoingSouls.SOUL_PIPE_BLOCK.block().get())
                .add(IndustrialForegoingSouls.SOUL_SURGE_BLOCK.block().get());
        tag(SoulTags.Blocks.CANT_ACCELERATE).add(IndustrialForegoingSouls.SOUL_SURGE_BLOCK.block().get());
        tag(SoulTags.Blocks.FORGE_CANT_ACCELERATE).add(IndustrialForegoingSouls.SOUL_SURGE_BLOCK.block().get());
    }
}
