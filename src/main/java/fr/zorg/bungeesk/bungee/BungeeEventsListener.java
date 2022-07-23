package fr.zorg.bungeesk.bungee;

import fr.zorg.bungeesk.bungee.packets.PacketServer;
import fr.zorg.bungeesk.common.entities.BungeePlayer;
import fr.zorg.bungeesk.common.packets.BungeePlayerJoinPacket;
import fr.zorg.bungeesk.common.packets.BungeePlayerLeavePacket;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeEventsListener implements Listener {

    @EventHandler
    public void onJoin(PostLoginEvent e) {
        final ProxiedPlayer player = e.getPlayer();
        final BungeePlayer bungeePlayer = new BungeePlayer(player.getName(), player.getUniqueId());
        final BungeePlayerJoinPacket packet = new BungeePlayerJoinPacket(bungeePlayer);
        PacketServer.broadcastPacket(packet);
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent e) {
        final ProxiedPlayer player = e.getPlayer();
        final BungeePlayer bungeePlayer = new BungeePlayer(player.getName(), player.getUniqueId());
        final BungeePlayerLeavePacket packet = new BungeePlayerLeavePacket(bungeePlayer);
        PacketServer.broadcastPacket(packet);
    }

}