package com.buuz135.industrialforegoingsouls.block_network;

import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import com.hrznstudio.titanium.block_network.INetworkDirectionalConnection;
import com.hrznstudio.titanium.block_network.Network;
import com.hrznstudio.titanium.block_network.element.NetworkElement;
import com.hrznstudio.titanium.block_network.element.NetworkElementFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class DefaultSoulNetworkElement extends NetworkElement {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(IndustrialForegoingSouls.MOD_ID, "default_souls");

    public DefaultSoulNetworkElement(Level level, BlockPos pos) {
        super(level, pos);
    }

    @Override
    public void joinNetwork(Network network) {
        super.joinNetwork(network);
    }

    @Override
    public void leaveNetwork() {
        super.leaveNetwork();
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public ResourceLocation getNetworkType() {
        return SoulNetwork.SOUL_NETWORK;
    }

    @Override
    public boolean canConnectFrom(Direction direction) {
        if (this.level != null){
            var state = this.level.getBlockState(this.pos);
            if (state.getBlock() instanceof INetworkDirectionalConnection networkDirectionalConnection) {
                return networkDirectionalConnection.canConnect(this.level, this.pos, state, direction);
            }
        }
        return true;
    }

    public static class Factory implements NetworkElementFactory {

        @Override
        public NetworkElement createFromNbt(Level level, CompoundTag tag) {
            BlockPos pos = BlockPos.of(tag.getLong("pos"));
            DefaultSoulNetworkElement element = new DefaultSoulNetworkElement(level, pos);
            return element;
        }
    }
}
