package me.bubbles.bosspve.util.messages;

import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.settings.Settings;
import me.bubbles.bosspve.util.UtilDatabase;
import me.bubbles.bosspve.util.UtilUserData;
import me.bubbles.bosspve.util.string.UtilString;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UtilPreparedMessage {

    public static void sendMessage(Player player,
            MessageType type,
            String string) {
        UUID uuid = player.getUniqueId();
        if(type.equals(MessageType.KILL_MESSAGE)) {
            if(UtilDatabase.SettingsDB().getValue(uuid, Settings.KILL_MESSAGES)==0) {
                return;
            }
        }
        if(type.equals(MessageType.ENCHANT_PROC)) {
            if(UtilDatabase.SettingsDB().getValue(uuid, Settings.PROCC_MESSAGES)==0) {
                return;
            }
        }
        player.sendMessage(UtilString.colorFillPlaceholders(string));
    }

    public static void onProc(Player player, Enchant enchant) {
        sendMessage(player, MessageType.ENCHANT_PROC, "%prefix% %secondary%"+enchant.getName()+" %primary%has activated!");
    }

    public static void kill(Player player, IEntity entity, int xp, int money) {
        boolean levelUp = false;
        UtilUserData uud = UtilUserData.getUtilUserData(player.getUniqueId());
        int level=uud.getLevel();
        if(xp>0) {
            uud.setXp(uud.getXp()+xp);
            levelUp=uud.getLevel()!=level;
        }
        if(xp>0&&money>0) {
            if(levelUp) {
                sendMessage(player, MessageType.KILL_MESSAGE, "%prefix% %primary%You killed a %secondary%"+entity.getUncoloredName()+"%primary% and gained %secondary%$"+money+
                        " %primary%and leveled up from %secondary%"+level+"%primary% to %secondary%"+uud.getLevel()+"%primary%.");
            } else {
                sendMessage(player, MessageType.KILL_MESSAGE, "%prefix% %primary%You killed a %secondary%"+entity.getUncoloredName()+"%primary% and gained %secondary%$"+money+"%primary% and %secondary%"+xp+"%primary% XP.");
            }
        } else if (money>0) {
            sendMessage(player, MessageType.KILL_MESSAGE, "%prefix% %primary%You killed a %secondary%"+entity.getUncoloredName()+"%primary% and gained %secondary%$"+money+"%primary%.");
        } else if (xp>0) {
            if(levelUp) {
                sendMessage(player, MessageType.KILL_MESSAGE, "%prefix% %primary%You killed a %secondary%"+entity.getUncoloredName()+"%primary% and leveled up from %secondary%"+level+"%primary% to %secondary%"+uud.getLevel()+"%primary%.");
            } else {
                sendMessage(player, MessageType.KILL_MESSAGE, "%prefix% %primary%You killed a %secondary%"+entity.getUncoloredName()+"%primary% and gained %secondary%"+xp+"%primary% XP.");
            }
        } else {
            sendMessage(player, MessageType.KILL_MESSAGE, "%prefix% %primary%You killed a %secondary%"+entity.getUncoloredName()+".");
        }
    }

    public static void give(Player player, int xp, int money) {
        String msg;
        boolean levelUp = false;
        UtilUserData uud = UtilUserData.getUtilUserData(player.getUniqueId());
        int level=uud.getLevel();
        if(xp>0) {
            uud.setXp(uud.getXp()+xp);
            levelUp=uud.getLevel()!=level;
        }
        if(xp>0&&money>0) {
            if(levelUp) {
                sendMessage(player, MessageType.KILL_MESSAGE, "%prefix% %primary%You gained %secondary%$"+money+
                        " %primary%and leveled up from %secondary%"+level+"%primary% to %secondary%"+uud.getLevel()+"%primary%.");
            } else {
                sendMessage(player, MessageType.KILL_MESSAGE, "%prefix% %primary%You gained %secondary%$"+money+"%primary% and %secondary%"+xp+"%primary% XP.");
            }
        } else if (xp>0) {
            sendMessage(player, MessageType.KILL_MESSAGE, "%prefix% %primary%You gained %secondary%"+xp+"%primary% XP.");
        } else if (money>0) {
            sendMessage(player, MessageType.KILL_MESSAGE, "%prefix% %primary%You gained %secondary%$"+money+"%primary%.");
        }
    }

}
