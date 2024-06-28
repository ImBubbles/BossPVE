package me.bubbles.bosspve.utility.messages;

import me.bubbles.bosspve.game.GamePlayer;
import me.bubbles.bosspve.settings.BooleanSetting;
import me.bubbles.bosspve.settings.Settings;

public enum MessageType {

    KILL_MESSAGE(Settings.KILL_MESSAGES),
    ENCHANT_PROC(Settings.PROCC_MESSAGES),
    ITEM_DROP(Settings.ITEMDROP_MESSAGES),
    BALANCE_GAIN,
    OTHER;

    private Settings setting;
    private BooleanSetting booleanSetting;

    MessageType() {
        this.setting=null;
    }

    MessageType(Settings setting) {
        this.setting=setting;
        if(setting.get() instanceof BooleanSetting) {
            this.booleanSetting=(BooleanSetting) setting.get();
        }
    }

    public boolean allowMessage(GamePlayer gamePlayer) {
        if(setting==null) {
            return true;
        }
        return booleanSetting.getOption(gamePlayer.getCache().getOrDefault(setting));
    }

}
