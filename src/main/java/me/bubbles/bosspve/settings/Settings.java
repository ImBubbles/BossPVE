package me.bubbles.bosspve.settings;

import org.bukkit.Material;

public enum Settings {

    KILL_MESSAGES(new BooleanSetting("Kill Messages", true) {
        @Override
        public Material getMaterial(Boolean option) {
            return option ? Material.IRON_SWORD : Material.WOODEN_SWORD;
        }
    }),
    PROCC_MESSAGES(new BooleanSetting("PROCC_MESSAGES", "Activation Messages", true) {
        @Override
        public Material getMaterial(Boolean option) {
            return option ? Material.ENCHANTED_BOOK : Material.BOOK;
        }
    }),
    ITEMDROP_MESSAGES(new BooleanSetting("ITEMDROP_MESSAGES", "Item Drop Messages", true) {
        @Override
        public Material getMaterial(Boolean option) {
            return option ? Material.STICK : Material.BARRIER;
        }
    }),
    LEVELUP_MESSAGES(new BooleanSetting("LEVELUP_MESSAGES", "Level Up Display", true) {
        @Override
        public Material getMaterial(Boolean option) {
            return option ? Material.EMERALD : Material.EMERALD_ORE;
        }
    }),
    NEXTSTAGE_MESSAGES(new BooleanSetting("NEXTSTAGE_MESSAGES", "Stage Unlock Display", true) {
        @Override
        public Material getMaterial(Boolean option) {
            return option ? Material.EXPERIENCE_BOTTLE : Material.GLASS_BOTTLE;
        }
    });

    private Setting setting;

    Settings(Setting setting) {
        this.setting=setting;
    }

    public Setting get() {
        return setting;
    }

    public String getDisplayName() {
        return setting.getDisplayName();
    }

    public Object getDefault() {
        return setting.getDefault();
    }

    public Object getNext(Object object) {
        return setting.getNext(object);
    }

    public int getIndex(Object obj) {
        if(!obj.getClass().equals(setting.getDefault().getClass())) {
            return -1;
        }
        return setting.getIndex(obj);
    }

    public Object getOption(int index) {
        return setting.getOption(index);
    }

    public String valueToString(Object obj) {
        if(!obj.getClass().equals(setting.getDefault().getClass())) {
            return "INVALID CHECK";
        }
        return setting.valueToString(obj);
    }

    public Material getMaterial(Object object) {
        return setting.getMaterial(object);
    }

    @Override
    public String toString() {
        return setting.getKey();
    }

}
