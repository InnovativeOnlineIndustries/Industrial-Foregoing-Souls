package com.buuz135.industrialforegoingsouls.block.tile;

import com.buuz135.industrial.block.resourceproduction.tile.ILaserBase;
import com.buuz135.industrial.block.tile.IndustrialMachineTile;
import com.buuz135.industrial.module.ModuleCore;
import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import com.buuz135.industrialforegoingsouls.block_network.DefaultSoulNetworkElement;
import com.buuz135.industrialforegoingsouls.block_network.SoulNetwork;
import com.buuz135.industrialforegoingsouls.client.SculkSoulTankScreenAddon;
import com.buuz135.industrialforegoingsouls.config.ConfigSoulLaserBase;
import com.hrznstudio.titanium.annotation.Save;
import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.augment.AugmentTypes;
import com.hrznstudio.titanium.api.client.IScreenAddon;
import com.hrznstudio.titanium.block_network.NetworkManager;
import com.hrznstudio.titanium.block_network.element.NetworkElement;
import com.hrznstudio.titanium.client.screen.addon.ProgressBarScreenAddon;
import com.hrznstudio.titanium.component.energy.EnergyStorageComponent;
import com.hrznstudio.titanium.component.inventory.SidedInventoryComponent;
import com.hrznstudio.titanium.component.progress.ProgressBarComponent;
import com.hrznstudio.titanium.component.sideness.IFacingComponent;
import com.hrznstudio.titanium.item.AugmentWrapper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SoulLaserBaseBlockEntity extends IndustrialMachineTile<SoulLaserBaseBlockEntity> implements ILaserBase<SoulLaserBaseBlockEntity> {

    @Save
    private ProgressBarComponent<SoulLaserBaseBlockEntity> work;
    @Save
    private SidedInventoryComponent<SoulLaserBaseBlockEntity> catalyst;
    @Save
    private int soulAmount;
    private boolean unloaded;

    public SoulLaserBaseBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(IndustrialForegoingSouls.SOUL_LASER_BLOCK, blockPos, blockState);
        this.soulAmount = 0;
        setShowEnergy(false);
        this.addProgressBar(work = new ProgressBarComponent<SoulLaserBaseBlockEntity>(74, 24 + 18, 0, ConfigSoulLaserBase.MAX_PROGRESS) {
                    @Override
                    @OnlyIn(Dist.CLIENT)
                    public List<IFactory<? extends IScreenAddon>> getScreenAddons() {
                        return Collections.singletonList(() -> new ProgressBarScreenAddon<SoulLaserBaseBlockEntity>(work.getPosX(), work.getPosY(), this) {
                            @Override
                            public List<Component> getTooltipLines() {
                                List<Component> tooltip = new ArrayList<>();
                                tooltip.add(Component.literal(ChatFormatting.GOLD + Component.translatable("tooltip.titanium.progressbar.progress").getString() + ChatFormatting.WHITE + new DecimalFormat().format(work.getProgress()) + ChatFormatting.GOLD + "/" + ChatFormatting.WHITE + new DecimalFormat().format(work.getMaxProgress())));
                                return tooltip;
                            }
                        });
                    }
                }
                        .setBarDirection(ProgressBarComponent.BarDirection.ARROW_RIGHT)
                        .setCanIncrease(oreLaserBaseTile -> true)
                        .setProgressIncrease(0)
                        .setCanReset(oreLaserBaseTile -> true)
                        .setOnStart(() -> {
                            int maxProgress = (int) Math.floor(ConfigSoulLaserBase.MAX_PROGRESS * (this.hasAugmentInstalled(AugmentTypes.EFFICIENCY) ? AugmentWrapper.getType(this.getInstalledAugments(AugmentTypes.EFFICIENCY).get(0), AugmentTypes.EFFICIENCY) : 1));
                            work.setMaxProgress(maxProgress);
                        })
                        .setOnFinishWork(this::onWork)
        );
        this.addInventory(catalyst = (SidedInventoryComponent<SoulLaserBaseBlockEntity>) new SidedInventoryComponent<SoulLaserBaseBlockEntity>("lens", 50, 24 + 18, 1, 0)
                        .setColor(DyeColor.BLUE)
                        .setRange(2, 3)
                        .setSlotLimit(1)
                //.setInputFilter((stack, integer) -> stack.getItem() instanceof LaserLensItem)
        );
        catalyst.getFacingModes().keySet().forEach(sideness -> catalyst.getFacingModes().put(sideness, IFacingComponent.FaceMode.NONE));

    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initClient() {
        super.initClient();
        this.addGuiAddonFactory(() -> new SculkSoulTankScreenAddon(100, 20, this));
    }

    @Override
    public void setChanged() {
        super.setChanged();
    }

    private void onWork() {
        if (!catalyst.getStackInSlot(0).isEmpty() && catalyst.getStackInSlot(0).getItem().equals(ModuleCore.LASER_LENS[11].get()) && this.soulAmount + ConfigSoulLaserBase.SOULS_PER_OPERATION <= ConfigSoulLaserBase.SOUL_STORAGE_AMOUNT) {
            VoxelShape box = Shapes.box(-1, 0, -1, 2, 3, 2).move(this.worldPosition.getX(), this.worldPosition.getY() - 1, this.worldPosition.getZ());
            List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class, box.bounds(), entity -> entity.getType().equals(EntityType.WARDEN));
            if (entities.size() > 0) {
                LivingEntity first = entities.get(0);
                if (first.getHealth() > ConfigSoulLaserBase.DAMAGE_PER_OPERATION || ConfigSoulLaserBase.KILL_WARDEN) {
                    first.hurt(first.damageSources().generic(), ConfigSoulLaserBase.DAMAGE_PER_OPERATION);
                    this.soulAmount += ConfigSoulLaserBase.SOULS_PER_OPERATION;
                    syncObject(this.soulAmount);
                }
            }
        }
    }

    @Override
    public void clientTick(Level level, BlockPos pos, BlockState state, SoulLaserBaseBlockEntity blockEntity) {
        super.clientTick(level, pos, state, blockEntity);
        VoxelShape box = Shapes.box(-1, 0, -1, 2, 3, 2).move(this.worldPosition.getX(), this.worldPosition.getY() - 1, this.worldPosition.getZ());
        List<Mob> entities = this.level.getEntitiesOfClass(Mob.class, box.bounds());
        for (Mob entity : entities) {
            if (entity instanceof Warden warden) {
                warden.sonicBoomAnimationState.start(warden.tickCount - 43);
                if (level.random.nextDouble() <= 0.10f) {
                    level.addParticle(ParticleTypes.SCULK_SOUL, entity.getX() + (level.random.nextDouble() - 0.5), entity.getY() + 1.5, entity.getZ() + (level.random.nextDouble() - 0.5), 0, 0.1d, 0);
                }
            }
        }
    }

    public int getSoulAmount() {
        return soulAmount;
    }

    public void useSoul() {
        --this.soulAmount;
        syncObject(this.soulAmount);
    }

    @Override
    public SoulLaserBaseBlockEntity getSelf() {
        return this;
    }

    @Override
    protected EnergyStorageComponent<SoulLaserBaseBlockEntity> createEnergyStorage() {
        return new EnergyStorageComponent<>(0, 4, 10);
    }

    @Override
    public ProgressBarComponent<SoulLaserBaseBlockEntity> getBar() {
        return work;
    }

    @Override
    public boolean canAcceptAugment(ItemStack augment) {
        if (AugmentWrapper.hasType(augment, AugmentTypes.SPEED)) {
            return false;
        }
        return super.canAcceptAugment(augment);
    }

    @Override
    public void clearRemoved() {
        super.clearRemoved();
        if (!level.isClientSide) {
            NetworkManager networkManager = NetworkManager.get(level);

            if (networkManager.getElement(worldPosition) == null) {
                networkManager.addElement(createElement(level, worldPosition));
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
            }

            networkManager.removeElement(worldPosition);
        }
    }

    protected NetworkElement createElement(Level level, BlockPos pos) {
        return new DefaultSoulNetworkElement(level, pos);
    }

    public SoulNetwork getNetwork() {
        return (SoulNetwork) NetworkManager.get(this.level).getElement(worldPosition).getNetwork();
    }
}
