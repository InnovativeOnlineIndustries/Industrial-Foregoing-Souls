package com.buuz135.industrialforegoingsouls.data;

import com.buuz135.industrial.recipe.LaserDrillOreRecipe;
import com.buuz135.industrial.recipe.LaserDrillRarity;
import com.hrznstudio.titanium.recipe.generator.IJSONGenerator;
import com.hrznstudio.titanium.recipe.generator.IJsonFile;
import com.hrznstudio.titanium.recipe.generator.TitaniumSerializableProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.biome.Biomes;

import java.util.Map;

public class IFSoulsSerializableRecipe extends TitaniumSerializableProvider {
    public IFSoulsSerializableRecipe(DataGenerator generatorIn, String modid) {
        super(generatorIn, modid);
    }

    @Override
    public void add(Map<IJsonFile, IJSONGenerator> serializables) {
        var rarity = new ResourceKey[]{Biomes.DEEP_DARK};
        var laser = new LaserDrillOreRecipe("echo_shard", Ingredient.of(Items.ECHO_SHARD), 11, null, new LaserDrillRarity(rarity, new ResourceKey[0], -64, 20, 1));
        serializables.put(laser, laser);
    }
}
