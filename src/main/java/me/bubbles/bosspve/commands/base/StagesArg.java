package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.events.presets.GuiClickCommand;
import me.bubbles.bosspve.events.presets.GuiClickIndex;
import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.stages.Stage;
import me.bubbles.bosspve.stages.StageManager;
import me.bubbles.bosspve.utility.UtilUserData;
import me.bubbles.bosspve.utility.gui.command.ClickGUI;
import me.bubbles.bosspve.utility.string.UtilString;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StagesArg extends Argument {

    public StagesArg(BossPVE plugin, int index) {
        super(plugin, "stages", "stages", index);
        setPermission("stages");
        setAlias("stages");
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

        ClickGUI<Stage> gui = new ClickGUI<Stage>(plugin, utilSender.getPlayer(), 3, Stage.class, getAllStages(), pageNum) {
            @Override
            public ItemStack getItemStack(Stage object) {
                int stageNum = object.getLevelRequirement();
                ItemStack stageButton = new ItemStack(uud.getLevel()>=stageNum? Material.WHITE_STAINED_GLASS : Material.RED_STAINED_GLASS);
                ItemMeta itemMeta = stageButton.getItemMeta();
                itemMeta.setDisplayName(UtilString.colorFillPlaceholders(
                        "%secondary%&lStage " + stageNum
                ));
                List<String> lore = new ArrayList<>();
                Stage stage = plugin.getStageManager().getStage(stageNum);
                lore.add(UtilString.colorFillPlaceholders(
                        "%primary%Money Multiplier: %secondary%"+stage.getMoneyMultiplier())+"x");
                lore.add(UtilString.colorFillPlaceholders(
                        "%primary%XP Multiplier: %secondary%"+stage.getXpMultiplier())+"x");
                lore.add(UtilString.colorFillPlaceholders(
                        "%primary%Monster Limit: %secondary%"+stage.getMaxEntities()));
                itemMeta.setLore(lore);
                stageButton.setItemMeta(itemMeta);
                return stageButton;
            }

            @Override
            public GuiClickIndex getGuiClick(Stage object, int index) {
                int stageNum = object.getLevelRequirement();
                return new GuiClickCommand(plugin, inventory, index, "stage "+stageNum, utilSender.getPlayer());
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
            public GuiClickIndex getBackClick(int index) {
                return new GuiClickCommand(plugin, inventory, index, "stages "+(page-1), utilSender.getPlayer());
            }

            @Override
            public GuiClickIndex getForwardClick(int index) {
                return new GuiClickCommand(plugin, inventory, index, "stages "+(page+1), utilSender.getPlayer());
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

    private Stage[] getAllStages() {
        StageManager stageManager = plugin.getStageManager();
        List<Integer> stages = new ArrayList<>();
        stageManager.getStages().forEach(stage -> {
            stages.add(stage.getLevelRequirement());
        });
        Collections.sort(stages);
        Stage[] result = new Stage[stages.size()];
        for(int i=0; i<stages.size(); i++) {
            result[i]=stageManager.getStage(stages.get(i));
        }
        return result;
    }

}