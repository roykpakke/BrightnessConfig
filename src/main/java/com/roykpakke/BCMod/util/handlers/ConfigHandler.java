package com.roykpakke.BCMod.util.handlers;

import java.io.File;

import com.roykpakke.BCMod.BCMod;
import com.roykpakke.BCMod.util.Ref;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {

    public static Configuration config;

    public static int BRIGHTNESS = 0;
    public static boolean BRIGHTNESS_OVERRIDE_ENABLED = true;

    public static void init(File file) {
        config = new Configuration(file);
        loadConfig();
    }

    public static void loadConfig() {
        if (config == null) return;

        config.load();

            String category = "BRIGHTNESS VALUES";

        config.addCustomCategoryComment(category, "Brightness override settings (changes require restart)");
        BRIGHTNESS_OVERRIDE_ENABLED = config.getBoolean("EnableBrightnessOverride", category, true, "When true, the brightness value from config overrides the in-game brightness slider. Set to false to disable the override.");
        BRIGHTNESS = config.getInt("Brightness", category, 0, -200, 200, "Set brightness percentage. 0 = normal, 200 = very bright, -200 = the abyss is gazing back. Only applied if EnableBrightnessOverride is true.");

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static void registerConfig(FMLPreInitializationEvent event) {
        BCMod.config = new File(event.getModConfigurationDirectory() + "/" + Ref.MODID);
        init(new File(BCMod.config.getPath() + ".cfg"));
    }
}
