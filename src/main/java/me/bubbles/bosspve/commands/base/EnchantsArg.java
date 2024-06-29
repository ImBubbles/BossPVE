package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.events.presets.GuiClick;
import me.bubbles.bosspve.events.presets.GuiClickCommand;
import me.bubbles.bosspve.events.presets.GuiClickIndex;
import me.bubbles.bosspve.events.presets.GuiClickRunnable;
import me.bubbles.bosspve.flags.Flag;
import me.bubbles.bosspve.flags.ItemFlag;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.items.manager.bases.enchants.EnchantItem;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.utility.gui.command.ClickGUI;
import me.bubbles.bosspve.utility.string.UtilString;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class EnchantsArg extends Argument {

    //private HashMap<UUID, Enchant> map;

    public EnchantsArg(BossPVE plugin, int index) {
        super(plugin, "enchants", "enchants", index);
        setPermission("enchants");
        setAlias("enchants");
        //map=new HashMap<>();
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
        utilSender.getPlayer().openInventory(enchantGUI(page));
    }

    private Inventory enchantGUI(int pageNum) {

        ClickGUI<Item> gui = new ClickGUI<Item>(plugin, utilSender.getPlayer(), 5, Item.class, onlyEnchants(), pageNum) {
            @Override
            public ItemStack getItemStack(Item object) {

                ItemStack itemStack = object.nmsAsItemStack();
                EnchantItem enchantItem = (EnchantItem) object;

                Enchant enchant = enchantItem.getEnchant();

                List<String> lore = new ArrayList<>();

                lore.add(UtilString.colorFillPlaceholders("&7"+enchant.getDescription()));

                lore.add(UtilString.colorFillPlaceholders(""));

                lore.add(UtilString.colorFillPlaceholders("&7Max Level: &9"+enchant.getMaxLevel()));
                if(enchant.getLevelRequirement()!=-1) {
                    lore.add(UtilString.colorFillPlaceholders("&7 Level Requirement: &9"+enchant.getLevelRequirement()));
                }

                HashSet<Flag<ItemFlag, Double>> flags = enchant.getFlags(1);
                if(flags!=null) {
                    if(!flags.isEmpty()) {
                        lore.add(UtilString.colorFillPlaceholders(""));
                        lore.add(UtilString.colorFillPlaceholders("&7&lEffects: "));
                        for (Flag<ItemFlag, Double> flag : flags) {
                            lore.add(UtilString.colorFillPlaceholders("&7"+flag.getFlag().name()+": &9"+flag.getValue()));
                        }
                    }
                }

                ItemMeta itemMeta = (itemStack.hasItemMeta()) ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);

                return itemStack;
            }

            @Override
            public GuiClickIndex getGuiClick(Item object, int index) {

                Enchant enchant = ((EnchantItem) object).getEnchant();

                Runnable runnable = () -> {
                    if(!utilSender.hasPermission("bosspve.admin")) {
                        return;
                    }
                    utilSender.getPlayer().closeInventory();
                    utilSender.getPlayer().openInventory(levelGUI(0, enchant));
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
            public GuiClickIndex getBackClick(int index) {
                return new GuiClickCommand(plugin, inventory, index, "enchants "+(page-1), utilSender.getPlayer());
            }

            @Override
            public GuiClickIndex getForwardClick(int index) {
                return new GuiClickCommand(plugin, inventory, index, "enchants "+(page+1), utilSender.getPlayer());
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
                return "Enchants ("+(page+1)+"/"+getGridifier().getTotalPages();
            }

        };
        return gui.build();
    }

    private Item[] onlyEnchants() {
        List<Item> items = new ArrayList<>(plugin.getItemManager().getItems());
        return items.stream().filter(item -> {
            return (item instanceof EnchantItem);
        }).toArray(Item[]::new);
    }

    private Inventory levelGUI(int pageNum, Enchant enchant) {
        ClickGUI<Integer> gui = new ClickGUI<Integer>(plugin, utilSender.getPlayer(), 3, Integer.class, getLevels(enchant), pageNum) {


            @Override
            public ItemStack getItemStack(Integer object) {

                EnchantItem enchantItem = enchant.getEnchantItem();
                ItemStack result = enchantItem.nmsAsItemStack();
                result.setAmount(object);

                return result;

            }

            @Override
            public GuiClickIndex getGuiClick(Integer object, int index) {

                Runnable runnable = () -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bpve giveitem "+utilSender.getPlayer().getName()+" "+enchant.getKey()+"Ench "+object);
                };

                return new GuiClickRunnable(plugin, inventory, index, runnable);

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
            public GuiClickIndex getBackClick(int index) {

                Runnable runnable = () -> {
                    utilSender.getPlayer().closeInventory();
                    utilSender.getPlayer().openInventory(levelGUI(page-1, enchant));
                };

                return new GuiClickRunnable(plugin, inventory, index, runnable);

            }

            @Override
            public GuiClickIndex getForwardClick(int index) {

                Runnable runnable = () -> {
                    utilSender.getPlayer().closeInventory();
                    utilSender.getPlayer().openInventory(levelGUI(page+1, enchant));
                };

                return new GuiClickRunnable(plugin, inventory, index, runnable);

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
                return "Level ("+(page+1)+"/"+getGridifier().getTotalPages();
            }

            @Override
            public Inventory build() {

                ItemStack backButton = new ItemStack(Material.COMPASS);
                ItemMeta itemMeta = backButton.getItemMeta();
                itemMeta.setDisplayName(UtilString.colorFillPlaceholders("&7&lBack"));
                backButton.setItemMeta(itemMeta);

                Runnable runnable = () -> {
                    utilSender.getPlayer().closeInventory();
                    utilSender.getPlayer().openInventory(enchantGUI(0));
                };

                set(22, backButton, new GuiClickRunnable(plugin, inventory, 22, runnable));

                return super.build();

            }

        };
        return gui.build();
    }

    private Integer[] getLevels(Enchant enchant) {
        /*List<Item> items = new ArrayList<>(plugin.getItemManager().getItems());
        return items.stream().filter(item -> {
            return (item.getType().equals(Item.Type.ENCHANT));
        }).toArray(Item[]::new);*/
        Integer[] result = new Integer[enchant.getMaxLevel()];
        for(int i=1; i<=enchant.getMaxLevel(); i++) {
            result[i-1]=i;
        }
        return result;
    }

}
