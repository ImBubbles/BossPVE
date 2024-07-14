package me.bubbles.bosspve.commands.base.mapcreator;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.regions.Region;
import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.stages.Stage;
import me.bubbles.bosspve.stages.StageManager;
import me.bubbles.bosspve.utility.UtilNumber;
import me.bubbles.bosspve.utility.location.MapCreator;
import me.bubbles.bosspve.utility.location.UtilWorldEdit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnArg extends Argument {

    public SetSpawnArg(int index) {
        super("setspawn", "setspawn", index);
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

        Player player = utilSender.getPlayer();

        Stage stage = BossPVE.getInstance().getStageManager().getStage(player.getLocation());

        if(stage==null) {
            utilSender.sendMessage("%prefix% %primary%Stage not found! Must be inside stage to do this.");
            return;
        }

        MapCreator.setSpawn(MapCreator.getStage(stage), player.getLocation());
        utilSender.sendMessage("%prefix% %primary%Spawn set.");

    }
}
