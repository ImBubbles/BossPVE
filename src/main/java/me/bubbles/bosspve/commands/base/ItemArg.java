package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import me.bubbles.bosspve.items.manager.bases.enchants.EnchantItem;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ItemArg extends Argument {

    public ItemArg(BossPVE plugin, int index) {
        super(plugin, "giveitem", "giveitem <player> <item> [level]", index);
        setPermission("admin");
    }

    @Override
    public void run(CommandSender sender, String[] args, boolean alias) {
        super.run(sender,args,alias);
        if(!permissionCheck()) {
            return;
        }
        if(args.length<=relativeIndex+1) { // DOESNT SEND ENOUGH ARGS
            utilSender.sendMessage(getItemsList());
            utilSender.sendMessage(getArgsMessage());
            return;
        }
        Player player = Bukkit.getPlayer(args[relativeIndex]);
        if(player==null) {
            utilSender.sendMessage("%prefix% %primary%Could not find player.");
            return;
        }
        Item item = plugin.getItemManager().getItemByName(args[relativeIndex+1]);
        if(item==null) {
            utilSender.sendMessage("%prefix% %primary%Item %secondary%"+args[relativeIndex+1]+"%primary% does not exist.");
            return;
        }
        ItemStack result = null;
        if(item instanceof EnchantItem) {
            EnchantItem enchantItem = (EnchantItem) item;
            if(args.length>=relativeIndex+2) {
                int level;
                try {
                    level=Integer.parseInt(args[relativeIndex+2]);
                } catch(NumberFormatException | IndexOutOfBoundsException e) {
                    level=-1;
                }
                if(enchantItem.getEnchant().getMaxLevel()>=level&&level>0) {
                    result=enchantItem.getAtLevel(level);
                }else{
                    result=enchantItem.nmsAsItemStack();
                }
            } else {
                result=enchantItem.nmsAsItemStack();
            }
        }
        if(result==null) {
            result=item.nmsAsItemStack();
        }
        PlayerInventory inventory = player.getInventory();
        if(inventory.firstEmpty()!=-1) {
            if(inventory.getItem(inventory.getHeldItemSlot())==null) {
                inventory.setItem(inventory.getHeldItemSlot(),result);
            }else{
                inventory.setItem(inventory.firstEmpty(),result);
            }
            player.getInventory().setContents(inventory.getContents());
        } else {
            player.getWorld().dropItem(player.getLocation(),result);
        }
        utilSender.sendMessage("%prefix% %primary%Item %secondary%"+item.getNBTIdentifier()+"%primary% has been given.");
    }

    private String getItemsList() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("%prefix% %primary%Items:");
        for(Item item : plugin.getItemManager().getItems()) {
            stringBuilder.append("\n").append("%primary%").append("- ").append("%secondary%").append(item.getNBTIdentifier());
        }
        return stringBuilder.toString();
    }

}