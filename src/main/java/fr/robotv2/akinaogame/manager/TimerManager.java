package fr.robotv2.akinaogame.manager;

import fr.robotv2.akinaogame.AkinaoGame;
import fr.robotv2.akinaogame.impl.GameState;
import fr.robotv2.akinaogame.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Nullable;

public class TimerManager implements Listener {

    private final AkinaoGame game;
    private BukkitTask task;

    private RequiredSeconds current;
    private int timer = 120;

    public TimerManager(AkinaoGame game) {
        this.game = game;
        this.current = RequiredSeconds.NONE;
        game.getServer().getPluginManager().registerEvents(this, game);
    }

    public enum RequiredSeconds {
        NONE(-1, 1),
        SECONDS_120(120,4),
        SECONDS_60(60, 3),
        SECONDS_30(30, 2),
        SECONDS_10(10, 1.5);

        public final double divisor;
        public final int time;
        RequiredSeconds(int time, double divisor) {
            this.divisor = divisor;
            this.time = time;
        }

        @Nullable
        public RequiredSeconds getNext() {

            switch (this) {
                case NONE: return SECONDS_120;
                case SECONDS_120: return SECONDS_60;
                case SECONDS_60: return SECONDS_30;
                case SECONDS_30: return SECONDS_10;
            }

            return null;
        }

        @Nullable
        public RequiredSeconds getBefore() {

            switch (this) {
                case SECONDS_10: return SECONDS_30;
                case SECONDS_30: return SECONDS_60;
                case SECONDS_60: return SECONDS_120;
                case SECONDS_120: return NONE;
            }

            return null;
        }
    }

    public int getRequiredPlayer(RequiredSeconds seconds) {
        return (int) Math.floor(game.getCountManager().getMaxCount() / seconds.divisor);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.handleJoin();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.handleQuit();
    }

    public void handleJoin() {
        RequiredSeconds next = current.getNext();

        if(next == null)
            return;

        if(game.getTotalPlayers() >= this.getRequiredPlayer(next)) {
            current = next;
            this.stopTask();
            this.initTimer(next);
        }
    }

    public void handleQuit() {
        RequiredSeconds before = current.getBefore();

        if(before == null)
            return;

        if(before == RequiredSeconds.NONE) {
            this.stopTask();
            return;
        }

        if(game.getTotalPlayers() < this.getRequiredPlayer(before)) {
            current = before;
        }
    }

    public void initTimer(RequiredSeconds seconds) {

        if(seconds.time < timer)
            timer = seconds.time;

        task = Bukkit.getScheduler().runTaskTimer(game, () -> {

            if(timer < 0) {
                this.stopTask();
                game.getGameStateManager().setCurrent(GameState.PLAYING);
            }

            else if(timer < 10) {
                broadcastTimer(timer);
            }

            else if(this.isBetween(timer, 10, 30)) {
                if(timer % 5 == 0)
                    broadcastTimer(timer);
            }

            else if(this.isBetween(timer, 30, 60)) {
                if(timer % 10 == 0)
                    broadcastTimer(timer);
            }

            else if(timer <= 120) {
                if(timer % 20 == 0)
                    broadcastTimer(timer);
            }

            --this.timer;

        }, 20, 20);
    }

    public void stopTask() {
        if(task != null)
            task.cancel();
    }

    public boolean isBetween(int value, int min, int max) {
        return value >= min && value <= max;
    }

    public void broadcastTimer(int timer) {
        StringUtil.broadcast("&fLa partie commence dans &e" + timer + " seconde(s) !");
    }
}
