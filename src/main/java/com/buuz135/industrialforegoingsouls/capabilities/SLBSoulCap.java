package com.buuz135.industrialforegoingsouls.capabilities;

import com.buuz135.industrialforegoingsouls.block.tile.SoulLaserBaseBlockEntity;
import com.buuz135.industrialforegoingsouls.config.ConfigSoulLaserBase;

public class SLBSoulCap implements ISoulHandler{

    private final SoulLaserBaseBlockEntity blockEntity;

    public SLBSoulCap(SoulLaserBaseBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    @Override
    public int getSoulTanks() {
        return 1;
    }

    @Override
    public int getSoulInTank(int tank) {
        return this.blockEntity.getSoulAmount();
    }

    @Override
    public int getTankCapacity(int tank) {
        return ConfigSoulLaserBase.SOUL_STORAGE_AMOUNT;
    }

    @Override
    public int fill(int amount, Action action) {
        return 0;
    }

    @Override
    public int drain(int maxDrain, Action action) {
        if (action.execute()){
            return blockEntity.useSoul(maxDrain);
        } else {
            return Math.max(0, this.blockEntity.getSoulAmount() - maxDrain);
        }
    }
}
