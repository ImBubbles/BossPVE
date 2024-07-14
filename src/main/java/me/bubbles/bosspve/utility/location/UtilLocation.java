package me.bubbles.bosspve.utility.location;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.session.SessionManager;
import me.bubbles.bosspve.BossPVE;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class UtilLocation {

    public static Location toLocation(String value) {
        String[] values = value.split(",");
        Location location;
        if(values.length==4) {
            location=new Location(
                    BossPVE.getInstance().getMultiverseCore().getMVWorldManager().getMVWorld(values[0]).getCBWorld(),
                    Double.parseDouble(values[1]),
                    Double.parseDouble(values[2]),
                    Double.parseDouble(values[3])
            );
        } else if (values.length==6) {
            location=new Location(
                    BossPVE.getInstance().getMultiverseCore().getMVWorldManager().getMVWorld(values[0]).getCBWorld(),
                    Double.parseDouble(values[1]),
                    Double.parseDouble(values[2]),
                    Double.parseDouble(values[3]),
                    Float.parseFloat(values[4]),
                    Float.parseFloat(values[5])
            );
        } else {
            location=null;
        }
        return location;
    }

    public static String asLocationString(Location location) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(location.getWorld().getName());
        stringBuilder.append(",");
        stringBuilder.append(location.getX());
        stringBuilder.append(",");
        stringBuilder.append(location.getY());
        stringBuilder.append(",");
        stringBuilder.append(location.getZ());
        stringBuilder.append(",");
        stringBuilder.append(location.getYaw());
        stringBuilder.append(",");
        stringBuilder.append(location.getPitch());
        return stringBuilder.toString();
    }

}
