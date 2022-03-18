package fr.robotv2.akinaogame.manager;

import fr.robotv2.akinaogame.AkinaoGame;

public class CountManager {

    private final AkinaoGame plugin;
    private int maxCount;

    public CountManager(AkinaoGame instance) {
        this.plugin = instance;
        maxCount = plugin.getConfig().getInt("max-count");
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
        plugin.getConfig().set("max-count", maxCount);
        plugin.saveConfig();
    }
}
