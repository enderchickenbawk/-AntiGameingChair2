package club.mineman.antigamingchair.check.impl.badpackets;

import club.mineman.antigamingchair.check.checks.*;
import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.data.*;
import org.bukkit.entity.*;
import net.minecraft.server.v1_8_R3.*;
import club.mineman.antigamingchair.event.player.*;

public class BadPacketsB extends PacketCheck
{
    public BadPacketsB(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData);
    }
    
    @Override
    public void handleCheck(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInFlying && Math.abs(((PacketPlayInFlying)packet).e()) > 90.0f && this.alert(PlayerAlertEvent.AlertType.RELEASE, player, "failed Bad Packets Check B.") && !this.playerData.isBanning()) {
            this.ban(player, "Bad Packets Check B");
        }
    }
}
