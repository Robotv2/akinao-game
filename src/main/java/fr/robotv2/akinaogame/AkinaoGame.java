package fr.robotv2.akinaogame;

import co.aikar.commands.PaperCommandManager;
import fr.robotv2.akinaogame.command.GameCommand;
import fr.robotv2.akinaogame.impl.GameState;
import fr.robotv2.akinaogame.listeners.PlayerEvents;
import fr.robotv2.akinaogame.listeners.QuitJoinEvent;
import fr.robotv2.akinaogame.manager.CountManager;
import fr.robotv2.akinaogame.manager.GameStateManager;
import fr.robotv2.akinaogame.manager.LobbyManager;
import fr.robotv2.akinaogame.manager.TimerManager;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class AkinaoGame extends JavaPlugin {

    private LobbyManager lobbyManager;
    private GameStateManager gameStateManager;
    private CountManager countManager;
    private TimerManager timerManager;

    @Override
    public void onEnable() {

        this.loadConfig();
        this.loadManagers();
        this.loadCommands();
        this.loadListeners();

        for(World world : Bukkit.getWorlds()) {
            world.setStorm(false);
            world.setTime(1000);
            world.setGameRuleValue("doDaylightCycle", "false");
        }

        Bukkit.getScheduler().runTaskLater(this, () -> {
            getGameStateManager().setCurrent(GameState.WAITING_FOR_PLAYERS);
        }, 20 * 3);
    }

    @Override
    public void onDisable() {
        getGameStateManager().setCurrent(GameState.RESTARTING);
    }

    //<-- getters -->

    public LobbyManager getLobbyManager() {
        return lobbyManager;
    }

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public CountManager getCountManager() {
        return countManager;
    }

    public TimerManager getTimerManager() {
        return timerManager;
    }

    public int getTotalPlayers() {
        return Bukkit.getOnlinePlayers().size();
    }

    //<-- loaders -->

    public void loadConfig() {
        saveDefaultConfig();
    }

    public void loadCommands() {
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new GameCommand(this));
    }

    public void loadManagers() {
        this.lobbyManager = new LobbyManager(this);
        this.gameStateManager = new GameStateManager(this);
        this.countManager = new CountManager(this);
        this.timerManager = new TimerManager(this);
    }

    public void loadListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new QuitJoinEvent(this), this);
        pm.registerEvents(new PlayerEvents(this), this);
    }
}
