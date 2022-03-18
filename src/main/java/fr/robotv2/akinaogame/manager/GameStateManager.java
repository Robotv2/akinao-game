package fr.robotv2.akinaogame.manager;

import fr.robotv2.akinaogame.AkinaoGame;
import fr.robotv2.akinaogame.event.GameStateChangeEvent;
import fr.robotv2.akinaogame.impl.GameState;

public class GameStateManager {

    private final AkinaoGame game;
    private GameState current;

    public GameStateManager(AkinaoGame game) {
        this.game = game;
        this.current = GameState.INITIATING;
    }

    public GameState getCurrent() {
        return current;
    }

    public void setCurrent(GameState current) {
        System.out.println(current);
        this.current = current;
        game.getServer().getPluginManager().callEvent(new GameStateChangeEvent(current));
    }
}
