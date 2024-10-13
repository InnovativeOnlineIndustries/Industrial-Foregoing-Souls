package com.buuz135.industrialforegoingsouls.block.tile;

import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import com.buuz135.industrialforegoingsouls.multiblock.PSDStructureChecker;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block.tile.PoweredTile;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class PhantomSoulDriveControllerTile extends PoweredTile<PhantomSoulDriveControllerTile> {

    private PSDStructureChecker structureChecker;
    @Save
    private boolean test;

    public PhantomSoulDriveControllerTile(BlockPos pos, BlockState state) {
        super((BasicTileBlock<PhantomSoulDriveControllerTile>) IndustrialForegoingSouls.PHANTOM_SOUL_DRIVE_CONTROLLER.getKey().get(), IndustrialForegoingSouls.PHANTOM_SOUL_DRIVE_CONTROLLER.getValue().get(), pos, state);
        setShowEnergy(true);
        this.structureChecker = new PSDStructureChecker();
    }

    @Override
    public InteractionResult onActivated(Player player, InteractionHand hand, Direction facing, double hitX, double hitY, double hitZ) {
        openGui(player);
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull PhantomSoulDriveControllerTile getSelf() {
        return this;
    }

    @Override
    protected @NotNull EnergyStorageComponent<PhantomSoulDriveControllerTile> createEnergyStorage() {
        return new EnergyStorageComponent<>(2_000_000_000, 4, 10);
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state, PhantomSoulDriveControllerTile blockEntity) {
        super.serverTick(level, pos, state, blockEntity);
        var prevFormed = structureChecker.isFormed();
        var area = structureChecker.checkStructure(this.level, this.worldPosition, this.getFacingDirection());
        structureChecker.setFormed(area != null);
        if (area != null && prevFormed != structureChecker.isFormed()) {
           if (level instanceof ServerLevel serverLevel) {
               serverLevel.playSound(null, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS, 1f , 1.2f);
           }
        }
    }



    public void handleUnmultiblock(){

    }
}
