package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.util.UtilUserData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class GiveXpArg extends Argument {

    public GiveXpArg(BossPVE plugin, int index) {
        super(plugin, "givexp", "givexp <player> <amount>", index);
        setPermission("givexp");
    }

    @Override
    public void run(CommandSender sender, String[] args, boolean alias) {
        super.run(sender, args, alias);
        if(!permissionCheck()) {
            return;
        }
        if(args.length!=relativeIndex+2) { //send 2 args
            utilSender.sendMessage(getArgsMessage());
            return;
        }
        OfflinePlayer player = Bukkit.getOfflinePlayer(args[relativeIndex]);
        if(!player.hasPlayedBefore()&&player.getPlayer()==null) {
            utilSender.sendMessage("%prefix% %primary%Could not find player.");
            return;
        }
        int xp;
        try {
            xp=Integer.parseInt(args[relativeIndex+1]);
        } catch(NumberFormatException e) {
            utilSender.sendMessage(getArgsMessage());
            return;
        }
        UtilUserData uud = plugin.getGameManager().getGamePlayer(player.getUniqueId()).getCache();
        uud.setXp(uud.getXp()+xp);
        UtilUserData.save(plugin, uud);
        utilSender.sendMessage("%prefix% %secondary%"+player.getName()+"'s%primary% xp is now %secondary%"+uud.getXp()+"%primary%.");
    }

}
