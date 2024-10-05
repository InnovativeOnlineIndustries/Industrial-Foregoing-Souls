package com.buuz135.industrialforegoingsouls;

import com.buuz135.industrialforegoingsouls.block.SoulLaserBaseBlock;
import com.buuz135.industrialforegoingsouls.block.SoulPipeBlock;
import com.buuz135.industrialforegoingsouls.block.SoulSurgeBlock;
import com.buuz135.industrialforegoingsouls.block_network.DefaultSoulNetworkElement;
import com.buuz135.industrialforegoingsouls.block_network.SoulNetwork;
import com.buuz135.industrialforegoingsouls.data.IFSoulsBiomeTagProvider;
import com.buuz135.industrialforegoingsouls.data.IFSoulsBlockstateProvider;
import com.buuz135.industrialforegoingsouls.data.IFSoulsLangProvider;
import com.buuz135.industrialforegoingsouls.data.IFSoulsRecipeProvider;
import com.buuz135.industrialforegoingsouls.data.IFSoulsTagProvider;
import com.hrznstudio.titanium.block_network.NetworkRegistry;
import com.hrznstudio.titanium.block_network.element.NetworkElementRegistry;
import com.hrznstudio.titanium.datagenerator.loot.TitaniumLootTableProvider;
import com.hrznstudio.titanium.datagenerator.model.BlockItemModelGeneratorProvider;
import com.hrznstudio.titanium.module.BlockWithTile;
import com.hrznstudio.titanium.module.ModuleController;
import com.hrznstudio.titanium.network.NetworkHandler;
import com.hrznstudio.titanium.tab.TitaniumTab;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(IndustrialForegoingSouls.MOD_ID)
public class IndustrialForegoingSouls extends ModuleController {

    public final static String MOD_ID = "industrialforegoingsouls";
    public static NetworkHandler NETWORK = new NetworkHandler(MOD_ID);
    public static TitaniumTab TAB = new TitaniumTab(ResourceLocation.fromNamespaceAndPath(MOD_ID, "main"));

    public static BlockWithTile SOUL_LASER_BLOCK = null;
    public static BlockWithTile SOUL_PIPE_BLOCK = null;
    public static BlockWithTile SOUL_SURGE_BLOCK = null;

    public IndustrialForegoingSouls(Dist dist, IEventBus modBus, ModContainer container) {
        super(container);
        if (dist.isClient()) {
            this.onClient();
        }
        NetworkRegistry.INSTANCE.addFactory(SoulNetwork.SOUL_NETWORK, new SoulNetwork.Factory());
        NetworkElementRegistry.INSTANCE.addFactory(DefaultSoulNetworkElement.ID, new DefaultSoulNetworkElement.Factory());
    }


    @Override
    protected void initModules() {
        this.addCreativeTab("main", () -> new ItemStack(SOUL_LASER_BLOCK.block().get()), MOD_ID, TAB);
        SOUL_LASER_BLOCK = this.getRegistries().registerBlockWithTile("soul_laser_base", SoulLaserBaseBlock::new, TAB);
        SOUL_PIPE_BLOCK = this.getRegistries().registerBlockWithTile("soul_network_pipe", SoulPipeBlock::new, TAB);
        SOUL_SURGE_BLOCK = this.getRegistries().registerBlockWithTile("soul_surge", SoulSurgeBlock::new, TAB);
    }

    public void onClient() {

    }

    @Override
    public void addDataProvider(GatherDataEvent event) {
        Supplier<List<Block>> blocksToProcess = () -> BuiltInRegistries.BLOCK.stream().toList()
                .stream()
                .filter(basicBlock -> Optional.ofNullable(BuiltInRegistries.BLOCK.getKey(basicBlock))
                        .map(ResourceLocation::getNamespace)
                        .filter(MOD_ID::equalsIgnoreCase)
                        .isPresent())
                .collect(Collectors.toList());
        event.getGenerator().addProvider(true, new IFSoulsLangProvider(event.getGenerator().getPackOutput(), MOD_ID, "en_us"));
        event.getGenerator().addProvider(true, new BlockItemModelGeneratorProvider(event.getGenerator(), MOD_ID, blocksToProcess));
        event.getGenerator().addProvider(true, new TitaniumLootTableProvider(event.getGenerator(), blocksToProcess, event.getLookupProvider()));
        event.getGenerator().addProvider(true, new IFSoulsRecipeProvider(event.getGenerator(), event.getLookupProvider()));
        event.getGenerator().addProvider(true, new IFSoulsTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), MOD_ID, event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new IFSoulsBiomeTagProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), MOD_ID, event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new IFSoulsBlockstateProvider(event.getGenerator().getPackOutput(), MOD_ID, event.getExistingFileHelper()));
    }
}
