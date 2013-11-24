package com.ur.onigokko.config;

import org.bukkit.configuration.file.FileConfiguration;

import com.ur.onigokko.Main;

public class ConfigLoader {
	private Main main;
	public ConfigLoader(Main main)
	{
		LoadConfig();
	}
	public void LoadConfig()
	{
		FileConfiguration FConfig = main.getConfig();
		FConfig.addDefault(ConfigVariables.DiamondReward, ConfigVariables.DefaultValue_DR);
		FConfig.options().copyDefaults(true);
		main.saveConfig();
	}

}
