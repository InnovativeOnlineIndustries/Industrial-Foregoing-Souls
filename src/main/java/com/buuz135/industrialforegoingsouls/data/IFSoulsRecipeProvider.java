package com.buuz135.industrialforegoingsouls.data;

import com.buuz135.industrial.utils.IndustrialTags;
import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import com.hrznstudio.titanium.recipe.generator.TitaniumRecipeProvider;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;


public class IFSoulsRecipeProvider extends TitaniumRecipeProvider {
    public IFSoulsRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public void register(Consumer<FinishedRecipe> consumer) {
        //TitaniumShapedRecipeBuilder.shapedRecipe()
        TitaniumShapedRecipeBuilder.shapedRecipe(IndustrialForegoingSouls.SOUL_LASER_BLOCK.getKey().get())
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
        TitaniumShapedRecipeBuilder.shapedRecipe(IndustrialForegoingSouls.SOUL_PIPE_BLOCK.getKey().get(), 8)
                .pattern("SSS")
                .pattern("CDC")
                .pattern("SSS")
                .define('C', IndustrialTags.Items.PLASTIC)
                .define('D', Items.ECHO_SHARD)
                .define('S', Tags.Items.STONE)
                .save(consumer);
        TitaniumShapedRecipeBuilder.shapedRecipe(IndustrialForegoingSouls.SOUL_SURGE_BLOCK.getKey().get())
                .pattern("ABA")
                .pattern("CBC")
                .pattern("CPC")
                .define('B', IndustrialTags.Items.PLASTIC)
                .define('A', Tags.Items.STONE)
                .define('C', Items.ECHO_SHARD)
                .define('P', Items.PISTON)
                .save(consumer);
    }
}
