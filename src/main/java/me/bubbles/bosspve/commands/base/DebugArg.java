package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.stages.Stage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class DebugArg extends Argument {

    public DebugArg(int index) {
        super("debug", "debug", index);
        setPermission("admin");
        setAlias("debug");
    }

    @Override
    @SuppressWarnings("deprecation")
    public void run(CommandSender sender, String[] args, boolean alias) {
        super.run(sender, args, alias);
        if(!permissionCheck()) {
            return;
        }
        String result="";
        result+="Stages: "+BossPVE.getInstance().getStageManager().getStages().size();
        result+="\nGame Entities: "+BossPVE.getInstance().getGameManager().getGameEntities().size();
        for(Stage stage : BossPVE.getInstance().getStageManager().getStages()) {
            result+="\nStage "+stage.getLevelRequirement()+" Entities: "+stage.getSpawned().size();
        }
        result+="\nRegistered Entity Classes "+BossPVE.getInstance().getEntityManager().getEntities().size();
        result+="\nRegistered Events "+BossPVE.getInstance().getEventManager().getEvents().size();
        utilSender.sendMessage(result);
    }

}