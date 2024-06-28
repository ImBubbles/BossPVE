package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.stages.Stage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class DebugArg extends Argument {

    public DebugArg(BossPVE plugin, int index) {
        super(plugin, "debug", "debug", index);
        setPermission("debug");
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
        result+="Stages: "+plugin.getStageManager().getStages().size();
        result+="\nGame Entities: "+plugin.getGameManager().getGameEntities().size();
        for(Stage stage : plugin.getStageManager().getStages()) {
            result+="\nStage "+stage.getLevelRequirement()+" Entities: "+stage.getSpawned().size();
        }
        result+="\nRegistered Entities Classes "+plugin.getEntityManager().getEntities().size();
        result+="\nRegistered Events "+plugin.getEventManager().getEvents().size();
        utilSender.sendMessage(result);
    }

}