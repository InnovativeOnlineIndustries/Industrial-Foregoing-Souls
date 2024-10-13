package com.buuz135.industrialforegoingsouls.client;

import com.buuz135.industrialforegoingsouls.IndustrialForegoingSouls;
import com.buuz135.industrialforegoingsouls.block.tile.SoulLaserBaseBlockEntity;
import com.buuz135.industrialforegoingsouls.config.ConfigSoulLaserBase;
import com.hrznstudio.titanium.client.screen.addon.BasicScreenAddon;
import com.hrznstudio.titanium.client.screen.asset.IAssetProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SculkSoulTankScreenAddon extends BasicScreenAddon {

    private final SoulLaserBaseBlockEntity soulLaserBaseBlockEntity;
    private List<GuiParticle> particleList = new ArrayList<>();
    private long lastCheckedForParticle;

    public SculkSoulTankScreenAddon(int posX, int posY, SoulLaserBaseBlockEntity soulLaserBaseBlockEntity) {
        super(posX, posY);
        this.soulLaserBaseBlockEntity = soulLaserBaseBlockEntity;
        this.lastCheckedForParticle = Minecraft.getInstance().level.getGameTime();
    }

    @Override
    public void drawBackgroundLayer(GuiGraphics guiGraphics, Screen screen, IAssetProvider provider, int guiX, int guiY, int mouseX, int mouseY, float partialTicks) {
        var fullAmount = Math.min(1, soulLaserBaseBlockEntity.getSoulAmount() / (double) ConfigSoulLaserBase.SOUL_STORAGE_AMOUNT);
        var currentTime = Minecraft.getInstance().level.getGameTime();
        if (this.lastCheckedForParticle != currentTime) {
            if (Minecraft.getInstance().level.random.nextDouble() <= fullAmount) {
                particleList.add(new GuiParticle(Minecraft.getInstance().level.random.nextInt(getXSize() - 14), getYSize() - 17 - Minecraft.getInstance().level.random.nextInt(10), currentTime));
            }
            this.lastCheckedForParticle = currentTime;
        }
        var ageTick = 3;
        if (currentTime % ageTick == 0) {
            particleList.removeIf(guiParticle -> ((currentTime - guiParticle.age) / ageTick) > 10);
        }
        for (GuiParticle guiParticle : particleList) {
            var particleAge = ((currentTime - guiParticle.age) / (double) ageTick);
            var extraY = -((getYSize() - 32) / 10D) * particleAge;
            guiGraphics.blit(ResourceLocation.withDefaultNamespace("textures/particle/sculk_soul_" + Math.max(0, Math.min(10, (int) particleAge)) + ".png"), this.getPosX() + guiX + guiParticle.x, (int) (this.getPosY() + guiY + guiParticle.y + extraY), 0, 0, 16, 16, 16, 16);
        }
        guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(IndustrialForegoingSouls.MOD_ID, "textures/gui/soul_tank.png"), this.getPosX() + guiX, (this.getPosY() + guiY), 0, 0, 65, 60, 65, 60);
    }

    @Override
    public void drawForegroundLayer(GuiGraphics guiGraphics, Screen screen, IAssetProvider provider, int guiX, int guiY, int mouseX, int mouseY, float partialTicks) {
        var fullAmount = Math.min(1, soulLaserBaseBlockEntity.getSoulAmount() / (double) ConfigSoulLaserBase.SOUL_STORAGE_AMOUNT);
        if (isMouseOver(mouseX - guiX, mouseY - guiY)) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, Component.translatable("industrialforegoingsouls.soul_storage").withStyle(ChatFormatting.GOLD)
                    .append(Component.literal(ChatFormatting.WHITE + (new DecimalFormat("##.##").format(fullAmount * 100)))
                            .append("%").withStyle(ChatFormatting.YELLOW)), mouseX - guiX, mouseY - guiY);
        }
    }

    @Override
    public int getXSize() {
        return 65;
    }

    @Override
    public int getYSize() {
        return 60;
    }

    private class GuiParticle {

        private int x;
        private int y;
        private long age;

        public GuiParticle(int x, int y, long age) {
            this.x = x;
            this.y = y;
            this.age = age;
        }
    }
}
