package com.buuz135.industrialforegoingsouls;

import com.buuz135.industrialforegoingsouls.block.*;
import com.buuz135.industrialforegoingsouls.block_network.DefaultSoulNetworkElement;
import com.buuz135.industrialforegoingsouls.block_network.SoulNetwork;
import com.buuz135.industrialforegoingsouls.data.*;
import com.hrznstudio.titanium.block_network.NetworkRegistry;
import com.hrznstudio.titanium.block_network.element.NetworkElementRegistry;
import com.hrznstudio.titanium.datagenerator.loot.TitaniumLootTableProvider;
import com.hrznstudio.titanium.datagenerator.model.BlockItemModelGeneratorProvider;
import com.hrznstudio.titanium.module.ModuleController;
import com.hrznstudio.titanium.network.NetworkHandler;
import com.hrznstudio.titanium.tab.TitaniumTab;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(IndustrialForegoingSouls.MOD_ID)
public class IndustrialForegoingSouls extends ModuleController {

    public final static String MOD_ID = "industrialforegoingsouls";
    public static NetworkHandler NETWORK = new NetworkHandler(MOD_ID);
    public static TitaniumTab TAB = new TitaniumTab(new ResourceLocation(MOD_ID, "main"));

    public static Pair<RegistryObject<Block>, RegistryObject<BlockEntityType<?>>> SOUL_LASER_BLOCK = null;
    public static Pair<RegistryObject<Block>, RegistryObject<BlockEntityType<?>>> SOUL_PIPE_BLOCK = null;
    public static Pair<RegistryObject<Block>, RegistryObject<BlockEntityType<?>>> SOUL_SURGE_BLOCK = null;

    public static Pair<RegistryObject<Block>, RegistryObject<BlockEntityType<?>>> PHANTOM_SOUL_DRIVE_CONTROLLER = null;
    public static Pair<RegistryObject<Block>, RegistryObject<BlockEntityType<?>>> DRIVE_FRAME_BLOCK = null;

    public IndustrialForegoingSouls() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> this::onClient);
        NetworkRegistry.INSTANCE.addFactory(SoulNetwork.SOUL_NETWORK, new SoulNetwork.Factory());
        NetworkElementRegistry.INSTANCE.addFactory(DefaultSoulNetworkElement.ID, new DefaultSoulNetworkElement.Factory());
    }


    @Override
    protected void initModules() {
        this.addCreativeTab("main", () -> new ItemStack(SOUL_LASER_BLOCK.getKey().get()), MOD_ID, TAB);
        SOUL_LASER_BLOCK = this.getRegistries().registerBlockWithTile("soul_laser_base", SoulLaserBaseBlock::new, TAB);
        SOUL_PIPE_BLOCK = this.getRegistries().registerBlockWithTile("soul_network_pipe", SoulPipeBlock::new, TAB);
        SOUL_SURGE_BLOCK = this.getRegistries().registerBlockWithTile("soul_surge", SoulSurgeBlock::new, TAB);
        PHANTOM_SOUL_DRIVE_CONTROLLER = this.getRegistries().registerBlockWithTile("phantom_soul_drive_controller", PhantomSoulDriveControllerBlock::new, TAB);
        DRIVE_FRAME_BLOCK = this.getRegistries().registerBlockWithTile("drive_frame", DriveFrameBlock::new, TAB);
    }

    @OnlyIn(Dist.CLIENT)
    public void onClient() {

    }

    @Override
    public void addDataProvider(GatherDataEvent event) {
        NonNullLazy<List<Block>> blocksToProcess = NonNullLazy.of(() ->
                ForgeRegistries.BLOCKS.getValues()
                        .stream()
                        .filter(basicBlock -> Optional.ofNullable(ForgeRegistries.BLOCKS.getKey(basicBlock))
                                .map(ResourceLocation::getNamespace)
                                .filter(MOD_ID::equalsIgnoreCase)
                                .isPresent())
                        .collect(Collectors.toList())
        );
        event.getGenerator().addProvider(true, new IFSoulsLangProvider(event.getGenerator().getPackOutput(), MOD_ID, "en_us"));
        event.getGenerator().addProvider(true, new BlockItemModelGeneratorProvider(event.getGenerator(), MOD_ID, blocksToProcess));
        event.getGenerator().addProvider(true, new TitaniumLootTableProvider(event.getGenerator(), blocksToProcess));
        event.getGenerator().addProvider(true, new IFSoulsRecipeProvider(event.getGenerator()));
        event.getGenerator().addProvider(true, new IFSoulsSerializableRecipe(event.getGenerator(), MOD_ID));
        event.getGenerator().addProvider(true, new IFSoulsTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), MOD_ID, event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new IFSoulsBlockstateProvider(event.getGenerator().getPackOutput(), MOD_ID, event.getExistingFileHelper()));
    }
}
