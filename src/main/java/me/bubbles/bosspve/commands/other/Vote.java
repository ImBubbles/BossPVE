package me.bubbles.bosspve.commands.other;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vote extends Command {

    public Vote() {
        super("vote");
        setPermission("vote");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        super.run(sender, args);
        List<String> sites = Arrays.asList(
                "https://minecraftservers.org/vote/663644",
                "https://minecraft-mp.com/server/331983/vote/",
                "https://minecraft-server-list.com/server/497273/vote/",
                "https://www.planetminecraft.com/server/bubblesmc-6272457/vote/",
                "https://minecraft-server.net/vote/BubblesMC/",
                "https://minecraft-serverlist.com/server/1370"
        );

        StringBuilder result= new StringBuilder("&8[%secondary%&lVote&8]");

        for(String name : sites) {
            result.append("\n%primary%- %secondary%").append(name);
        }

        utilSender.sendMessage(result.toString());

    }

}
