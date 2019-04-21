package me.rabrg.clans.zcore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

public class MPluginSecretServerListener implements Listener {
	private final MPlugin p;

	public MPluginSecretServerListener(final MPlugin p) {
		this.p = p;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onServerCommand(final ServerCommandEvent event) {
		if (event.getCommand().length() == 0) {
			return;
		}

		if (p.handleCommand(event.getSender(), event.getCommand())) {
			event.setCommand(p.refCommand);
		}
	}

}
