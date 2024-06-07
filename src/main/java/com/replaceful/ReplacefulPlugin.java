package com.replaceful;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.kit.KitType;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Replaceful"
)
public class ReplacefulPlugin extends Plugin {
	@Inject
	private Client client;

	@Inject
	private ReplacefulConfig config;

	@Override
	protected void startUp() throws Exception {
		log.info("Replaceful started!");
	}

	@MethodHook(
			value = "decodeAppearance",
			end = true
	)

	public void onDecodeAppearance(RSBuffer var1) {

		RSP1ayerComposition composition = this.getP1ayerComposition();
		int currentCapeID = composition.getEquipmentId(KitType.CAPE);

		if (currentCapeID == ItemID.RED_CAPE) {
			composition.getEquipmentIds()[KitType.CAPE.getlndex()] = Item1D.INFERNAL_CAPE + 512;
			composition.setHash();
		}

		if (currentCapeID == ItemID.BLUE_CAPE) {
			composition.getEquipmentIds()[KitType.CAPE.getIndex()] = ItemID.FIRE_CAPE + 512;
			composition.setHash();
		}
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Replaceful stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Replaceful says " + config.greeting(), null);
		}
	}

	@Provides
	ReplacefulConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ReplacefulConfig.class);
	}
}
