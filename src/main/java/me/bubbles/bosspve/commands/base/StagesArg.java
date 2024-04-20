package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StagesArg extends Argument {

    public StagesArg(BossPVE plugin, int index) {
        super(plugin, "stages", "stages", index);
        setPermission("stages");
        setAlias("stages");
    }

    @Override
    public void run(CommandSender sender, String[] args, boolean alias) {
        super.run(sender, args, alias);
        if(!permissionCheck()) {
            return;
        }
        utilSender.sendMessage("%prefix% %primary%All stages: "+getAvailableStages());
    }

    private String getAvailableStages() {
        List<Integer> allowedStages = new ArrayList<>();
        plugin.getStageManager().getStages().stream()
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