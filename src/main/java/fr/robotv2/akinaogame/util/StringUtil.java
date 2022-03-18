package fr.robotv2.akinaogame.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class StringUtil {

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(colorize(message));
    }

    public static void broadcast(String message) {
        String finalMessage = colorize(message);
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(finalMessage));
    }
}
