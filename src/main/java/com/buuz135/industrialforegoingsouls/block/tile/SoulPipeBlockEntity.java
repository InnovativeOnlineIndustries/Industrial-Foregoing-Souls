package com.buuz135.industrialforegoingsouls.block.tile;

import com.buuz135.industrialforegoingsouls.capabilities.ISoulHandler;
import com.buuz135.industrialforegoingsouls.capabilities.SoulCapabilities;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.BasicTileBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SoulPipeBlockEntity extends NetworkBlockEntity<SoulPipeBlockEntity> {

    @Save
    private boolean wip;

    public SoulPipeBlockEntity(BasicTileBlock<SoulPipeBlockEntity> base, BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(base, blockEntityType, pos, state);
        this.wip = false;
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state, SoulPipeBlockEntity blockEntity) {
        super.serverTick(level, pos, state, blockEntity);
        for (Direction value : Direction.values()) {
            var capability = level.getCapability(SoulCapabilities.BLOCK, pos.relative(value), value.getOpposite());
            if (capability != null) {
                var network = getNetwork();
                if (network != null) {
                    var simulatedFill = capability.fill(4, ISoulHandler.Action.SIMULATE);
                    var filled = capability.fill(network.drainSouls(this.level, simulatedFill), ISoulHandler.Action.EXECUTE);
                    // don't try to fill and drain on the same tick
                    if (filled == 0) {
                        var simulatedDrain = capability.drain(4, ISoulHandler.Action.SIMULATE);
                        capability.drain(network.addSouls(this.level, simulatedDrain), ISoulHandler.Action.EXECUTE);                            
                    }
                }
            }
        }
    }

    @NotNull
    @Override
    public SoulPipeBlockEntity getSelf() {
        return this;
    }

//    @NotNull
//    @Override
//    public <U> LazyOptional<U> getCapability(@NotNull Capability<U> cap, @Nullable Direction side) {
//        return super.getCapability(cap, side);
//    }
//
//    @Override
//    public void invalidateCaps() {
//        super.invalidateCaps();
//    }

}
