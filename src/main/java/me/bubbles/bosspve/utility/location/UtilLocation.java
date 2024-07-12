package me.bubbles.bosspve.utility.location;

import me.bubbles.bosspve.BossPVE;
import org.bukkit.Location;

public class UtilLocation {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private boolean angle;

    public UtilLocation(double x, double y, double z) {
        this.x=x;
        this.y=y;
        this.z=z;
        this.angle=false;
    }

    public UtilLocation(double x, double y, double z, float yaw, float pitch) {
        this.x=x;
        this.y=y;
        this.z=z;
        this.yaw=yaw;
        this.pitch=pitch;
        this.angle=true;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public boolean hasAngle() {
        return this.angle;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

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
