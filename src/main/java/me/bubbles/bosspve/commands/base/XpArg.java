package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.utility.UtilUserData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class XpArg extends Argument {

    public XpArg(int index) {
        super("xp", "xp <player>", index);
        setPermission("xp");
        setAlias("xp");
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
        if(args.length==relativeIndex) { // no args
            UtilUserData uud = BossPVE.getInstance().getGameManager().getGamePlayer(utilSender.getPlayer().getUniqueId()).getCache();
            utilSender.sendMessage("%prefix% %primary%Your xp is %secondary%"+uud.getXp()+"%primary%.");
            return;
        }
        OfflinePlayer player = Bukkit.getOfflinePlayer(args[relativeIndex]);
        if(!utilSender.hasPermission("bosspve.xp.other")) {
            utilSender.sendMessage("%prefix% %primary%You do not have permission to do that.");
            return;
        }
        if(!player.hasPlayedBefore()&&player.getPlayer()==null) {
            utilSender.sendMessage("%prefix% %primary%Could not find player %secondary%"+args[relativeIndex]+"%primary%.");
            return;
        }
        Player onlinePlayer = player.getPlayer();
        UtilUserData uud;
        if(onlinePlayer!=null) {
            uud=BossPVE.getInstance().getGameManager().getGamePlayer(onlinePlayer).getCache();
        } else {
            uud=UtilUserData.getUtilUserData(player.getUniqueId());
        }
        utilSender.sendMessage("%prefix% %secondary%"+player.getName()+"'s %primary%xp is %secondary%"+uud.getXp()+"%primary%.");
    }

}
