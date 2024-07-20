package me.bubbles.bosspve.items.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.enchants.*;
import me.bubbles.bosspve.items.manager.bases.enchants.CooldownEnchant;
import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.utility.UtilEnchant;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

public class EnchantManager {

    private HashSet<Enchant> enchants;
    private static boolean REGISTERED = false;

    public EnchantManager(ItemManager itemManager) {
        this.enchants=new HashSet<>();
    }

    public void registerEnchants() {
        if(REGISTERED) {
            return;
        }
        UtilEnchant.unfreezeRegistry();
        addEnchant(
                new Speed(),
                new Telepathy(),
                new Resistance(),
                new Damager(),
                new KeyFinder(),
                new Banker(),
                new Grinder(),
                new Nuker(),
                new Bloodsucker(),
                new Essence(),
                new Soulful(),
                new Merchant(),
                new Sharp()
        );
        UtilEnchant.freezeRegistry();
        registerEnchantItems();
        REGISTERED=true;
    }

    private void addEnchant(Enchant... enchants) {
        Arrays.stream(enchants).forEach(enchant -> {
            this.enchants.add(enchant);
            //this.plugin.getItemManager().registerItem(enchant.getEnchantItem());
        });
    }

    private void registerEnchantItems() {
        enchants.forEach(enchant -> {
            BossPVE.getInstance().getItemManager().registerItem(enchant.getEnchantItem());
        });
    }

    public void onTick() {
        enchants.stream().filter(enchant -> enchant instanceof CooldownEnchant).forEach(enchant -> ((CooldownEnchant) enchant).onTick());
        //enchants.forEach(Enchant::onTick);
    }

    public void onEvent(Event event) {
        enchants.forEach(enchant -> enchant.onEvent(event));
    }

    public Enchant asCustomEnchant(Enchantment enchantment) {
        return fromNamespacedKey(enchantment.getKey());
    }

    public Enchant getEnchant(String name) {
        Optional<Enchant> result = enchants.stream().filter(enchant -> enchant.getName().equalsIgnoreCase(name)).findFirst();
        return result.orElse(null);
    }

    public Enchant fromNamespacedKey(NamespacedKey key) {
        Optional<Enchant> result = enchants.stream().filter(enchant -> enchant.getNamespacedKey().equals(key)).findFirst();
        return result.orElse(null);
    }

}
