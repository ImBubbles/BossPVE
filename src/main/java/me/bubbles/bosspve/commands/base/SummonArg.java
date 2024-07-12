package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.entities.manager.IEntity;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class SummonArg extends Argument {

    public SummonArg(int index) {
        super("summon", "summon <entity>", index);
        setPermission("admin");
    }

    @Override
    public void run(CommandSender sender, String[] args, boolean alias) {
        super.run(sender,args,alias);
        if(!permissionCheck()) {
            return;
        }
        if(args.length==relativeIndex) { // SENDS NO ARGS
            utilSender.sendMessage(getEntitiesList());
            return;
        }
        if(!utilSender.isPlayer()) {
            utilSender.sendMessage("%prefix% %primary%You must be in game to do this.");
            return;
        }
        IEntity base = BossPVE.getInstance().getEntityManager().getEntityByName(args[relativeIndex]);
        if(base==null) {
            utilSender.sendMessage("%prefix% %primary%Entity %secondary%"+args[relativeIndex]+"%primary% does not exist.");
            return;
        }
        base.spawn(utilSender.getPlayer().getLocation());
        utilSender.sendMessage("%prefix% %primary%Entity %secondary%"+ChatColor.stripColor(base.getUncoloredName())+"%primary% has been spawned.");
    }

    private String getEntitiesList() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("%prefix% %primary%Entities:");
        for(IEntity entity : BossPVE.getInstance().getEntityManager().getEntities()) {
            stringBuilder.append("\n").append("%primary%").append("- ").append("%secondary%").append(ChatColor.stripColor(entity.getUncoloredName()).replaceAll(" ","_"));
        }
        return stringBuilder.toString();
    }

}
