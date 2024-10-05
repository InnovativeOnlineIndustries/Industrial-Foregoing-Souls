package com.buuz135.industrialforegoingsouls.block_network;

import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import com.buuz135.industrialforegoingsouls.block.tile.SoulLaserBaseBlockEntity;
import com.hrznstudio.titanium.block_network.Network;
import com.hrznstudio.titanium.block_network.NetworkFactory;
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

    private List<NetworkElement> queueNetworkElements;
    private List<NetworkElement> soulLaserDrills;

    public SoulNetwork(BlockPos originPos, String id) {
        super(originPos, id);
        this.queueNetworkElements = new ArrayList<>();
        this.soulLaserDrills = new ArrayList<>();
    }

    public void addElement(NetworkElement element) {
        this.queueNetworkElements.add(element);
    }

    public void removeElement(NetworkElement element) {
        var tile = element.getLevel().getBlockEntity(element.getPos());
        if (tile instanceof SoulLaserBaseBlockEntity) this.soulLaserDrills.remove(element);
    }

    @Override
    public void update(Level level) {
        super.update(level);
        for (NetworkElement element : this.queueNetworkElements) {
            var tile = element.getLevel().getBlockEntity(element.getPos());
            if (tile instanceof SoulLaserBaseBlockEntity) {
                this.soulLaserDrills.add(element);
            }
        }
        this.queueNetworkElements.clear();

    }

    @Override
    public void onMergedWith(Network mainNetwork) {
        if (mainNetwork instanceof SoulNetwork matterNetwork) {

        }
    }

    @Override
    public CompoundTag writeToNbt(CompoundTag tag) {
        var nbt = super.writeToNbt(tag);
        return nbt;
    }

    @Override
    public ResourceLocation getType() {
        return SOUL_NETWORK;
    }

    public List<NetworkElement> getSoulLaserDrills() {
        return soulLaserDrills;
    }

    public static class Factory implements NetworkFactory {

        private static final Logger LOGGER = LogManager.getLogger(Factory.class);

        @Override
        public Network create(BlockPos pos) {
            return new SoulNetwork(pos, NetworkFactory.randomString(new Random(), 8));
        }

        @Override
        public Network create(CompoundTag tag) {
            SoulNetwork network = new SoulNetwork(BlockPos.of(tag.getLong("origin")), tag.getString("id"));

            LOGGER.debug("Deserialized matter network {} of type {}", network.getId(), network.getType().toString());

            return network;
        }
    }
}
