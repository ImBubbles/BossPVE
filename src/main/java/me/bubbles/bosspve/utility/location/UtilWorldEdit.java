package me.bubbles.bosspve.utility.location;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.session.SessionManager;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class UtilWorldEdit {

    public static LocalSession getLocalSession(Player bukkitPlayer) {
        com.sk89q.worldedit.entity.Player player = BukkitAdapter.adapt(bukkitPlayer);
        SessionManager sessionManager = com.sk89q.worldedit.WorldEdit.getInstance().getSessionManager();
        return sessionManager.get(player);
    }

    public static Location getBlockLocation(World world, BlockVector3 blockVector3) {
        if(blockVector3==null) {
            return null;
        }
        Vector3 vector3 = blockVector3.toVector3();
        if(vector3==null) {
            return null;
        }
        if(world==null) {
            return null;
        }
        return new Location(BukkitAdapter.asBukkitWorld(world).getWorld(), vector3.blockX(), vector3.blockY(), vector3.getBlockZ());
    }

}
