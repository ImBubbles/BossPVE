package me.bubbles.bosspve.commands.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.base.BaseCommand;
import me.bubbles.bosspve.commands.other.Spawn;

import java.util.ArrayList;
import java.util.HashSet;

public class CommandManager {

    private BossPVE plugin;
    private HashSet<Command> commands;

    public CommandManager(BossPVE plugin) {
        this.plugin=plugin;
        this.commands=new HashSet<>();
        registerCommands();
    }

    public void registerCommands() {
        addCommand(
                new BaseCommand(plugin),
                new Spawn(plugin)
        );
    }

    public void addCommand(Command... commands) {
        for(Command command : commands) {
            try {
                plugin.getCommand(command.getCommand()).setExecutor(command);
                plugin.getCommand(command.getCommand()).setTabCompleter(command);
                this.commands.add(command);
                if(!command.getArguments().isEmpty()) {
                    registerArguments(command.getArguments());
                }
            } catch (NullPointerException e) {
                plugin.getLogger().warning("Command /"+command.getCommand()+", could not be registered. Most likely due to improper plugin.yml");
            }
        }
    }

    public void registerArguments(ArrayList<Argument> arguments) {
        for(Argument argument : arguments) {
            if(argument.getAlias()!=null) {
                try {
                    plugin.getCommand(argument.getAlias()).setExecutor(argument);
                } catch (NullPointerException e) {
                    plugin.getLogger().warning("Command /"+argument.getAlias()+", could not be registered. Most likely due to improper plugin.yml");
                }
            }
            if(!argument.getArguments().isEmpty()) {
                registerArguments(argument.getArguments());
            }
        }
    }

    public HashSet<Command> getCommands() {
        return commands;
    }

}