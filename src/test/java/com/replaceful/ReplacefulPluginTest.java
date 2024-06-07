package com.replaceful;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ReplacefulPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ReplacefulPlugin.class);
		RuneLite.main(args);
	}
}