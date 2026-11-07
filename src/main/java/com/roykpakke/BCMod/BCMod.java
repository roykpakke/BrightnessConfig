package com.roykpakke.BCMod;

import com.roykpakke.BCMod.client.BrightnessEventHandler;
import com.roykpakke.BCMod.util.handlers.ConfigHandler;
import com.roykpakke.BCMod.util.handlers.RegistryHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.roykpakke.BCMod.util.Ref;

import java.io.File;


@Mod(
	modid = Ref.MODID,
	name = Ref.NAME,
	version = Ref.VERSION
)
public class BCMod {
	public static final Logger LOGGER = LogManager.getLogger(Ref.MODID);

	public static File config;

	@Mod.Instance
	public static BCMod instance;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {RegistryHandler.preInitRegistries(event);
	}
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new BrightnessEventHandler());
	}
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {LOGGER.info("Post-initializing");
	}
}
