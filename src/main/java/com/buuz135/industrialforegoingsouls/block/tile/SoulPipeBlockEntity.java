package com.buuz135.industrialforegoingsouls.block.tile;

import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.BasicTileBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    }

    @NotNull
    @Override
    public SoulPipeBlockEntity getSelf() {
        return this;
    }

    @NotNull
    @Override
    public <U> LazyOptional<U> getCapability(@NotNull Capability<U> cap, @Nullable Direction side) {
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
    }

}
