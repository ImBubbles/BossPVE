package me.bubbles.bosspve.commands.other;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Command;
import me.bubbles.bosspve.utility.UtilLocation;
import org.bukkit.command.CommandSender;

public class Spawn extends Command {

    public Spawn(BossPVE plugin) {
        super(plugin, "spawn");
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
                    plugin.getConfigManager().getConfig("config.yml").getFileConfiguration().set("spawn",UtilLocation.asLocationString(utilSender.getPlayer().getLocation()));
                    utilSender.sendMessage("%prefix% %primary%Spawn has been set.");
                    plugin.getConfigManager().saveAll();
                    return;
                }
            }
        }
        utilSender.sendMessage("%prefix% %primary%Teleporting to spawn.");
        utilSender.getPlayer().teleport(UtilLocation.toLocation(plugin,plugin.getConfigManager().getConfig("config.yml").getFileConfiguration().getString("spawn")));
    }

}
