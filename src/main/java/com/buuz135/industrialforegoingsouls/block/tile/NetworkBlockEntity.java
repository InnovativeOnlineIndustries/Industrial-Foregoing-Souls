package com.buuz135.industrialforegoingsouls.block.tile;

import com.buuz135.industrialforegoingsouls.block_network.DefaultSoulNetworkElement;
import com.buuz135.industrialforegoingsouls.block_network.SoulNetwork;
import com.hrznstudio.titanium.block.BasicTileBlock;
import com.hrznstudio.titanium.block.tile.ActiveTile;
import com.hrznstudio.titanium.block.tile.ITickableBlockEntity;
import com.hrznstudio.titanium.block_network.Network;
import com.hrznstudio.titanium.block_network.NetworkManager;
import com.hrznstudio.titanium.block_network.element.NetworkElement;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public abstract class NetworkBlockEntity<T extends ActiveTile<T>> extends ActiveTile<T> implements ITickableBlockEntity<T> {

    private boolean unloaded;

    public NetworkBlockEntity(BasicTileBlock<T> base, BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(base, blockEntityType, pos, state);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initClient() {
        super.initClient();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (level instanceof ServerLevel serverLevel) {
            NetworkManager networkManager = NetworkManager.get(serverLevel);
            if (networkManager.getElement(worldPosition) == null) {
                var element = createElement(serverLevel, worldPosition);
                networkManager.addElement(element);
            }
        }
    }

    @Override
    public void onChunkUnloaded() {
        super.onChunkUnloaded();
        unloaded = true;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();

        if (!level.isClientSide && !unloaded) {
            NetworkManager networkManager = NetworkManager.get(level);

            NetworkElement pipe = networkManager.getElement(worldPosition);
            if (pipe != null) {
                //spawnDrops(pipe);
                networkManager.removeElement(worldPosition);
            }
        }
    }

    protected NetworkElement createElement(Level level, BlockPos pos) {
        return new DefaultSoulNetworkElement(level, pos);
    }

    public SoulNetwork getNetwork() {
        return (SoulNetwork) NetworkManager.get(this.level).getElement(worldPosition).getNetwork();
    }


}
