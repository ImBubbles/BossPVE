package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.utility.UtilUserData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class LevelArg extends Argument {

    public LevelArg(BossPVE plugin, int index) {
        super(plugin, "level", "level <player>", index);
        setPermission("level");
        setAlias("level");
    }

    @Override
    @SuppressWarnings("deprecation")
    public void run(CommandSender sender, String[] args, boolean alias) {
        super.run(sender, args, alias);
        if(!permissionCheck()) {
            return;
        }
        if(!utilSender.isPlayer()) {
            if(args.length==relativeIndex) {
                utilSender.sendMessage(getArgsMessage());
                return;
            }
        }
        UtilUserData uud = plugin.getGameManager().getGamePlayer(utilSender.getPlayer().getUniqueId()).getCache();
        if(args.length==relativeIndex) { // no args
            utilSender.sendMessage("%prefix% %primary%Your level is %secondary%"+uud.getLevel()+"%primary%.");
            return;
        }
        OfflinePlayer player = Bukkit.getOfflinePlayer(args[relativeIndex]);
        if(!utilSender.hasPermission("bosspve.level.other")) {
            utilSender.sendMessage("%prefix% %primary%You do not have permission to do that.");
            return;
        }
        if(!player.hasPlayedBefore()&&player.getPlayer()==null) {
            utilSender.sendMessage("%prefix% %primary%Could not find player %secondary%"+args[relativeIndex]+"%primary%.");
            return;
        }
        utilSender.sendMessage("%prefix% %secondary%"+player.getName()+"'s %primary%level is %secondary%"+uud.getLevel()+"%primary%.");
    }

}
