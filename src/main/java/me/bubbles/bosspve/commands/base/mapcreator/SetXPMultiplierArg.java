package me.bubbles.bosspve.commands.base.mapcreator;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.stages.Stage;
import me.bubbles.bosspve.utility.UtilNumber;
import me.bubbles.bosspve.utility.location.MapCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetXPMultiplierArg extends Argument {

    public SetXPMultiplierArg(int index) {
        super("setxpmultiplier", "setxpmultiplier <#>", index);
        setPermission("mapcreator");
    }

    @Override
    public void run(CommandSender commandSender, String[] args, boolean alias) {
        super.run(commandSender, args, alias);
        if(!permissionCheck()) {
            return;
        }
        if(!utilSender.isPlayer()) {
            utilSender.sendMessage("%prefix% %primary%Must be in-game to do that!");
            return;
        }
        if(!(args.length>relativeIndex)) {
            utilSender.sendMessage(getArgsMessage());
            return;
        }
        Double number = UtilNumber.toNumber(args[relativeIndex]);
        if(number==null) {
            utilSender.sendMessage("%prefix% %primary%Must be a number.");
            return;
        }
        Player player = utilSender.getPlayer();

        Stage stage = BossPVE.getInstance().getStageManager().getStage(player.getLocation());

        if(stage==null) {
            utilSender.sendMessage("%prefix% %primary%Stage not found! Must be inside stage to do this.");
            return;
        }

        MapCreator.setXpMultiplier(MapCreator.getStage(stage), number.intValue());
        utilSender.sendMessage("%prefix% %primary%Multiplier set.");

    }
}
