package me.bubbles.bosspve.utility;

import me.bubbles.bosspve.items.manager.bases.enchants.Enchant;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_21_R1.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_21_R1.CraftServer;
import org.bukkit.craftbukkit.v1_21_R1.util.CraftNamespacedKey;
import org.bukkit.inventory.EquipmentSlot;

import java.util.*;
import java.util.function.BiConsumer;

public class UtilEnchant {

    private static final Set<Material> ALL_MATERIALS = getAllMaterials();
    private static final Registry<Enchantment> ENCHANTMENT_REGISTRY = getEnchantmentRegistry();
    private static final HolderSet<Enchantment> EXCLUSIVE_SET = HolderSet.direct();
    private static final DataComponentMap EFFECTS = DataComponentMap.builder().build();

    private static Registry<Enchantment> getEnchantmentRegistry() {
        DedicatedServer dedicatedServer = ((CraftServer) Bukkit.getServer()).getServer();
        Optional<Registry<Enchantment>> opt = dedicatedServer.registryAccess().registry(Registries.ENCHANTMENT);
        return opt.orElse(null);
    }

    private static Set<Material> getAllMaterials() {
        Set<Material> material = new HashSet<Material>();
        Material[] allMat = Material.values();
        for (int i = 0; i < allMat.length; i++) {
            material.add(allMat[i]);
        }
        return material;
    }

    public static void unfreezeRegistry() {

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

        Reflex.setFieldValue(ENCHANTMENT_REGISTRY, "l", false);             // MappedRegistry#frozen
        Reflex.setFieldValue(ENCHANTMENT_REGISTRY, "m", new IdentityHashMap<>()); // MappedRegistry#unregisteredIntrusiveHolders
        
    }

    public static void freezeRegistry() {
        ENCHANTMENT_REGISTRY.freeze();
    }

