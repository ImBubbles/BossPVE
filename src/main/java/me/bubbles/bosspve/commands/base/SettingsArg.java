package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.database.databases.SettingsDB;
import me.bubbles.bosspve.events.presets.GuiClickCommand;
import me.bubbles.bosspve.events.presets.GuiClickRunnable;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.settings.Settings;
import me.bubbles.bosspve.util.UtilDatabase;
import me.bubbles.bosspve.util.UtilNumber;
import me.bubbles.bosspve.util.UtilUserData;
import me.bubbles.bosspve.util.pagifier.Gridifier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SettingsArg extends Argument {

    public SettingsArg(BossPVE plugin, int index) {
        super(plugin, "settings", "settings", index);
        setPermission("settings");
        setAlias("settings");
    }

    @Override
    public void run(CommandSender sender, String[] args, boolean alias) {
        super.run(sender, args, alias);
        if(!permissionCheck()) {
            return;
        }
        Settings[] settings = Settings.values();
        Gridifier<Settings> grid = new Gridifier<Settings>(Settings.class, settings, 2, 9);

        int page;

        if(args.length!=relativeIndex) {
            try {
                page = Integer.parseInt(args[relativeIndex]);
            } catch (NumberFormatException e) {
                page = 0;
            }
        } else {
            page = 0;
        }

        page = (int) UtilNumber.clampBorder(grid.getTotalPages()-1, 0, page);

        utilSender.getPlayer().openInventory(generateGUI(grid, page));

    }

    private Inventory generateGUI(Gridifier<Settings> gridifier, int pageNum) {

        Inventory page = Bukkit.createInventory(null, 27, "Settings ("+(pageNum+1)+"/"+gridifier.getTotalPages()+")");
        ArrayList<Settings> listed = new ArrayList<>();

        GamePlayer gamePlayer = plugin.getGameManager().getGamePlayer(utilSender.getPlayer().getUniqueId());
        UtilUserData uud = gamePlayer.getCache();

        for(int f=0; f<17; f++) {

            // SETTING BUTTON

            if(Settings.values().length<((18*pageNum+f)+1)) {
                break;
            }

            Settings setting = Settings.values()[(18*pageNum+f)];
            boolean isBool = setting.getMax()==1&&setting.getMin()==0;
            ItemStack settingButton;
            int value = SettingsDB.getValue(uud, setting);
            if(isBool) {
                settingButton = new ItemStack(value==1 ? Material.LIME_STAINED_GLASS : Material.RED_STAINED_GLASS);
            } else {
                settingButton = new ItemStack(setting.getMaterial());
            }
            ItemMeta itemMeta = settingButton.getItemMeta();
            if(isBool) {
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                        "&f" + setting.getDisplayName() + ": " + (value==1)
                ));
            } else {
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                        "&f" + setting.getDisplayName() + ": " + value
                ));
            }
            settingButton.setItemMeta(itemMeta);
            page.setItem(f, settingButton);

            listed.add(setting);
        }

        for(Settings setting : listed) {
            int value = SettingsDB.getValue(uud, setting);
            int newValue = (int) UtilNumber.clampLoop(setting.getMax(), setting.getMin(), value+1);
            Runnable runnable = () -> {
                UtilDatabase.SettingsDB().setRelation(gamePlayer.getUuid(), setting.toString(), newValue);
                gamePlayer.updateCache();
                Player player = utilSender.getPlayer();
                player.closeInventory();
                Bukkit.getServer().dispatchCommand(player, "settings");
            };
            plugin.getEventManager().addEvent(new GuiClickRunnable(plugin, page, listed.indexOf(setting), runnable, utilSender.getPlayer()));
        }

        // BACK & NEXT BUTTON

        if(pageNum>0) {
            ItemStack backButton = new ItemStack(Material.ARROW);
            ItemMeta itemMeta = backButton.getItemMeta();
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                    "&fPage " + (pageNum-1)
            ));
            backButton.setItemMeta(itemMeta);
            page.setItem(18, backButton);
            plugin.getEventManager().addEvent(new GuiClickCommand(plugin, page, 18, "settings "+(pageNum-1), utilSender.getPlayer()));
        }

        if(pageNum<gridifier.getTotalPages()-1) {
            ItemStack nextButton = new ItemStack(Material.FEATHER);
            ItemMeta itemMeta = nextButton.getItemMeta();
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                    "&fPage " + (pageNum+1)
            ));
            nextButton.setItemMeta(itemMeta);
            page.setItem(26, nextButton);
            plugin.getEventManager().addEvent(new GuiClickCommand(plugin, page, 26, "settings "+(pageNum+1), utilSender.getPlayer()));
        }

        return page;

    }

}