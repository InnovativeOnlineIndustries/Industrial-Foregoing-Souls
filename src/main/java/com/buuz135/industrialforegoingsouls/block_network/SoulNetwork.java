package com.buuz135.industrialforegoingsouls.block_network;

import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import com.buuz135.industrialforegoingsouls.block.tile.SoulLaserBaseBlockEntity;
import com.buuz135.industrialforegoingsouls.config.IFSoulsMachines;
import com.hrznstudio.titanium.block_network.Network;
import com.hrznstudio.titanium.block_network.NetworkFactory;
import com.hrznstudio.titanium.block_network.NetworkManager;
import com.hrznstudio.titanium.block_network.element.NetworkElement;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SoulNetwork extends Network {

    public static ResourceLocation SOUL_NETWORK = ResourceLocation.fromNamespaceAndPath(IndustrialForegoingSouls.MOD_ID, "soul");

    private int soulAmount;

    public SoulNetwork(BlockPos originPos, String id, int soulAmount) {
        super(originPos, id);
        this.soulAmount = soulAmount;
    }

    @Override
    public void update(Level level) {
        super.update(level);
    }

    @Override
    public void onMergedWith(Network mainNetwork) {
        if (mainNetwork instanceof SoulNetwork matterNetwork) {
            this.addSouls(null, matterNetwork.soulAmount);
        }
    }

    public int addSouls(Level level, int soulAmount){
        var oldAmount = this.soulAmount;
        this.soulAmount = Math.min(getMaxSouls(), soulAmount + oldAmount);
        if (level != null) NetworkManager.get(level).setDirty(true);
        return this.soulAmount - oldAmount;
    }

    public boolean useSoul(Level level){
        if (this.soulAmount > 0) {
            --this.soulAmount;
            if (level != null) NetworkManager.get(level).setDirty(true);
            return true;
        }
        return false;
    }

    public int getMaxSouls(){
        return this.graph.getElements().size() * IFSoulsMachines.SOUL_AMOUNT_PER_PIPE;
    }

    public int getSoulAmount() {
        return soulAmount;
    }

    @Override
    public CompoundTag writeToNbt(CompoundTag tag) {
        var nbt = super.writeToNbt(tag);
        nbt.putInt("soulAmount", soulAmount);
        return nbt;
    }

    @Override
    public ResourceLocation getType() {
        return SOUL_NETWORK;
    }


    public static class Factory implements NetworkFactory {

        private static final Logger LOGGER = LogManager.getLogger(Factory.class);

        @Override
        public Network create(BlockPos pos) {
            return new SoulNetwork(pos, NetworkFactory.randomString(new Random(), 8), 0);
        }

        @Override
        public Network create(CompoundTag tag) {
            SoulNetwork network = new SoulNetwork(BlockPos.of(tag.getLong("origin")), tag.getString("id"), tag.getInt("soulAmount"));

            LOGGER.debug("Deserialized matter network {} of type {}", network.getId(), network.getType().toString());

            return network;
        }
    }
}
