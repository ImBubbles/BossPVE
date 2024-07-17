package me.bubbles.bosspve.commands.base.mapcreator;

import me.bubbles.bosspve.commands.manager.Argument;
import org.bukkit.command.CommandSender;

public class MapCreatorArg extends Argument {

    public MapCreatorArg(int index) {
        super("mapcreator", "mapcreator <setting> [values]", index);
        setPermission("mapcreator");
        setAlias("mapcreator");
        addArguments(
                new AddSpawnArg(index),
                new CreateStageArg(index),
                new SetSpawnArg(index),
                new SetXPMultiplierArg(index),
                new SetMoneyMultiplierArg(index),
                new SetKillAll(index),
                new SetMaxArg(index)
        );
    }

    @Override
    public void run(CommandSender sender, String[] args, boolean alias) {
        if(!permissionCheck()) {
            return;
        }
        if(args.length==0) {
            utilSender.sendMessage(getArgsMessage());
        }
        super.run(sender, args, alias);
        //
    }

}
