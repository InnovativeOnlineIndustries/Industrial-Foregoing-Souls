package com.buuz135.industrialforegoingsouls.data;

import com.buuz135.industrial.utils.IndustrialTags;
import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;


public class IFSoulsRecipeProvider extends RecipeProvider {
    public IFSoulsRecipeProvider(DataGenerator generatorIn, CompletableFuture<HolderLookup.Provider> prov) {
        super(generatorIn.getPackOutput(), prov);
    }

    @Override
    public void buildRecipes(RecipeOutput consumer) {
        //TitaniumShapedRecipeBuilder.shapedRecipe()
        TitaniumShapedRecipeBuilder.shapedRecipe(IndustrialForegoingSouls.SOUL_LASER_BLOCK.block().get())
                .pattern("ABA")
                .pattern("CDC")
                .pattern("GSG")
                .define('A', IndustrialTags.Items.PLASTIC)
                .define('B', Blocks.SCULK_SHRIEKER)
                .define('C', Items.IRON_BARS)
                .define('D', IndustrialTags.Items.MACHINE_FRAME_ADVANCED)
                .define('G', IndustrialTags.Items.GEAR_DIAMOND)
                .define('S', Items.SCULK_CATALYST)
                .save(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(IndustrialForegoingSouls.SOUL_PIPE_BLOCK.block().get(), 8)
                .pattern("SSS")
                .pattern("CDC")
                .pattern("SSS")
                .define('C', IndustrialTags.Items.PLASTIC)
                .define('D', Items.ECHO_SHARD)
                .define('S', Tags.Items.STONES)
                .save(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(IndustrialForegoingSouls.SOUL_SURGE_BLOCK.block().get())
                .pattern("ABA")
                .pattern("CBC")
                .pattern("CPC")
                .define('B', IndustrialTags.Items.PLASTIC)
                .define('A', Tags.Items.STONES)
                .define('C', Items.ECHO_SHARD)
                .define('P', Items.PISTON)
                .save(consumer);

        IFSoulsSerializableRecipe.init(consumer);
    }
}
