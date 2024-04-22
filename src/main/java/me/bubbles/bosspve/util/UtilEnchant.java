package me.bubbles.bosspve.util;

import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import me.bubbles.bosspve.items.manager.bases.items.Item;
import org.bukkit.craftbukkit.v1_20_R3.enchantments.CraftEnchantment;
import org.bukkit.inventory.ItemStack;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.IdentityHashMap;

import java.lang.reflect.Field;

public class UtilEnchant {

    public static int getLevel(ItemStack itemStack, Enchant enchant) {
        if(itemStack==null) {
            return -1;
        }
        if(!itemStack.hasItemMeta()) {
            return -1;
        }
        if(!itemStack.getEnchantments().containsKey(enchant)) {
            return -1;
        }
        return itemStack.getItemMeta().getEnchantLevel(CraftEnchantment.minecraftToBukkit(enchant));
    }

    public static int getLevel(Item item, Enchant enchant) {
        return getLevel(item.nmsAsItemStack(), enchant);
    }

    public static void unfreezeRegistry() {
        setFieldValue(BuiltInRegistries.ENCHANTMENT, "l", false);
        setFieldValue(BuiltInRegistries.ENCHANTMENT, "m", new IdentityHashMap<>());
    }

    public static void freezeRegistry() {
        BuiltInRegistries.ENCHANTMENT.freeze();
    }

    public static void registerEnchantment(Enchant enchant) {
        Registry.register(BuiltInRegistries.ENCHANTMENT, enchant.getName(), enchant);
    }

    private static Field getField(@NotNull Class<?> clazz, @NotNull String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        }
        catch (NoSuchFieldException e) {
            Class<?> superClass = clazz.getSuperclass();
            return superClass == null ? null : getField(superClass, fieldName);
        }
    }

    private static boolean setFieldValue(@NotNull Object of, @NotNull String fieldName, @Nullable Object value) {
        try {
            boolean isStatic = of instanceof Class;
            Class<?> clazz = isStatic ? (Class<?>) of : of.getClass();

            Field field = getField(clazz, fieldName);
            if (field == null) return false;

            field.setAccessible(true);
            field.set(isStatic ? null : of, value);
            return true;
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

}
