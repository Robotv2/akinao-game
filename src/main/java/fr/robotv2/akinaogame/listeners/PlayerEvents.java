package fr.robotv2.akinaogame.listeners;

import org.bukkit.entity.Player;
import fr.robotv2.akinaogame.AkinaoGame;
import fr.robotv2.akinaogame.impl.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerEvents implements Listener {

    private final AkinaoGame game;
    public PlayerEvents(AkinaoGame game) {
        this.game = game;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(game.getGameStateManager().getCurrent() != GameState.PLAYING) {
            if(!(event.getEntity() instanceof Player))
                return;

            event.setCancelled(true);

            if(event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                game.getLobbyManager().teleportToLobby((Player) event.getEntity());
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(game.getGameStateManager().getCurrent() != GameState.PLAYING) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(game.getGameStateManager().getCurrent() != GameState.PLAYING) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        event.setCancelled(true);
    }
}
