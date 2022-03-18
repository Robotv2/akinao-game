package fr.robotv2.akinaogame.listeners;

import fr.robotv2.akinaogame.AkinaoGame;
import fr.robotv2.akinaogame.impl.GameState;
import fr.robotv2.akinaogame.util.StringUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitJoinEvent implements Listener {

    private final AkinaoGame game;
    public QuitJoinEvent(AkinaoGame game) {
        this.game = game;
    }

    @EventHandler
    public void onConnect(PlayerLoginEvent event) {
        switch (game.getGameStateManager().getCurrent()) {
            case PLAYING:
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, StringUtil.colorize("&cLa partie a déjà commencé. (skuuuuuuuu)"));
                break;
            case INITIATING:
            case RESTARTING:
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, StringUtil.colorize("&cLe serveur est pas encore prêt connard."));
                break;
            case STARTING:
                if(game.getTotalPlayers() > game.getCountManager().getMaxCount()) {
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, StringUtil.colorize("&cIl y a déjà trop de personne connecté sur le serveur."));
                    return;
                }
                break;
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(this.isCurrentOr(GameState.WAITING_FOR_PLAYERS, GameState.STARTING)) {
            game.getLobbyManager().teleportToLobby(event.getPlayer());
            event.setJoinMessage(StringUtil.colorize("&eLe joueur &f" + event.getPlayer().getName() + " &evient de rejoindre la partie &f(" + game.getTotalPlayers() + "/" + game.getCountManager().getMaxCount() + ")"));
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    public boolean isCurrentOr(GameState... states) {
        for(GameState state : states) {
            if (state == game.getGameStateManager().getCurrent())
                return true;
        }
        return false;
    }

}
