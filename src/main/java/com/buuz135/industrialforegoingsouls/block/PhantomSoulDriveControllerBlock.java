package com.buuz135.industrialforegoingsouls.block;

import com.buuz135.industrialforegoingsouls.block.tile.PhantomSoulDriveControllerTile;
import com.hrznstudio.titanium.block.RotatableBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

public class PhantomSoulDriveControllerBlock extends RotatableBlock<PhantomSoulDriveControllerTile> {

    public PhantomSoulDriveControllerBlock() {
        super("phantom_soul_drive_controller", Properties.copy(Blocks.IRON_BLOCK), PhantomSoulDriveControllerTile.class);
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<?> getTileEntityFactory() {
        return PhantomSoulDriveControllerTile::new;
    }

    @Override
    public @NotNull RotationType getRotationType() {
        return RotationType.FOUR_WAY;
    }


}
