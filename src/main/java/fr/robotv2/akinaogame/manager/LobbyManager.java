package fr.robotv2.akinaogame.manager;

import fr.robotv2.akinaogame.AkinaoGame;
import fr.robotv2.akinaogame.serializer.LocationSerializer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class LobbyManager implements Listener {
    private final AkinaoGame plugin;
    private Location lobby;

    public LobbyManager(AkinaoGame instance) {
        this.plugin = instance;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
        String lobbyStr = plugin.getConfig().getString("lobby-location");
        if(lobbyStr != null) {
            this.lobby = LocationSerializer.fromString(lobbyStr);
            this.lobby.getChunk().load(true);
        }
    }

    public Location getLobby() {
        return lobby;
    }

    public void setLobby(Location lobby) {
        this.lobby = lobby;
        String lobbyStr = LocationSerializer.toString(lobby);
        plugin.getConfig().set("lobby-location", lobbyStr);
        plugin.saveConfig();
    }

    public void teleportToLobby(Player player) {
        if(lobby != null) {
            player.teleport(getLobby(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        }
    }
}
