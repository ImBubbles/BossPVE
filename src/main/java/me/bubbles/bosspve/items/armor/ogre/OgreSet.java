package me.bubbles.bosspve.items.armor.ogre;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.bases.armor.Armor;
import me.bubbles.bosspve.items.manager.bases.armor.ArmorSet;

public class OgreSet extends ArmorSet {

    public OgreSet(BossPVE plugin) {
        super(plugin);
    }

    @Override
    public Armor getBoots() {
        return new OgreBoots(plugin);
    }

    @Override
    public Armor getPants() {
        return new OgrePants(plugin);
    }

    @Override
    public Armor getChestplate() {
        return new OgreChestplate(plugin);
    }

    @Override
    public Armor getHelmet() {
        return new OgreHelmet(plugin);
    }

}
