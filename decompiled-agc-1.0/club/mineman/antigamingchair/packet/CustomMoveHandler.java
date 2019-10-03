package club.mineman.antigamingchair.packet;

import gg.ragemc.spigot.handler.*;
import club.mineman.antigamingchair.*;
import org.bukkit.entity.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.event.*;
import club.mineman.antigamingchair.event.*;

public class CustomMoveHandler implements MovementHandler
{
    private final AntiGamingChair plugin;
    
    public CustomMoveHandler(final AntiGamingChair plugin) {
        this.plugin = plugin;
    }
    
    public void handleUpdateLocation(final Player arg0, final Location arg1, final Location arg2, final PacketPlayInFlying arg3) {
        final PlayerUpdatePositionEvent event = new PlayerUpdatePositionEvent(arg0, arg2, arg2);
        if (!event.isCancelled()) {
            Bukkit.getPluginManager().callEvent((Event)event);
        }
    }
    
    public void handleUpdateRotation(final Player arg0, final Location arg1, final Location arg2, final PacketPlayInFlying arg3) {
        final PlayerUpdateRotationEvent event = new PlayerUpdateRotationEvent(arg0, arg2, arg2);
        if (!event.isCancelled()) {
            Bukkit.getPluginManager().callEvent((Event)event);
        }
    }
}
