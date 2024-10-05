package com.buuz135.industrialforegoingsouls.data;

import com.buuz135.industrialforegoingsouls.tag.SoulTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class IFSoulsBiomeTagProvider extends BiomeTagsProvider {
    public IFSoulsBiomeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(SoulTags.Biomes.DEEP_DARK).add(Biomes.DEEP_DARK);
    }
}
