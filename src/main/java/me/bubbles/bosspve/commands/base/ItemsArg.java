package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.events.presets.GuiClickCommand;
import me.bubbles.bosspve.events.presets.GuiClickIndex;
import me.bubbles.bosspve.events.presets.GuiClickRunnable;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.utility.UtilUserData;
import me.bubbles.bosspve.utility.guis.GUI;
import me.bubbles.bosspve.utility.guis.command.ClickGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemsArg extends Argument {

    public ItemsArg(BossPVE plugin, int index) {
        super(plugin, "items", "items", index);
        setPermission("items");
        setAlias("items");
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

        utilSender.getPlayer().closeInventory();
        utilSender.getPlayer().openInventory(generateGUI(page));

    }

    private Inventory generateGUI(int pageNum) {

        ClickGUI<Item> gui = new ClickGUI<Item>(plugin, utilSender.getPlayer(), Item.class, noEnchants(), pageNum, false) {
            @Override
            public ItemStack getItemStack(Item object) {
                return object.nmsAsItemStack();
            }

            @Override
            public GuiClickIndex getGuiClick(Item object, int index) {

                Runnable runnable = () -> {
                    if(!utilSender.hasPermission("bosspve.admin")) {
                        return;
                    }
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bpve giveitem "+utilSender.getPlayer().getName()+" "+object.getNBTIdentifier());
                };

                return new GuiClickRunnable(plugin, inventory, index, runnable);
                //return new GuiClickIndex(plugin, inventory, index, false);
            }

            @Override
            public ItemStack getBackItemStack() {
                ItemStack backButton = new ItemStack(Material.ARROW);
                ItemMeta itemMeta = backButton.getItemMeta();
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                        "&fPage " + (page)
                ));
                backButton.setItemMeta(itemMeta);
                return backButton;
            }

            @Override
            public ItemStack getForwardItemStack() {
                ItemStack nextButton = new ItemStack(Material.FEATHER);
                ItemMeta itemMeta = nextButton.getItemMeta();
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                        "&fPage " + (page+2)
                ));
                nextButton.setItemMeta(itemMeta);
                return nextButton;
            }

            @Override
            public GuiClickIndex getBackCommand(int index) {
                return new GuiClickCommand(plugin, inventory, index, "items "+(page-1), utilSender.getPlayer());
            }

            @Override
            public GuiClickIndex getForwardCommand(int index) {
                return new GuiClickCommand(plugin, inventory, index, "items "+(page+1), utilSender.getPlayer());
            }

            @Override
            public ItemStack getBottomItemStack() {
                return new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            }

            @Override
            public ItemStack getBackground() {
                return new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            }

            @Override
            public String getTitle() {
                return "Items ("+(page+1)+"/"+getGridifier().getTotalPages();
            }

        };
        return gui.build();
    }

    private Item[] noEnchants() {
        List<Item> items = new ArrayList<>(plugin.getItemManager().getItems());
        return items.stream().filter(item -> {
            return (!(item.getType().equals(Item.Type.ENCHANT)));
        }).toArray(Item[]::new);
    }

}