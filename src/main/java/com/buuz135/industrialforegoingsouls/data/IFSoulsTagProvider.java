package com.buuz135.industrialforegoingsouls.data;

import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class IFSoulsTagProvider extends BlockTagsProvider {
    public IFSoulsTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(IndustrialForegoingSouls.SOUL_LASER_BLOCK.getKey().get())
                .add(IndustrialForegoingSouls.SOUL_PIPE_BLOCK.getKey().get())
                .add(IndustrialForegoingSouls.SOUL_SURGE_BLOCK.getKey().get());
    }
}
