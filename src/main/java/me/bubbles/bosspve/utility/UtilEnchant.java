package me.bubbles.bosspve.utility;

import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.bukkit.NamespacedKey;

import java.util.IdentityHashMap;

public class UtilEnchant {

    public static void unfreezeRegistry() {
        //Class<?> clazz = ((Object) BuiltInRegistries.ENCHANTMENT).getClass();
        /*MappedRegistry<Enchantment> mappedRegistry = (MappedRegistry<Enchantment>) BuiltInRegistries.ENCHANTMENT;
        try {
            Field frozen = mappedRegistry.getClass().getField("frozen");
            frozen.setAccessible(true);
            frozen.set(BuiltInRegistries.ENCHANTMENT, false);
            Field unregisteredIntrusiveHolders = mappedRegistry.getClass().getField("unregisteredIntrusiveHolders");
            unregisteredIntrusiveHolders.setAccessible(true);
            unregisteredIntrusiveHolders.set(BuiltInRegistries.ENCHANTMENT, new IdentityHashMap<>());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }*/
        //Reflex.setFieldValue(BuiltInRegistries.ENCHANTMENT, "frozen", false);
        //Reflex.setFieldValue(BuiltInRegistries.ENCHANTMENT, "unregisteredIntrusiveHolders", new IdentityHashMap<>());
        Reflex.setFieldValue(BuiltInRegistries.ENCHANTMENT, "l", false);
        Reflex.setFieldValue(BuiltInRegistries.ENCHANTMENT, "m", new IdentityHashMap<>());
    }

    public static void freezeRegistry() {
        BuiltInRegistries.ENCHANTMENT.freeze();
    }

    public static void registerEnchant(Enchant enchant) {
        NamespacedKey key = enchant.getNamespacedKey();
        ResourceLocation nmsKey = new ResourceLocation(key.getNamespace(), key.getKey());
        Registry.register(BuiltInRegistries.ENCHANTMENT, nmsKey, enchant);
    }

}