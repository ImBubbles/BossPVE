package me.bubbles.bosspve.commands.base.mapcreator;

import com.fastasyncworldedit.core.Fawe;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.Region;
import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.stages.StageManager;
import me.bubbles.bosspve.utility.UtilNumber;
import me.bubbles.bosspve.utility.location.MapCreator;
import me.bubbles.bosspve.utility.location.UtilWorldEdit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateStageArg extends Argument {

    public CreateStageArg(int index) {
        super("createstage", "createstage <#>", index);
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
        Double stageNumber = UtilNumber.toNumber(args[relativeIndex]);
        if(stageNumber==null) {
            utilSender.sendMessage("%prefix% %primary%Stage must be a number.");
            return;
        }
        /*Double killAllDelay = UtilNumber.toNumber(args[relativeIndex+1]);
        if(killAllDelay==null) {
            utilSender.sendMessage("%prefix% %primary%Kill All Delay must be a number");
            return;
        }*/
        LocalSession localSession = UtilWorldEdit.getLocalSession(utilSender.getPlayer());
        Region region = localSession.getSelection();
        if(region==null) {
            utilSender.sendMessage("%prefix% %primary%No region selected.");
            return;
        }
        Location min = UtilWorldEdit.getBlockLocation(region.getWorld(), region.getMinimumPoint());
        if(min==null) {
            utilSender.sendMessage("%prefix% %primary%Position 1 not found.");
            return;
        }
        Location max = UtilWorldEdit.getBlockLocation(region.getWorld(), region.getMaximumPoint());
        if(max==null) {
            utilSender.sendMessage("%prefix% %primary%Position 2 not found.");
            return;
        }
        StageManager stageManager = BossPVE.getInstance().getStageManager();
        if(stageManager.getStage(min)!=null||stageManager.getStage(max)!=null) {
            utilSender.sendMessage("%prefix% %primary%Stage is overlapping another stage!");
            return;
        }
        Player player = utilSender.getPlayer();

        MapCreator.createStage(stageNumber.intValue(), player.getLocation(), min, max, 1D, 1D, 24000, 50);

    }
}
