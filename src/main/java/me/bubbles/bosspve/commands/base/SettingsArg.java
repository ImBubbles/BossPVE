package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.database.databases.SettingsDB;
import me.bubbles.bosspve.events.presets.GuiClickCommand;
import me.bubbles.bosspve.events.presets.GuiClickIndex;
import me.bubbles.bosspve.events.presets.GuiClickRunnable;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.settings.Settings;
import me.bubbles.bosspve.utility.UtilDatabase;
import me.bubbles.bosspve.utility.UtilNumber;
import me.bubbles.bosspve.utility.UtilUserData;
import me.bubbles.bosspve.utility.guis.command.ClickGUI;
import me.bubbles.bosspve.utility.pagifier.Gridifier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

        utilSender.getPlayer().openInventory(generateGUI(page));

    }

    private Inventory generateGUI(int pageNum) {

        GamePlayer gamePlayer = plugin.getGameManager().getGamePlayer(utilSender.getPlayer().getUniqueId());
        UtilUserData uud = gamePlayer.getCache();

        ClickGUI<Settings> gui = new ClickGUI<Settings>(plugin, utilSender.getPlayer(), Settings.class, Settings.values(), pageNum, false) {
            @Override
            public ItemStack getItemStack(Settings object) {
                int index = SettingsDB.getValue(uud, object);
                ItemStack settingButton = new ItemStack(object.getMaterial(object.getOption(index)));
                ItemMeta itemMeta = settingButton.getItemMeta();
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                        "&f" + object.getDisplayName() + ": " + object.valueToString(object.getOption(index)))
                );
                settingButton.setItemMeta(itemMeta);
                return settingButton;
            }

            @Override
            public GuiClickIndex getGuiClick(Settings object, int index) {
                int i = SettingsDB.getValue(uud, object);
                Object next = object.getNext(object.getOption(i));
                Runnable runnable = () -> {
                    UtilDatabase.SettingsDB().setRelation(gamePlayer.getUuid(), object.toString(), object.getIndex(next));
                    gamePlayer.updateCache();
                    Player player = utilSender.getPlayer();
                    player.closeInventory();
                    Bukkit.getServer().dispatchCommand(player, "settings");
                };
                return new GuiClickRunnable(plugin, inventory, index, runnable, utilSender.getPlayer());
            }

            @Override
            public ItemStack getBackItemStack() {
                ItemStack backButton = new ItemStack(Material.ARROW);
                ItemMeta itemMeta = backButton.getItemMeta();
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                        "&fPage " + (page-1)
                ));
                backButton.setItemMeta(itemMeta);
                return backButton;
            }

            @Override
            public ItemStack getForwardItemStack() {
                ItemStack nextButton = new ItemStack(Material.FEATHER);
                ItemMeta itemMeta = nextButton.getItemMeta();
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                        "&fPage " + (page+1)
                ));
                nextButton.setItemMeta(itemMeta);
                return nextButton;
            }

            @Override
            public GuiClickIndex getBackCommand(int index) {
                return new GuiClickCommand(plugin, inventory, index, "settings "+(page-1), utilSender.getPlayer());
            }

            @Override
            public GuiClickIndex getForwardCommand(int index) {
                return new GuiClickCommand(plugin, inventory, index, "settings "+(page+1), utilSender.getPlayer());
            }

            @Override
            public ItemStack getBottomItemStack() {
                return new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            }

            @Override
            public ItemStack getBackground() {
                return new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            }
        };
        return gui.build();
    }

}