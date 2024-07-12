package me.bubbles.bosspve.commands.other;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Command;
import me.bubbles.bosspve.utility.location.UtilLocation;
import org.bukkit.command.CommandSender;

public class Spawn extends Command {

    public Spawn() {
        super("spawn");
        setPermission("spawn");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        super.run(sender, args);
        if(!permissionCheck()) {
            return;
        }
        if(!utilSender.isPlayer()) {
            utilSender.sendMessage("%prefix% %primary%You must be in game to do that!");
            return;
        }
        if(args.length==index+1) { // 1 arg
            if(utilSender.hasPermission("setspawn")) {
                if(args[index].equalsIgnoreCase("set")) {
                    BossPVE.getInstance().getConfigManager().getConfig("config.yml").getFileConfiguration().set("spawn",UtilLocation.asLocationString(utilSender.getPlayer().getLocation()));
                    utilSender.sendMessage("%prefix% %primary%Spawn has been set.");
                    BossPVE.getInstance().getConfigManager().saveAll();
                    return;
                }
            }
        }
        utilSender.sendMessage("%prefix% %primary%Teleporting to spawn.");
        utilSender.getPlayer().teleport(UtilLocation.toLocation(BossPVE.getInstance().getConfigManager().getConfig("config.yml").getFileConfiguration().getString("spawn")));
    }

}
