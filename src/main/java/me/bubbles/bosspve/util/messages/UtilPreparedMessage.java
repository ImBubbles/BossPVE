package me.bubbles.bosspve.util.messages;

import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.settings.Settings;
import me.bubbles.bosspve.util.UtilDatabase;
import me.bubbles.bosspve.util.UtilUserData;
import me.bubbles.bosspve.util.string.UtilString;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class UtilPreparedMessage {

    public static void sendMessage(GamePlayer gamePlayer,
            MessageType type,
            String string) {
        HashMap<String, Integer> settings = gamePlayer.getCache().getSettings();
        if(type.equals(MessageType.KILL_MESSAGE)) {
            if(settings.get(Settings.KILL_MESSAGES.toString())==0) {
                return;
            }
        }
        if(type.equals(MessageType.ENCHANT_PROC)) {
            if(settings.get(Settings.PROCC_MESSAGES.toString())==0) {
                return;
            }
        }
        gamePlayer.getBukkitPlayer().sendMessage(UtilString.colorFillPlaceholders(string));
    }

    public static void onProc(GamePlayer gamePlayer, Enchant enchant) {
        sendMessage(gamePlayer, MessageType.ENCHANT_PROC, "%prefix% %secondary%"+enchant.getName()+" %primary%has activated!");
    }

    public static void kill(GamePlayer gamePlayer, IEntity entity, int xp, double money) {
        boolean levelUp = false;
        UtilUserData uud = gamePlayer.getCache();
        int level=uud.getLevel();
        if(xp>0) {
            uud.setXp(uud.getXp()+xp);
            levelUp=uud.getLevel()!=level;
        }
        if(xp>0&&money>0) {
            if(levelUp) {
                sendMessage(gamePlayer, MessageType.KILL_MESSAGE, "%prefix% %primary%You killed a %secondary%"+entity.getUncoloredName()+"%primary% and gained %secondary%$"+money+
                        " %primary%and leveled up from %secondary%"+level+"%primary% to %secondary%"+uud.getLevel()+"%primary%.");
            } else {
                sendMessage(gamePlayer, MessageType.KILL_MESSAGE, "%prefix% %primary%You killed a %secondary%"+entity.getUncoloredName()+"%primary% and gained %secondary%$"+money+"%primary% and %secondary%"+xp+"%primary% XP.");
            }
        } else if (money>0) {
            sendMessage(gamePlayer, MessageType.KILL_MESSAGE, "%prefix% %primary%You killed a %secondary%"+entity.getUncoloredName()+"%primary% and gained %secondary%$"+money+"%primary%.");
        } else if (xp>0) {
            if(levelUp) {
                sendMessage(gamePlayer, MessageType.KILL_MESSAGE, "%prefix% %primary%You killed a %secondary%"+entity.getUncoloredName()+"%primary% and leveled up from %secondary%"+level+"%primary% to %secondary%"+uud.getLevel()+"%primary%.");
            } else {
                sendMessage(gamePlayer, MessageType.KILL_MESSAGE, "%prefix% %primary%You killed a %secondary%"+entity.getUncoloredName()+"%primary% and gained %secondary%"+xp+"%primary% XP.");
            }
        } else {
            sendMessage(gamePlayer, MessageType.KILL_MESSAGE, "%prefix% %primary%You killed a %secondary%"+entity.getUncoloredName()+".");
        }
    }

    public static void give(GamePlayer gamePlayer, int xp, double money) {
        String msg;
        boolean levelUp = false;
        UtilUserData uud = gamePlayer.getCache();
        int level=uud.getLevel();
        if(xp>0) {
            uud.setXp(uud.getXp()+xp);
            levelUp=uud.getLevel()!=level;
        }
        if(xp>0&&money>0) {
            if(levelUp) {
                sendMessage(gamePlayer, MessageType.KILL_MESSAGE, "%prefix% %primary%You gained %secondary%$"+money+
                        " %primary%and leveled up from %secondary%"+level+"%primary% to %secondary%"+uud.getLevel()+"%primary%.");
            } else {
                sendMessage(gamePlayer, MessageType.KILL_MESSAGE, "%prefix% %primary%You gained %secondary%$"+money+"%primary% and %secondary%"+xp+"%primary% XP.");
            }
        } else if (xp>0) {
            sendMessage(gamePlayer, MessageType.KILL_MESSAGE, "%prefix% %primary%You gained %secondary%"+xp+"%primary% XP.");
        } else if (money>0) {
            sendMessage(gamePlayer, MessageType.KILL_MESSAGE, "%prefix% %primary%You gained %secondary%$"+money+"%primary%.");
        }
    }

}
