package com.buuz135.industrialforegoingsouls.capabilities;

import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public class SoulCapabilities {

    public static final BlockCapability<ISoulHandler, @Nullable Direction> BLOCK =
            BlockCapability.createSided(ResourceLocation.fromNamespaceAndPath(IndustrialForegoingSouls.MOD_ID, "soul"), ISoulHandler.class);

}
