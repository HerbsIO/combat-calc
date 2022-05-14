package com.hzy.cmbcalc;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("combatlevelcalculator")
public interface CombatLevelCalculatorConfig extends Config
{
	@ConfigItem(
		keyName = "greeting",
		name = "Combat Level Calculator",
		description = ""
	)
	default String greeting()
	{
		return "Hello";
	}
}
