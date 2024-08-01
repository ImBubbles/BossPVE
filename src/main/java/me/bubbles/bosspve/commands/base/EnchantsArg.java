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
import me.bubbles.bosspve.items.manager.bases.enchants.ProcEnchant;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.utility.UtilItemStack;
import me.bubbles.bosspve.utility.gui.command.ClickGUI;
import me.bubbles.bosspve.utility.string.UtilString;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class EnchantsArg extends Argument {

    //private HashMap<UUID, Enchant> map;

    public EnchantsArg(int index) {
        super("enchants", "enchants", index);
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
        utilSender.getPlayer().openInventory(enchantGUI(utilSender.getPlayer(), page));
    }

    private Inventory enchantGUI(Player player, int pageNum) {

        ClickGUI<Item> gui = new ClickGUI<Item>(player, 5, Item.class, onlyEnchants(), pageNum) {
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
                /*if(flags!=null) {
                    if(!flags.isEmpty()) {
                        lore.add(UtilString.colorFillPlaceholders(""));
                        lore.add(UtilString.colorFillPlaceholders("&7&lEffects: "));
                        for (Flag<ItemFlag, Double> flag : flags) {
                            lore.add(UtilString.colorFillPlaceholders("&7"+flag.getFlag().name()+": &9"+flag.getValue()));
                        }
                    }
                }*/

                if(enchant instanceof ProcEnchant) {
                    lore.add("");
                    lore.add(UtilString.colorFillPlaceholders("&e&oClick me to see chances"));
                }

                if(flags!=null) {
                    if(flags.isEmpty()) {
                        lore.add("");
                        lore.add(UtilString.colorFillPlaceholders("&e&oClick me to see effects"));
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
                    /*if(!utilSender.hasPermission("bosspve.admin")) {
                        return;
                    }*/
                    player.closeInventory();
                    player.openInventory(levelGUI(player, 0, enchant));
                };

                return new GuiClickRunnable(inventory, index, runnable);
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
                return new GuiClickCommand(inventory, index, "enchants "+(page-1), player);
            }

            @Override
            public GuiClickIndex getForwardClick(int index) {
                return new GuiClickCommand(inventory, index, "enchants "+(page+1), player);
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
        List<Item> items = new ArrayList<>(BossPVE.getInstance().getItemManager().getItems());
        return items.stream().filter(item -> {
            return (item instanceof EnchantItem);
        }).toArray(Item[]::new);
    }

    private Inventory levelGUI(Player player, int pageNum, Enchant enchant) {
        ClickGUI<Integer> gui = new ClickGUI<Integer>(player, 3, Integer.class, getLevels(enchant), pageNum) {


            @Override
            public ItemStack getItemStack(Integer object) {

                EnchantItem enchantItem = enchant.getEnchantItem();
                ItemStack result = enchantItem.nmsAsItemStack();
                ItemMeta itemMeta = result.getItemMeta();
                itemMeta.setDisplayName(itemMeta.getDisplayName()+" "+object);

                List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
                if(enchant instanceof ProcEnchant) {
                    if(lore.isEmpty()) {
                        lore.add("");
                    }
                    lore.add(UtilString.colorFillPlaceholders("%primary%Chance: %secondary%"+roundNumber(((ProcEnchant) enchant).getActivation(object).getPercentChance())+"%"));
                    itemMeta.setLore(lore);
                }

                HashSet<Flag<ItemFlag, Double>> flags = enchant.getFlags(1);
                if(flags!=null) {
                    if(!flags.isEmpty()) {
                        if(lore.isEmpty()) {
                            lore.add("");
                        }
                        lore.add(UtilString.colorFillPlaceholders(""));
                        lore.add(UtilString.colorFillPlaceholders("&7&lEffects: "));
                        for (Flag<ItemFlag, Double> flag : flags) {
                            lore.add(UtilString.colorFillPlaceholders("&7"+flag.getFlag().name()+": &9"+flag.getValue()));
                        }
                    }
                }

                result.setItemMeta(itemMeta);
                result.setAmount(object);

                return result;

            }

            @Override
            public GuiClickIndex getGuiClick(Integer object, int index) {

                Runnable runnable = () -> {
                    if(!player.hasPermission("bosspve.admin")) {
                        return;
                    }
                    UtilItemStack.giveItem(player, enchant.getEnchantItem().getAtLevel(object));
                    //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bpve giveitem "+utilSender.getPlayer().getName()+" "+enchant.getKey()+"Ench "+object);
                };

                return new GuiClickRunnable(inventory, index, runnable);

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
                    player.closeInventory();
                    player.openInventory(levelGUI(player, page-1, enchant));
                };

                return new GuiClickRunnable(inventory, index, runnable);

            }

            @Override
            public GuiClickIndex getForwardClick(int index) {

                Runnable runnable = () -> {
                    player.closeInventory();
                    player.openInventory(levelGUI(player, page+1, enchant));
                };

                return new GuiClickRunnable(inventory, index, runnable);

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
                    player.closeInventory();
                    player.openInventory(enchantGUI(player, 0));
                };

                set(22, backButton, new GuiClickRunnable(inventory, 22, runnable));

                return super.build();

            }

        };
        return gui.build();
    }

    private double roundNumber(double num) {
        return (double) Math.round(num * 10.0) / 10;
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
