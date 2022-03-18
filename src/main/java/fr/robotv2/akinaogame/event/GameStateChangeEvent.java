package fr.robotv2.akinaogame.event;

import com.avaje.ebean.validation.NotNull;
import fr.robotv2.akinaogame.impl.GameState;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStateChangeEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final GameState current;

    public GameStateChangeEvent(GameState current) {
        this.current = current;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public GameState getState() {
        return current;
    }
}
