package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.events.presets.GuiClickCommand;
import me.bubbles.bosspve.events.presets.GuiClickIndex;
import me.bubbles.bosspve.events.presets.GuiClickRunnable;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.utility.UtilItemStack;
import me.bubbles.bosspve.utility.chance.Drop;
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
import java.util.List;

public class MobsArg extends Argument {

    public MobsArg(int index) {
        super("mobs", index);
        setPermission("mobs");
        setAlias("mobs");
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
        utilSender.getPlayer().openInventory(generateGUI(utilSender.getPlayer(), page));

    }

    private Inventory generateGUI(Player player, int pageNum) {

        ClickGUI<IEntity> gui = new ClickGUI<IEntity>(player, 4, IEntity.class, getMobs(), pageNum) {
            @Override
            public ItemStack getItemStack(IEntity object) {

                ItemStack result = new ItemStack(object.getShowMaterial());

                ItemMeta itemMeta = result.hasItemMeta() ? result.getItemMeta() : Bukkit.getItemFactory().getItemMeta(object.getShowMaterial());
                itemMeta.setDisplayName(object.getShowName());
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(UtilString.colorFillPlaceholders("&e&oClick me for drops"));
                itemMeta.setLore(lore);
                result.setItemMeta(itemMeta);

                return result;

            }

            @Override
            public GuiClickIndex getGuiClick(IEntity object, int index) {

                Runnable runnable = () -> {
                    //UtilItemStack.giveItem(utilSender.getPlayer(), object.nmsAsItemStack());
                    player.closeInventory();
                    player.openInventory(getDrops(player, object, 0));
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
                return new GuiClickCommand(inventory, index, "mobs "+(page-1), player);
            }

            @Override
            public GuiClickIndex getForwardClick(int index) {
                return new GuiClickCommand(inventory, index, "mobs "+(page+1), player);
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
                return "Mobs ("+(page+1)+"/"+getGridifier().getTotalPages();
            }

        };
        return gui.build();
    }

    private IEntity[] getMobs() {
        List<IEntity> mobs = new ArrayList<>(BossPVE.getInstance().getEntityManager().getEntities());
        return mobs.toArray(new IEntity[0]);
    }

    private double roundNumber(double num) {
        return (double) Math.round(num * 10.0) / 10;
    }

    private Inventory getDrops(Player player, IEntity iEntity, int page) {
        ClickGUI<Drop> clickGUI = new ClickGUI<Drop>(player, 2, Drop.class, dropsAsArray(iEntity), page) {

            @Override
            public ItemStack getItemStack(Drop object) {

                ItemStack result = object.getPossible();
                ItemMeta itemMeta = result.hasItemMeta() ? result.getItemMeta() : Bukkit.getItemFactory().getItemMeta(result.getType());
                List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
                lore.add("");
                lore.add(UtilString.colorFillPlaceholders("%primary%Chance: %secondary%"+roundNumber(object.getPercentChance()))+"%");
                itemMeta.setLore(lore);
                result.setItemMeta(itemMeta);

                return result;
            }

            @Override
            public GuiClickIndex getGuiClick(Drop object, int index) {
                return new GuiClickIndex(inventory, index, false);
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
                return new GuiClickRunnable(inventory, index, () -> {
                    getDrops(player, iEntity, page-1);
                });
            }

            @Override
            public GuiClickIndex getForwardClick(int index) {
                return new GuiClickRunnable(inventory, index, () -> {
                    getDrops(player, iEntity, page+1);
                });
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
                return "Drops ("+(page+1)+"/"+getGridifier().getTotalPages();
            }

            @Override
            public Inventory build() {

                ItemStack backButton = new ItemStack(Material.COMPASS);
                ItemMeta itemMeta = backButton.getItemMeta();
                itemMeta.setDisplayName(UtilString.colorFillPlaceholders("&7&lBack"));
                backButton.setItemMeta(itemMeta);

                Runnable runnable = () -> {
                    player.closeInventory();
                    player.openInventory(generateGUI(player, 0));
                };

                set(13, backButton, new GuiClickRunnable(inventory, 13, runnable));

                return super.build();

            }

        };
        return clickGUI.build();
    }

    private Drop[] dropsAsArray(IEntity iEntity) {
        return iEntity.getDrops().toArray(new Drop[0]);
    }

}