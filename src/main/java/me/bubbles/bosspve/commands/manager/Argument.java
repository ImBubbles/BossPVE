package me.bubbles.bosspve.commands.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.utility.UtilSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class Argument implements CommandExecutor {

    public int relativeIndex;
    private int index;
    public UtilSender utilSender;
    private ArrayList<Argument> arguments = new ArrayList<>();
    public String no_perms;
    private String arg;
    private String display;
    private String permission;
    private String alias;

    // CONSTRUCTORS
    public Argument(String arg, String display, int index) {
        this.index=index+1;
        this.arg=arg;
        this.display=display;
    }

    public Argument(String arg, int index) {
        this(arg,arg,index);
    }

    // ON RUN
    public void run(CommandSender sender, String[] args, boolean alias) {
        this.utilSender=new UtilSender(sender);
        if(alias) {
            relativeIndex=0;
        }else{
            relativeIndex=index;
        }
        if(!(args.length==relativeIndex)) { // IF PLAYER SENDS ARGUMENTS
            for(Argument argument : getArguments()) { // ARGUMENTS
                if(argument.getArg().equalsIgnoreCase(args[relativeIndex])) {
                    argument.run(sender, args,false);
                    return;
                }
            }
        }
    }

    // ON ALIAS
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        run(sender,args,true);
        return true;
    }

    // ARGUMENTS
    public void addArguments(Argument... args) {
        arguments.addAll(Arrays.asList(args));
    }

    public String getArg() {
        return arg;
    }

    public ArrayList<Argument> getArguments() {
        return arguments;
    }

    // ALIAS
    public void setAlias(String alias) {
        this.alias=alias;
    }

    public String getAlias() {
        return alias;
    }

    // PERMISSION
    public void setPermission(String permission) {
        String node = BossPVE.getInstance().getName().toLowerCase() + "." + permission;
        this.permission=node;
        this.no_perms=BossPVE.getInstance().getConfigManager().getConfig("config.yml").getFileConfiguration().getString("placeholders.no_perms").replace("%node%",node);
    }

    public boolean permissionCheck() {
        if(permission==null)
            return true;
        if(!utilSender.isPlayer()) {
            return true;
        }
        Player player = utilSender.getPlayer();
        if(!player.hasPermission(permission)) {
            utilSender.sendMessage(no_perms);
            return false;
        }else{
            return true;
        }
    }

    // GETTERS

    public String getDisplay() {
        return display;
    }

    public String getArgsMessage() {

        StringBuilder stringBuilder = new StringBuilder();
        String topLine = "%prefix%" + "%primary%" + " " + display;
        stringBuilder.append(topLine);

        for(Argument argument : arguments) {
            if(argument.getPermission()!=null) {
                if(!utilSender.hasPermission(argument.getPermission())) {
                    continue;
                }
            }
            String command = "\n" + "%primary%" + argument.getDisplay();
            stringBuilder.append(command);
        }

        return stringBuilder.toString();

    }

    public String getPermission() {
        return permission;
    }

}