package com.roykpakke.BCMod.client;

import com.roykpakke.BCMod.BCMod;
import com.roykpakke.BCMod.util.handlers.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class BrightnessEventHandler {

    private static final float MIN_GAMMA = -2.0F;
    private static final float MAX_GAMMA = 2.0F;

    private boolean initApplied = false;

    public static float brightnessToGamma(int brightness) {
        float gamma = brightness / 100.0F;
        return clamp(gamma, MIN_GAMMA, MAX_GAMMA);
    }

    private static float clamp(float value, float min, float max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public void applyBrightness() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.gameSettings == null) {
            return;
        }
        float gamma = brightnessToGamma(ConfigHandler.BRIGHTNESS);
        mc.gameSettings.gammaSetting = gamma;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!ConfigHandler.BRIGHTNESS_OVERRIDE_ENABLED) {
            return;
        }

        if (initApplied) {
            return;
        }

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.gameSettings == null) {
            return;
        }

        applyBrightness();
        initApplied = true;
        BCMod.LOGGER.info("Brightness applied from config: " + ConfigHandler.BRIGHTNESS + "%");
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (!ConfigHandler.BRIGHTNESS_OVERRIDE_ENABLED) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.currentScreen instanceof GuiVideoSettings && !(event.getGui() instanceof GuiVideoSettings)) {
            applyBrightness();
        }
    }
}
