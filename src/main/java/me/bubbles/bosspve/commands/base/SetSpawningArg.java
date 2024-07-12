package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import org.bukkit.command.CommandSender;

public class SetSpawningArg extends Argument {

    public SetSpawningArg(int index) {
        super("spawning", "spawning <level> <true/false>", index);
    }

    @Override
    public void run(CommandSender sender, String[] args, boolean alias) {
        super.run(sender, args, alias);
        // TODO
    }

}
