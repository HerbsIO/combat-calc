package com.hzy.cmbcalc;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

import javax.inject.Inject;
import java.awt.image.BufferedImage;

@Slf4j
@PluginDescriptor(
		name = "Combat Level Calculator",
		description="Enable the Combat Level Calculator panel"
)
public class CombatLevelCalculatorPlugin extends Plugin
{
	@Inject
	private SkillIconManager iconManager;

	@Inject
	private Client client;

	@Inject
	private ClientToolbar clientToolbar;

	private NavigationButton uiNavigationButton;


	@Override
	protected void startUp() throws Exception
	{
		iconManager = new SkillIconManager();
		final CombatLevelCalculatorPanel combatLevelCalculatorPanel = new CombatLevelCalculatorPanel(client, iconManager);
		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "cmb.png");
		clientToolbar.addNavigation(NavigationButton.builder()
				.tooltip("Combat Lvl Calculator")
				.icon(icon)
				.priority(12)
				.panel(combatLevelCalculatorPanel)
				.build());
	}

	@Override
	protected void shutDown() throws Exception
	{
		clientToolbar.removeNavigation(uiNavigationButton);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{

	}
}
