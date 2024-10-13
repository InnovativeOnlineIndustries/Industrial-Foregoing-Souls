package com.buuz135.industrialforegoingsouls.block.tile;

import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import com.buuz135.industrialforegoingsouls.multiblock.IMultiblockElement;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block.tile.BasicTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class DriveFrameTile extends BasicTile<DriveFrameTile> implements IMultiblockElement {

    @Save
    private BlockPos controllerPosition;

    public DriveFrameTile(BlockPos pos, BlockState state) {
        super((BasicTileBlock<DriveFrameTile>) IndustrialForegoingSouls.DRIVE_FRAME_BLOCK.getKey().get(), IndustrialForegoingSouls.DRIVE_FRAME_BLOCK.getValue().get(), pos, state);
        this.controllerPosition = pos;
    }


    @Override
    public void setControllerPosition(BlockPos controllerPosition) {
        this.controllerPosition = controllerPosition;
    }

    @Override
    public BlockPos getControllerPosition() {
        return controllerPosition;
    }

    @Override
    public boolean hasController() {
        return !this.getBlockPos().equals(this.controllerPosition);
    }

}
