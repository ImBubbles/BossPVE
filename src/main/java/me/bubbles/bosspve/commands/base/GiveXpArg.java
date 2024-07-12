package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.utility.UtilUserData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class GiveXpArg extends Argument {

    public GiveXpArg(int index) {
        super("givexp", "givexp <player> <amount>", index);
        setPermission("admin");
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
        /*UtilUserData uud = plugin.getGameManager().getGamePlayer(player.getUniqueId()).getCache();
        uud.setXp(uud.getXp()+xp);*/
        GamePlayer gamePlayer = BossPVE.getInstance().getGameManager().getGamePlayer(player.getUniqueId());
        gamePlayer.setXp(gamePlayer.getCache().getXp()+xp);
        //UtilUserData.save(plugin, uud);
        utilSender.sendMessage("%prefix% %secondary%"+player.getName()+"'s%primary% xp is now %secondary%"+gamePlayer.getCache().getXp()+"%primary%.");
    }

}
