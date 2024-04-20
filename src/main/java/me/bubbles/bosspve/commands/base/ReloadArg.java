package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import org.bukkit.command.CommandSender;

public class ReloadArg extends Argument {

    public ReloadArg(BossPVE plugin, int index) {
        super(plugin,"reload",index);
        setPermission("reload");
    }

    @Override
    public void run(CommandSender sender, String[] args, boolean alias) {
        super.run(sender,args,false);
        if(!permissionCheck()) {
            return;
        }
        utilSender.sendMessage("%prefix% %primary%Config reloaded.");
        plugin.saveDefaultConfig();
        plugin.reload();
    }

}
