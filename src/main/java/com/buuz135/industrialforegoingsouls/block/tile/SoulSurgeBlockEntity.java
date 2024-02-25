package com.buuz135.industrialforegoingsouls.block.tile;

import com.buuz135.industrialforegoingsouls.block.SoulSurgeBlock;
import com.buuz135.industrialforegoingsouls.config.ConfigSoulSurge;
import com.buuz135.industrialforegoingsouls.tag.SoulTags;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block.RotatableBlock;
import com.hrznstudio.titanium.block_network.element.NetworkElement;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.NotNull;

public class SoulSurgeBlockEntity extends NetworkBlockEntity<SoulSurgeBlockEntity> {

    @Save
    private int tickingTime;

    public SoulSurgeBlockEntity(BasicTileBlock<SoulSurgeBlockEntity> base, BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(base, blockEntityType, pos, state);
    }

    @NotNull
    @Override
    public SoulSurgeBlockEntity getSelf() {
        return this;
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state, SoulSurgeBlockEntity blockEntity) {
        super.serverTick(level, pos, state, blockEntity);
        if (state.getValue(SoulSurgeBlock.ENABLED)) {
            if (tickingTime <= 0) {
                var network = getNetwork();
                for (NetworkElement soulLaserDrill : network.getSoulLaserDrills()) {
                    var soulTile = soulLaserDrill.getLevel().getBlockEntity(soulLaserDrill.getPos());
                    if (soulTile instanceof SoulLaserBaseBlockEntity soulLaserBaseBlockEntity && soulLaserBaseBlockEntity.getSoulAmount() > 0) {
                        soulLaserBaseBlockEntity.useSoul();
                        tickingTime = ConfigSoulSurge.SOUL_TIME;
                        break;
                    }
                }
            }
            if (tickingTime > 0) {
                var targetingState = level.getBlockState(pos.relative(state.getValue(RotatableBlock.FACING_ALL).getOpposite()));
                if (!targetingState.is(Blocks.AIR) && !targetingState.is(SoulTags.Blocks.CANT_ACCELERATE)) {
                    BlockEntity targetingTile = level.getBlockEntity(pos.relative(state.getValue(RotatableBlock.FACING_ALL).getOpposite()));
                    if (targetingTile != null) {
                        BlockEntityTicker<BlockEntity> ticker = (BlockEntityTicker<BlockEntity>) targetingState.getTicker(this.level, targetingTile.getType());
                        if (ticker != null) {
                            for (int i = 0; i < ConfigSoulSurge.ACCELERATION_TICK; i++) {
                                ticker.tick(level, pos.relative(state.getValue(RotatableBlock.FACING_ALL).getOpposite()), targetingState, targetingTile);
                            }
                            --tickingTime;
                        }
                    } else if (level instanceof ServerLevel serverLevel) {
                        for (int i = 0; i < ConfigSoulSurge.BLOCK_ACCELERATION_TICK; i++) {
                            targetingState.randomTick(serverLevel, pos.relative(state.getValue(RotatableBlock.FACING_ALL).getOpposite()), serverLevel.random);
                        }
                        --tickingTime;
                    }
                }
                if (targetingState.is(Blocks.AIR)) {
                    var posOffset = pos.relative(state.getValue(RotatableBlock.FACING_ALL).getOpposite());
                    var box = Shapes.box(0, 0, 0, 1, 1, 1).move(posOffset.getX(), posOffset.getY(), posOffset.getZ());
                    var entities = level.getEntitiesOfClass(LivingEntity.class, box.bounds());
                    for (LivingEntity entity : entities) {
                        if (!entity.getType().is(SoulTags.Entities.CANT_ACCELERATE)) {
                            for (int i = 0; i < ConfigSoulSurge.ENTITIES_ACCELERATION_TICK; i++) {
                                entity.tick();
                            }
                            --tickingTime;
                        }
                    }
                }
            }
        }
    }
}
