package com.buuz135.industrialforegoingsouls.data;

import com.buuz135.industrial.recipe.LaserDrillOreRecipe;
import com.buuz135.industrial.recipe.LaserDrillRarity;
import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import com.buuz135.industrialforegoingsouls.tag.SoulTags;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class IFSoulsSerializableRecipe {

    public static void init(RecipeOutput output) {
        var biomeRarity = new LaserDrillRarity.BiomeRarity(List.of(SoulTags.Biomes.DEEP_DARK), List.of());
        var dimensionRarity = new LaserDrillRarity.DimensionRarity(List.of(), List.of());
        var laser = new LaserDrillOreRecipe(Ingredient.of(Items.ECHO_SHARD), 11,
                new LaserDrillRarity(biomeRarity, dimensionRarity, -64, 20, 1));
        ResourceLocation rl = ResourceLocation.fromNamespaceAndPath(IndustrialForegoingSouls.MOD_ID, "laser_drill_ore/echo_shard");
        AdvancementHolder advancementHolder = output.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(rl)).rewards(AdvancementRewards.Builder.recipe(rl)).requirements(AdvancementRequirements.Strategy.OR).build(rl);
        output.accept(rl, laser, advancementHolder);
    }
}
