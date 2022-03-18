package fr.robotv2.akinaogame.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import fr.robotv2.akinaogame.AkinaoGame;
import fr.robotv2.akinaogame.util.StringUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("game")
@CommandPermission("akinaogame.command.setlobby")
public class GameCommand extends BaseCommand {

    private final AkinaoGame game;
    public GameCommand(AkinaoGame game) {
        this.game = game;
    }

    @Subcommand("setlobby")
    public void onSetLobby(Player player) {
        game.getLobbyManager().setLobby(player.getLocation());
        StringUtil.sendMessage(player, "&aVous venez de mettre la localisation du spawn à votre position.");
    }

    @Subcommand("setmax")
    public void onSetMax(CommandSender sender, int max) {
        game.getCountManager().setMaxCount(max);
        StringUtil.sendMessage(sender, "&aNombre maximum de joueur connecté pour la partie: " + max);
    }
}
