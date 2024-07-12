package me.bubbles.bosspve.commands.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.base.BaseCommand;
import me.bubbles.bosspve.commands.other.Spawn;
import me.bubbles.bosspve.commands.other.Vote;

import java.util.ArrayList;
import java.util.HashSet;

public class CommandManager {
    private HashSet<Command> commands;

    public CommandManager() {
        this.commands=new HashSet<>();
        registerCommands();
    }

    public void registerCommands() {
        addCommand(
                new BaseCommand(),
                new Spawn(),
                new Vote()
        );
    }

    public void addCommand(Command... commands) {
        for(Command command : commands) {
            try {
                BossPVE.getInstance().getCommand(command.getCommand()).setExecutor(command);
                BossPVE.getInstance().getCommand(command.getCommand()).setTabCompleter(command);
                this.commands.add(command);
                if(!command.getArguments().isEmpty()) {
                    registerArguments(command.getArguments());
                }
            } catch (NullPointerException e) {
                BossPVE.getInstance().getLogger().warning("Command /"+command.getCommand()+", could not be registered. Most likely due to improper plugin.yml");
            }
        }
    }

    public void registerArguments(ArrayList<Argument> arguments) {
        for(Argument argument : arguments) {
            if(argument.getAlias()!=null) {
                try {
                    BossPVE.getInstance().getCommand(argument.getAlias()).setExecutor(argument);
                } catch (NullPointerException e) {
                    BossPVE.getInstance().getLogger().warning("Command /"+argument.getAlias()+", could not be registered. Most likely due to improper plugin.yml");
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