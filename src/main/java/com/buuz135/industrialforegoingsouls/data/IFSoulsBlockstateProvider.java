package com.buuz135.industrialforegoingsouls.data;


import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import com.buuz135.industrialforegoingsouls.block.SoulSurgeBlock;
import com.hrznstudio.titanium.block.RotatableBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class IFSoulsBlockstateProvider extends BlockStateProvider {
    public IFSoulsBlockstateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        MultiPartBlockStateBuilder model = getMultipartBuilder(IndustrialForegoingSouls.SOUL_SURGE_BLOCK.getLeft().get());
        SoulSurgeBlock.ENABLED.getAllValues().forEach(enabled -> {
            var enabledModel = new ModelFile.UncheckedModelFile("industrialforegoingsouls:block/soul_surge" + (!enabled.value() ? "_off" : ""));
            RotatableBlock.RotationType.SIX_WAY.getProperties()[0].getAllValues().forEach(rotation -> {
                var configured = model.part().modelFile(enabledModel);
                configured = getRotation(rotation.value()).apply(configured);
                configured.addModel().condition(SoulSurgeBlock.ENABLED, enabled.value()).condition(RotatableBlock.RotationType.SIX_WAY.getProperties()[0], rotation.value()).end();
                //configured.build();
            });
        });
        SoulSurgeBlock.TOP_CONN.getAllValues().forEach(enabled -> {
            if (enabled.value()) {
                var enabledModel = new ModelFile.UncheckedModelFile("industrialforegoingsouls:block/soul_surge_top_connection");
                RotatableBlock.RotationType.SIX_WAY.getProperties()[0].getAllValues().forEach(rotation -> {
                    var configured = model.part().modelFile(enabledModel);
                    configured = getRotation(rotation.value()).apply(configured);
                    configured.addModel().condition(SoulSurgeBlock.TOP_CONN, enabled.value()).condition(RotatableBlock.RotationType.SIX_WAY.getProperties()[0], rotation.value()).end();
                    //configured.build();
                });
            }
        });
        SoulSurgeBlock.UP_CONN.getAllValues().forEach(enabled -> {
            if (enabled.value()) {
                var enabledModel = new ModelFile.UncheckedModelFile("industrialforegoingsouls:block/soul_surge_side_connection_up");
                RotatableBlock.RotationType.SIX_WAY.getProperties()[0].getAllValues().forEach(rotation -> {
                    var configured = model.part().modelFile(enabledModel);
                    configured = getRotation(rotation.value()).apply(configured);
                    configured.addModel().condition(SoulSurgeBlock.UP_CONN, enabled.value()).condition(RotatableBlock.RotationType.SIX_WAY.getProperties()[0], rotation.value()).end();
                    //configured.build();
                });
            }
        });
        SoulSurgeBlock.DOWN_CONN.getAllValues().forEach(enabled -> {
            if (enabled.value()) {
                var enabledModel = new ModelFile.UncheckedModelFile("industrialforegoingsouls:block/soul_surge_side_connection_down");
                RotatableBlock.RotationType.SIX_WAY.getProperties()[0].getAllValues().forEach(rotation -> {
                    var configured = model.part().modelFile(enabledModel);
                    configured = getRotation(rotation.value()).apply(configured);
                    configured.addModel().condition(SoulSurgeBlock.DOWN_CONN, enabled.value()).condition(RotatableBlock.RotationType.SIX_WAY.getProperties()[0], rotation.value()).end();
                    //configured.build();
                });
            }
        });
        SoulSurgeBlock.EAST_CONN.getAllValues().forEach(enabled -> {
            if (enabled.value()) {
                var enabledModel = new ModelFile.UncheckedModelFile("industrialforegoingsouls:block/soul_surge_side_connection_east");
                RotatableBlock.RotationType.SIX_WAY.getProperties()[0].getAllValues().forEach(rotation -> {
                    var configured = model.part().modelFile(enabledModel);
                    configured = getRotation(rotation.value()).apply(configured);
                    configured.addModel().condition(SoulSurgeBlock.EAST_CONN, enabled.value()).condition(RotatableBlock.RotationType.SIX_WAY.getProperties()[0], rotation.value()).end();
                    //configured.build();
                });
            }
        });
        SoulSurgeBlock.WEST_CONN.getAllValues().forEach(enabled -> {
            if (enabled.value()) {
                var enabledModel = new ModelFile.UncheckedModelFile("industrialforegoingsouls:block/soul_surge_side_connection_west");
                RotatableBlock.RotationType.SIX_WAY.getProperties()[0].getAllValues().forEach(rotation -> {
                    var configured = model.part().modelFile(enabledModel);
                    configured = getRotation(rotation.value()).apply(configured);
                    configured.addModel().condition(SoulSurgeBlock.WEST_CONN, enabled.value()).condition(RotatableBlock.RotationType.SIX_WAY.getProperties()[0], rotation.value()).end();
                    //configured.build();
                });
            }
        });
    }

    private Function<ConfiguredModel.Builder, ConfiguredModel.Builder> getRotation(Direction direction) {
        if (direction == Direction.NORTH) return builder -> builder;
        if (direction == Direction.SOUTH) return builder -> builder.rotationY(-180);
        if (direction == Direction.EAST) return builder -> builder.rotationY(90);
        if (direction == Direction.WEST) return builder -> builder.rotationY(-90);
        if (direction == Direction.UP) return builder -> builder.rotationX(-90);
        if (direction == Direction.DOWN) return builder -> builder.rotationX(90);
        return builder -> builder;
    }
}
