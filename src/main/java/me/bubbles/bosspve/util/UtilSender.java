package me.bubbles.bosspve.util;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.util.string.UtilString;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UtilSender {

    private CommandSender sender;
    private BossPVE plugin;

    public UtilSender(BossPVE plugin, CommandSender sender) {
        this.plugin=plugin;
        this.sender=sender;
    }

    public boolean isPlayer() {
        return sender instanceof Player;
    }

    public CommandSender getSender() {
        return sender;
    }

    public void sendMessage(String message) {
        if(isPlayer()) {
            getPlayer().sendMessage(UtilString.colorFillPlaceholders(message));
            return;
        }
        Bukkit.getConsoleSender().sendMessage(UtilString.colorFillPlaceholders(message.replace("\n","\n&f")));
    }

    public Player getPlayer() {
        if(!isPlayer()){
            return null;
        }
        return (Player) sender;
    }

    public boolean hasPermission(String permission) {
        if(!isPlayer()) {
            return true;
        }
        return getPlayer().hasPermission(permission);
    }

    public GamePlayer getGamePlayer() {
        if(!isPlayer()) {
            return null;
        }
        return plugin.getGameManager().getGamePlayer(getPlayer());
    }

}
