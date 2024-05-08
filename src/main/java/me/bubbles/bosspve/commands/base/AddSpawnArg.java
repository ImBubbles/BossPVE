package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.stages.Stage;
import me.bubbles.bosspve.utility.UtilLocation;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class AddSpawnArg extends Argument {

    public AddSpawnArg(BossPVE plugin, int index) {
        super(plugin, "addspawn", "addspawn <entity> <interval>", index);
        setPermission("mapcreator");
    }

    @Override
    public void run(CommandSender sender, String[] args, boolean alias) {
        super.run(sender, args, alias);
        if(!permissionCheck()) {
            return;
        }
        if(!utilSender.isPlayer()) {
            utilSender.sendMessage("%prefix% %primary%Must be in game to do this!");
            return;
        }
        if(args.length<=relativeIndex+1) {
            utilSender.sendMessage(getArgsMessage());
            return;
        }
        Player player = utilSender.getPlayer();
        Stage stage = plugin.getStageManager().getStage(player.getLocation());
        if(stage==null) {
            utilSender.sendMessage("%prefix% %primary%Must be inside a stage to do this!");
            return;
        }
        IEntity iEntity = plugin.getEntityManager().getEntityByName(args[relativeIndex]);
        if(iEntity ==null) {
            utilSender.sendMessage(getEntitiesList());
            utilSender.sendMessage(getArgsMessage());
            return;
        }
        int interval;
        try {
            interval=Integer.parseInt(args[relativeIndex+1]);
        } catch(NumberFormatException e) {
            utilSender.sendMessage("%primary% %primary%Not a valid number");
            return;
        }
        if(interval<=0) {
            utilSender.sendMessage("%primary% %primary%Number must be greater than 0!");
            return;
        }
        ConfigurationSection entities = stage.getConfigurationSection().getConfigurationSection("entities");
        if(entities==null) {
            entities = stage.getConfigurationSection().createSection("entities");
        }
        int newKey = entities.getKeys(false).size()+1;
        ConfigurationSection newEntry = entities.createSection(String.valueOf(newKey));
        newEntry.set("entity", args[relativeIndex]);
        newEntry.set("pos",UtilLocation.asLocationString(player.getLocation()));
        newEntry.set("interval",interval);
        plugin.getConfigManager().saveAll();
        plugin.initStageManager();
        utilSender.sendMessage("%prefix% %primary%Entry added.");
    }

    private String getEntitiesList() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("%prefix% %primary%Entities:");
        for(IEntity entity : plugin.getEntityManager().getEntities()) {
            stringBuilder.append("\n").append("%primary%").append("- ").append("%secondary%").append(ChatColor.stripColor(entity.getUncoloredName()).replaceAll(" ","_"));
        }
        return stringBuilder.toString();
    }

}
