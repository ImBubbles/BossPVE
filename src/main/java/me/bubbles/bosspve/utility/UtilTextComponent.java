package me.bubbles.bosspve.utility;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class UtilTextComponent {

    private TextComponent textComponent;

    public UtilTextComponent(TextComponent textComponent) {
        this.textComponent=textComponent;
    }

    public UtilTextComponent(String string) {
        this(new TextComponent(string));
    }

    public UtilTextComponent append(String text) {
        textComponent.addExtra(text);
        return this;
    }

    public UtilTextComponent append(BaseComponent component) {
        textComponent.addExtra(component);
        return this;
    }

    public TextComponent getTextComponent() {
        return textComponent;
    }

}