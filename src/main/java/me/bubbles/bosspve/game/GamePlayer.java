package me.bubbles.bosspve.game;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.EntityBase;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.settings.Settings;
import me.bubbles.bosspve.stages.Stage;
import me.bubbles.bosspve.utility.UtilCalculator;
import me.bubbles.bosspve.utility.UtilNumber;
import me.bubbles.bosspve.utility.UtilUserData;
import me.bubbles.bosspve.utility.messages.PreparedMessages;
import me.bubbles.bosspve.utility.string.UtilString;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GamePlayer extends GameBase {

    private Player player;
    private UtilUserData cache;

    private double protection;
    private double damage;
    private double speed;

    public GamePlayer(Player player) {
        super(UtilCalculator.getMaxHealth(player));
        this.player=player;
        //this.protection=UtilCalculator.getProtection(player);
        updateStats();
        updateCache();
    }

    public void setXp(int xp) {
        cache.setXp(xp);
        updateXpBar();
    }

    public void updateXpBar() {
        if(player.getLevel()<cache.getLevel()) {
            Stage stage = BossPVE.getInstance().getStageManager().getStage(cache.getLevel());
            if(stage!=null) {
                if((Boolean) Settings.NEXTSTAGE_MESSAGES.getOption(cache.getOrDefault(Settings.NEXTSTAGE_MESSAGES))) {
                    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    player.sendTitle(UtilString.colorFillPlaceholders("&a&lStage Unlocked"), UtilString.colorFillPlaceholders("&aStage "+stage.getLevelRequirement()+" unlocked"), 5, 60, 5);
                }
            } else {
                if((Boolean) Settings.LEVELUP_MESSAGES.getOption(cache.getOrDefault(Settings.LEVELUP_MESSAGES))) {
                    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    player.sendTitle(UtilString.colorFillPlaceholders("&a&lLevel Up"), UtilString.colorFillPlaceholders("&aLevel "+cache.getLevel()), 5, 40, 5);
                }
            }
        }
        player.setLevel(cache.getLevel());
        float result = getPercentComplete(cache.getXp(), cache.getLevel());
        if(result<0.0||result>=1.0) {
            return;
        }
        player.setExp(result);
    }

    private float getPercentComplete(int xp, int level) { // get the percent complete

        float nextLevel = level+1;
        float xpRequirement = nextLevel*nextLevel*10;
        float lastXpRequirement = level*level*10;

        return (xp-lastXpRequirement)/(xpRequirement-lastXpRequirement);

    }

    public void updateHealthBar() {
        if(player.getHealth()==0D) {
            return;
        }
        double percent = health/maxHealth;
        if(20D*percent<0.5) {
            player.setHealth(1);
            return;
        }
        player.setHealth(UtilNumber.clampBorder(20, 0, 20*percent));
        updateSubtitle();
        /*ClientboundSetActionBarTextPacket packet = new ClientboundSetActionBarTextPacket(utc.getTextComponent());
        player.spigot().sendMessage();
        player.
        ((CraftPlayer) player).getHandle().connection.send();*/

    }

    public void updateSubtitle() {

        double healthNum = roundNumber(health);

        String result = "&c" + healthNum + "/" + maxHealth + " ❤"; // HEALTH
        result+="       ";
        //result+="&a"+damage+" \uD83D\uDDE1";

        double dmgNum = roundNumber(damage);

        result+="&a"+dmgNum+" 🗡"; // DAMAGE
        result+="       ";


        double protNum = roundNumber(protection);

        result+="&b"+protNum+" ♦"; // PROTECTION/DEFENSE
        result+="  ";

        //player.sendTitle("", result, 0, 1000, 0);
        //ClientboundSetTitlesAnimationPacket

        Component component = Component.literal(UtilString.colorFillPlaceholders(result));
        ClientboundSetActionBarTextPacket packet = new ClientboundSetActionBarTextPacket(component);

        CraftPlayer craftPlayer = (CraftPlayer) player;
        ServerPlayer serverPlayer = craftPlayer.getHandle();

        serverPlayer.connection.send(packet);

    }

    private double roundNumber(double num) {
        return (double) Math.round(num * 10.0) / 10;
    }

    @Override
    public boolean setHealth(double health) {
        boolean bool = super.setHealth(health);
        updateHealthBar();
        return bool;
    }

    @Override
    public void setMaxHealth(double maxHealth) {
        super.setMaxHealth(maxHealth);
        setHealth(UtilNumber.clampBorder(maxHealth, 0, health));
        updateHealthBar();
    }

    private void setProtection(double protection) {
        this.protection=protection;
        updateSubtitle();
    }

    private void setDamage(double damage) {
        this.damage=damage;
        updateSubtitle();
    }

    private void setSpeed(double speed) {
        this.speed=speed;
        player.setWalkSpeed((float) UtilNumber.clampBorder(1F, -1F, speed));
        //player.setFlySpeed((float) speed);
        //updateSubtitle();
    }

    public void updateStats() {
        if(player==null) {
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(BossPVE.getInstance(), () -> {
            setMaxHealth(UtilCalculator.getMaxHealth(player));
            setProtection(UtilCalculator.getProtection(player));
            setDamage(UtilCalculator.getDamage(player));
            setSpeed(UtilCalculator.getSpeed(player));
            updateHealthBar();
        });
    }

    public void updateCache(UtilUserData uud) {
        this.cache=uud;
    }

    public void updateCache() {
        this.cache = UtilUserData.getUtilUserData(player.getUniqueId());
    }

    public UtilUserData getCache() {
        return cache;
    }

    public UUID getUuid() {
        return player.getUniqueId();
    }

    public Player getBukkitPlayer() {
        return player;
    }

    public double getProtection() {
        return protection;
    }

    public double getDamage() {
        return damage;
    }

    @Override
    public boolean damage(double x) {
        boolean a = super.damage(x);
        updateHealthBar();
        return a;
    }

    public boolean heal(double x) {
        boolean a = isAlive();
        if(!a) {
            return false;
        }
        setHealth(UtilNumber.clampBorder(maxHealth, 0, health+x));
        //health=;
        //updateHealthBar();
        return true;
    }

    public boolean healPercent(double x) {
        double result = UtilNumber.clampBorder(1, 0.1, x);
        result = maxHealth*result;
        return heal(result);
    }

    public void give(double xp, double money, EntityBase cause, boolean message) {
        if(xp!=0) {
            setXp((int) (cache.getXp()+xp));
        }
        if(money!=0) {
            BossPVE.getInstance().getEconomy().depositPlayer(player,money);
        }
        if(message) {
            if(cause!=null) {
                PreparedMessages.kill(BossPVE.getInstance().getGameManager().getGamePlayer(player), cause, (int) xp, money);
            } else {
                PreparedMessages.give(BossPVE.getInstance().getGameManager().getGamePlayer(player), (int) xp, money);
            }
        }
    }

}
