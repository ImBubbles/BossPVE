package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Command;
import org.bukkit.command.CommandSender;

public class BaseCommand extends Command {
    private final int index=0;

    public BaseCommand() {
        super("bosspve");
        addArguments(
                new StageArg(index),
                new LevelArg(index),
                new XpArg(index),
                new GiveXpArg(index),
                new BalanceArg(index),
                new ItemArg(index),
                new SummonArg(index),
                new AddSpawnArg(index),
                new ReloadArg(index),
                new StagesArg(index),
                new SettingsArg(index),
                new ItemsArg(index),
                new DebugArg(index),
                new EnchantsArg(index),
                new MobsArg(index)
        );
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        super.run(sender,args);
    }

}
