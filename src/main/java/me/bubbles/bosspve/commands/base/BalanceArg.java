package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.utility.UtilNumber;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class BalanceArg extends Argument {

    public BalanceArg(int index) {
        super("balance", "balance <player>", index);
        setAlias("balance");
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
        OfflinePlayer player;
        if(args.length==relativeIndex) { // no args
            player = utilSender.getPlayer();
            utilSender.sendMessage("%prefix% %primary%Your balance is %secondary%$"+BossPVE.getInstance().getEconomy().getBalance(player)+"%primary%.");
            return;
        }
        player = Bukkit.getOfflinePlayer(args[relativeIndex]);
        if(!utilSender.hasPermission("bosspve.balance.other")) {
            utilSender.sendMessage("%prefix% %primary%You do not have permission to do that.");
        }
        if(!player.hasPlayedBefore()&&player.getPlayer()==null) {
            utilSender.sendMessage("%prefix% %primary%Could not find player %secondary%"+args[relativeIndex]+"%primary%.");
            return;
        }
        utilSender.sendMessage("%prefix% %secondary%"+player.getName()+"'s %primary%balance is %secondary%$"+ UtilNumber.formatMoney(BossPVE.getInstance().getEconomy().getBalance(player))+"%primary%.");
    }

}
