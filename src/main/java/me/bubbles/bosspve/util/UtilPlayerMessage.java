package me.bubbles.bosspve.util;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.util.string.UtilString;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class UtilPlayerMessage {

    private Player player;
    private BossPVE plugin;

    public UtilPlayerMessage(BossPVE plugin, Player player) {
        this.player=player;
        this.plugin=plugin;
    }

    public void sendMessage(
            MessageType type,
            String string) {
        if(type.equals(MessageType.KILL_MESSAGE)) {
            PersistentDataContainer data = player.getPersistentDataContainer();
            if(!data.get(new NamespacedKey(plugin,"mobKillMessages"), PersistentDataType.BOOLEAN)) {
                return;
            }
        }
        player.sendMessage(UtilString.colorFillPlaceholders(string));
    }

    public void onProc(Enchant enchant) {
        sendMessage(MessageType.ENCHANT_PROC, "%prefix% %secondary%"+enchant.getName()+" %primary%has activated!");
    }

}