    public static void registerEnchant(Enchant enchant) {

        ResourceKey<Enchantment> key = key(enchant.getKey());
        Component component = Component.literal(enchant.getName());

        HolderSet.Named<Item> allItems = createItemSet("enchant_primary", enchant.getKey(), ALL_MATERIALS);

        Enchantment.EnchantmentDefinition definition = Enchantment.definition(allItems, 1, enchant.getMaxLevel(), new Enchantment.Cost(1, 1), new Enchantment.Cost(enchant.getMaxLevel(), 2), 1, nmsSlots(EquipmentSlot.values()));

        Enchantment enchantment = new Enchantment(component, definition, EXCLUSIVE_SET, EFFECTS);

        // You must create a holder, otherwise enchantment won't be registered.
        Holder.Reference<Enchantment> reference = ENCHANTMENT_REGISTRY.createIntrusiveHolder(enchantment);
        // Actual register code.
        Registry.register(ENCHANTMENT_REGISTRY, key, enchantment);

        boolean isCurse = false;
        boolean isTreasure = false;
        boolean isDiscoverable = true;
        boolean isTradeable = true;

        if (isCurse) {
            addInTag(EnchantmentTags.CURSE, reference);
        }
        else {
            if (isTreasure) {
                addInTag(EnchantmentTags.TREASURE, reference);
            }
            else addInTag(EnchantmentTags.NON_TREASURE, reference);

            if (isTradeable) {
                addInTag(EnchantmentTags.TRADEABLE, reference);
            }
            else removeFromTag(EnchantmentTags.TRADEABLE, reference);

            if (isDiscoverable) {
                addInTag(EnchantmentTags.IN_ENCHANTING_TABLE, reference);
            }
            else removeFromTag(EnchantmentTags.IN_ENCHANTING_TABLE, reference);
        }

        enchant.setEnchantment(enchantment);

        /*ResourceLocation nmsKey = new ResourceLocation(key.getNamespace(), key.getKey());
        Registry.register(BuiltInRegistries.ENCHANTMENT, nmsKey, enchant);*/

    }

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.withDefaultNamespace(name));
    }

    private static final String HOLDER_SET_NAMED_CONTENTS_FIELD = "c"; // 'contents' field of the HolderSet.Named
    private static final String HOLDER_REFERENCE_TAGS_FIELD = "b"; // 'tags' field of the Holder.Reference

    public static net.minecraft.world.entity.EquipmentSlotGroup[] nmsSlots(EquipmentSlot[] slots) {
        net.minecraft.world.entity.EquipmentSlotGroup[] nmsSlots = new net.minecraft.world.entity.EquipmentSlotGroup[slots.length];

        for (int index = 0; index < nmsSlots.length; index++) {
            org.bukkit.inventory.EquipmentSlot bukkitSlot = slots[index];
            nmsSlots[index] = CraftEquipmentSlot.getNMSGroup(bukkitSlot.getGroup());
        }

        return nmsSlots;
    }

    @SuppressWarnings("unchecked")
    private static HolderSet.Named<Item> createItemSet(String prefix, String enchantId, Set<Material> materials) {

        DedicatedServer dedicatedServer = ((CraftServer) Bukkit.getServer()).getServer();
        Registry<Item> items = dedicatedServer.registryAccess().registry(Registries.ITEM).orElseThrow();
        TagKey<Item> customKey = TagKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace(prefix + "/" + enchantId));
        HolderSet.Named<Item> customItems = items.getOrCreateTag(customKey);
        List<Holder<Item>> holders = new ArrayList<>();

        materials.forEach(material -> {
            ResourceLocation location = CraftNamespacedKey.toMinecraft(material.getKey());
            Holder.Reference<Item> holder = items.getHolder(location).orElse(null);
            if (holder == null) return;

            // We must reassign the 'tags' field value because of the HolderSet#contains(Holder<T> holder) behavior.
            // It checks if Holder.Reference.is(this.key) -> Holder.Reference.tags.contains(key). Where 'key' is our custom key created above.
            // So, even if our HolderSet content is filled with items, we have to include their tag to the actual items in registry.
            Set<TagKey<Item>> holderTags = new HashSet<>((Set<TagKey<Item>>) Reflex.getFieldValue(holder, HOLDER_REFERENCE_TAGS_FIELD));
            holderTags.add(customKey);
            Reflex.setFieldValue(holder, HOLDER_REFERENCE_TAGS_FIELD, holderTags);

            holders.add(holder);
        });

        Reflex.setFieldValue(customItems, HOLDER_SET_NAMED_CONTENTS_FIELD, holders);

        return customItems;
    }

    private static void addInTag(TagKey<Enchantment> tagKey, Holder.Reference<Enchantment> reference) {
        modfiyTag(tagKey, reference, List::add);
    }

    private static void removeFromTag(TagKey<Enchantment> tagKey, Holder.Reference<Enchantment> reference) {
        modfiyTag(tagKey, reference, List::remove);
    }

    private static void modfiyTag(TagKey<Enchantment> tagKey,
                                  Holder.Reference<Enchantment> reference,
                                  BiConsumer<List<Holder<Enchantment>>, Holder.Reference<Enchantment>> consumer) {
        // Get HolderSet of the TagKey
        HolderSet.Named<Enchantment> holders = ENCHANTMENT_REGISTRY.getTag(tagKey).orElse(null);
        if (holders == null) {
            return;
        }

        modfiyHolderSetContents(holders, reference, consumer);
    }

    @SuppressWarnings("unchecked")
    private static <T> void modfiyHolderSetContents(HolderSet.Named<T> holders,
                                                    Holder.Reference<T> reference,
                                                    BiConsumer<List<Holder<T>>, Holder.Reference<T>> consumer) {

        // We must use reflection to get a mutable Holder list from the HolderSet.
        List<Holder<T>> contents = new ArrayList<>((List<Holder<T>>) Reflex.getFieldValue(holders, HOLDER_SET_NAMED_CONTENTS_FIELD));
        // Do something with it.
        consumer.accept(contents, reference);
        // Assign it back to the HolderSet.
        Reflex.setFieldValue(holders, HOLDER_SET_NAMED_CONTENTS_FIELD, contents);
    }

}