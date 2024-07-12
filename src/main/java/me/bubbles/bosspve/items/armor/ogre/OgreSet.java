package me.bubbles.bosspve.items.armor.ogre;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.bases.armor.Armor;
import me.bubbles.bosspve.items.manager.bases.armor.ArmorSet;

public class OgreSet extends ArmorSet {

    @Override
    public Armor getBoots() {
        return new OgreBoots();
    }

    @Override
    public Armor getPants() {
        return new OgrePants();
    }

    @Override
    public Armor getChestplate() {
        return new OgreChestplate();
    }

    @Override
    public Armor getHelmet() {
        return new OgreHelmet();
    }

}
