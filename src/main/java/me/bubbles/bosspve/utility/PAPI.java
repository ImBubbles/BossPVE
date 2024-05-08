package me.bubbles.bosspve.utility;

import me.bubbles.bosspve.BossPVE;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class PAPI extends PlaceholderExpansion {

    private BossPVE plugin;

    public PAPI(BossPVE plugin) {
        this.plugin=plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "BossPVE";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Bubbles";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        UtilUserData uud = plugin.getGameManager().getGamePlayer(player.getUniqueId()).getCache();
        if(params.equalsIgnoreCase("xp")){
            return String.valueOf(uud.getXp());
        }
        if(params.equalsIgnoreCase("level")) {
            return String.valueOf(uud.getLevel());
        }
        if(params.startsWith("player_position_")) {
            int pos;
            try {
                pos = Integer.parseInt(params.split("_")[2]);
            } catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {
                return "No One";
            }
            ArrayList<UUID> list = UtilDatabase.getXpDB().sortByXP();
            if(list.size()<pos) {
                return "No One";
            }
            OfflinePlayer posPlayer = Bukkit.getOfflinePlayer(list.get(pos-1));
            return posPlayer.getName();
        }
        if(params.startsWith("xp_position_")) {
            int pos;
            try {
                pos = Integer.parseInt(params.split("_")[2]);
            } catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {
                return "0";
            }
            ArrayList<UUID> list = UtilDatabase.getXpDB().sortByXP();
            if(list.size()<pos) {
                return "0";
            }
            OfflinePlayer xpPlayer = Bukkit.getOfflinePlayer(list.get(pos-1));
            return String.valueOf(UtilDatabase.getXpDB().getValue(xpPlayer.getUniqueId()));
        }
        if(params.equalsIgnoreCase("position")) {
            return String.valueOf(UtilDatabase.getXpDB().getPosition(player.getUniqueId()));
        }
        return null; // Placeholder is unknown by the Expansion
    }

}
