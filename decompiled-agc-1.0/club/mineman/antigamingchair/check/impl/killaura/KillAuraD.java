package club.mineman.antigamingchair.check.impl.killaura;

import club.mineman.antigamingchair.check.checks.*;
import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.data.*;
import org.bukkit.entity.*;
import net.minecraft.server.v1_8_R3.*;
import club.mineman.antigamingchair.event.player.*;
import club.mineman.antigamingchair.check.*;

public class KillAuraD extends PacketCheck
{
    public KillAuraD(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData);
    }
    
    @Override
    public void handleCheck(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInUseEntity && ((PacketPlayInUseEntity)packet).a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK && this.playerData.isPlacing() && this.alert(PlayerAlertEvent.AlertType.RELEASE, player, "failed Kill Aura Check D.")) {
            final int violations = this.playerData.getViolations(this, 60000L);
            if (!this.playerData.isBanning() && violations > 2) {
                this.ban(player, "Kill Aura Check D");
            }
        }
    }
}
