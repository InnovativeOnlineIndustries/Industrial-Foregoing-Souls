package com.buuz135.industrialforegoingsouls.data;

import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class IFSoulsLangProvider extends LanguageProvider {

    public IFSoulsLangProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        this.add(IndustrialForegoingSouls.SOUL_LASER_BLOCK.getKey().get(), "Soul Laser");
        this.add(IndustrialForegoingSouls.SOUL_PIPE_BLOCK.getKey().get(), "Soul Pipe");
        this.add(IndustrialForegoingSouls.SOUL_SURGE_BLOCK.getKey().get(), "Soul Surge");
        this.add("industrialforegoingsouls.soul_storage", "Soul Storage: ");
        this.add("itemGroup.industrialforegoingsouls", "Industrial Foregoing: Souls");
    }
}
