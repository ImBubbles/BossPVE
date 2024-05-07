package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.database.databases.SettingsDB;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.settings.Settings;
import me.bubbles.bosspve.util.UtilDatabase;
import me.bubbles.bosspve.util.UtilUserData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ToggleMsgs extends Argument {

    public ToggleMsgs(BossPVE plugin, int index) {
        super(plugin, "togglemsgs", index);
    }

    @Override
    public void run(CommandSender sender, String[] args, boolean alias) {
        super.run(sender, args, alias);
        if(!utilSender.isPlayer()) {
            utilSender.sendMessage("%prefix% %primary%You must be in game to do this!");
            return;
        }
        Player player = utilSender.getPlayer();
        SettingsDB db = UtilDatabase.SettingsDB();
        GamePlayer gamePlayer = plugin.getGameManager().getGamePlayer(player.getUniqueId());
        UtilUserData uud = gamePlayer.getCache();
        boolean result = SettingsDB.getValue(uud, Settings.KILL_MESSAGES)==1;
        int newValue = !result ? 1 : 0;
        db.setRelation(player.getUniqueId(), Settings.KILL_MESSAGES.toString(), newValue);
        gamePlayer.updateCache();
        utilSender.sendMessage("%prefix% %primary%Mob kill messages have been set to %secondary%"+!result+"%primary%.");
    }

}
