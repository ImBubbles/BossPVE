package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.events.presets.GuiClickCommand;
import me.bubbles.bosspve.stages.Stage;
import me.bubbles.bosspve.utility.UtilNumber;
import me.bubbles.bosspve.utility.pagifier.Gridifier;
import me.bubbles.bosspve.utility.string.UtilString;
import org.bukkit.Bukkit;
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
        Integer[] stages = getAllStages();
        Gridifier<Integer> grid = new Gridifier<Integer>(Integer.class, stages, 2, 9);

        int level = plugin.getGameManager().getGamePlayer(utilSender.getPlayer().getUniqueId()).getCache().getLevel();

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

        utilSender.getPlayer().openInventory(generateGUI(grid, stages, level, page));

    }

    private Inventory generateGUI(Gridifier<Integer> gridifier, Integer[] stages, int level, int pageNum) {

        Inventory page = Bukkit.createInventory(null, 27, "Stages ("+(pageNum+1)+"/"+gridifier.getTotalPages()+")");
        ArrayList<Integer> listed = new ArrayList<>();
        for(int f=0; f<17; f++) {

            // STAGE BUTTON

            if(stages.length<((18*pageNum+f)+1)) {
                break;
            }

            int stageNum = stages[(18*pageNum+f)];
            ItemStack stageButton = new ItemStack(level>=stageNum? Material.WHITE_STAINED_GLASS : Material.RED_STAINED_GLASS);
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
            page.setItem(f, stageButton);

            listed.add(stageNum);
        }

        for(int stageNum : listed) {
            plugin.getEventManager().addEvent(new GuiClickCommand(plugin, page, listed.indexOf(stageNum), "stage "+stageNum, utilSender.getPlayer()));
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
            plugin.getEventManager().addEvent(new GuiClickCommand(plugin, page, 18, "stages "+(pageNum-1), utilSender.getPlayer()));
        }

        if(pageNum<gridifier.getTotalPages()-1) {
            ItemStack nextButton = new ItemStack(Material.FEATHER);
            ItemMeta itemMeta = nextButton.getItemMeta();
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                    "&fPage " + (pageNum+1)
            ));
            nextButton.setItemMeta(itemMeta);
            page.setItem(26, nextButton);
            plugin.getEventManager().addEvent(new GuiClickCommand(plugin, page, 26, "stages "+(pageNum+1), utilSender.getPlayer()));
        }

        return page;

    }

    private Integer[] getAllStages() {
        List<Integer> stages = new ArrayList<>();
        plugin.getStageManager().getStages().forEach(stage -> {
            stages.add(stage.getLevelRequirement());
        });
        Collections.sort(stages);
        Integer[] result = new Integer[stages.size()];
        for(int i=0; i<stages.size(); i++) {
            result[i]=stages.get(i);
        }
        return result;
    }


/*    private String getAvailableStages() {
        List<Integer> allowedStages = new ArrayList<>();
        plugin.getStageManager().getStages().stream()
                .forEach(stage -> allowedStages.add(stage.getLevelRequirement())
                );
        Collections.sort(allowedStages);
        StringBuilder stringBuilder = new StringBuilder();
        boolean first=true;
        for(Integer stage : allowedStages) {
            if(!first) {
                stringBuilder.append("%primary%, ");
            } else {
                first=false;
            }
            stringBuilder.append("%secondary%").append(stage);
        }
        return stringBuilder.toString();
    }*/

}