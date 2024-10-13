package com.buuz135.industrialforegoingsouls.block;

import com.buuz135.industrialforegoingsouls.block.tile.DriveFrameTile;
import com.hrznstudio.titanium.block.BasicTileBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class DriveFrameBlock extends BasicTileBlock<DriveFrameTile> {

    public DriveFrameBlock() {
        super("drive_frame", Properties.copy(Blocks.IRON_BLOCK), DriveFrameTile.class);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return DriveFrameTile::new;
    }
}
