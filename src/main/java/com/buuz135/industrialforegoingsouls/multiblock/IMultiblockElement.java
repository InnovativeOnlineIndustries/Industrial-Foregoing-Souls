package com.buuz135.industrialforegoingsouls.multiblock;

import com.buuz135.industrialforegoingsouls.block.tile.PhantomSoulDriveControllerTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;


public interface IMultiblockElement {

    void setControllerPosition(BlockPos controllerPosition);

    BlockPos getControllerPosition();

    boolean hasController();

    public static void onBlockBroken(Level level, BlockPos controllerPosition) {
        if (controllerPosition != null){
            var tile = level.getBlockEntity(controllerPosition);
            if (tile instanceof PhantomSoulDriveControllerTile phantomSoulDriveControllerTile) {
                phantomSoulDriveControllerTile.handleUnmultiblock();
            }
        }
    }
}
