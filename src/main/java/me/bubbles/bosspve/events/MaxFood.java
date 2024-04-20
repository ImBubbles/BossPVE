package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class MaxFood extends Event {

    public MaxFood(BossPVE plugin) {
        super(plugin, FoodLevelChangeEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        FoodLevelChangeEvent e = (FoodLevelChangeEvent) event;
        if(!(e.getEntity() instanceof Player)) {
            return;
        }
        Player player = ((Player) e.getEntity()).getPlayer();
        if(player==null) {
            return;
        }
        e.setFoodLevel(20);
    }

}