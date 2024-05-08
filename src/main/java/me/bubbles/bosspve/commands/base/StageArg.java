package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.stages.Stage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StageArg extends Argument {

    public StageArg(BossPVE plugin, int index) {
        super(plugin, "stage", "stage <number>", index);
        setPermission("stage");
        setAlias("stage");
    }

    @Override
    public void run(CommandSender sender, String[] args, boolean alias) {
        super.run(sender, args, alias);
        //TODO allow 0 args to be passed to open gui
        if(!permissionCheck()) {
            return;
        }
        if(!utilSender.isPlayer()) {
            utilSender.sendMessage("%prefix% %primary%You must be in game to do this!");
            return;
        }
        Player player = utilSender.getPlayer();
        if(args.length!=relativeIndex+1) {
            Stage stage = plugin.getStageManager().getHighestAllowedStage(player);
            player.teleport(stage.getSpawn());
            return;
        }
        int stageNum;
        try {
            stageNum=Integer.parseInt(args[relativeIndex]);
        } catch(NumberFormatException e) {
            utilSender.sendMessage("%prefix% %primary%Could not find stage!"+"\n%primary%Your stages: "+getAvailableStages());
            return;
        }
        Stage stage = plugin.getStageManager().getStage(stageNum);
        if(stage==null) {
            utilSender.sendMessage("%prefix% %primary%Could not find stage!"+"\n%primary%Your stages: "+getAvailableStages());
            return;
        }
        if(!stage.isAllowed(utilSender.getPlayer())) {
            utilSender.sendMessage("%prefix% %primary%Hey, you can't go there!");
            return;
        }
        utilSender.sendMessage("%prefix% %primary%Teleporting to stage %secondary%"+stage.getLevelRequirement()+"%primary%.");
        utilSender.getPlayer().teleport(stage.getSpawn());
        plugin.getGameManager().getGamePlayer(utilSender.getPlayer().getUniqueId()).healPercent(1);
    }

    private String getAvailableStages() {
        int playerLevel = plugin.getGameManager().getGamePlayer(utilSender.getPlayer().getUniqueId()).getCache().getLevel();
        List<Integer> allowedStages = new ArrayList<>();
        plugin.getStageManager().getStages().stream()
                .filter(stage -> playerLevel>=stage.getLevelRequirement())
                .forEach(stage -> allowedStages.add(stage.getLevelRequirement())
                );
        Collections.sort(allowedStages);
        StringBuilder stringBuilder = new StringBuilder();
        boolean first=true;
        for(Integer stage : allowedStages) {
            if(!first) {
                stringBuilder.append("%primary%, ");
            } else {
                first=false;
            }
            stringBuilder.append("%secondary%").append(stage);
        }
        return stringBuilder.toString();
    }

}