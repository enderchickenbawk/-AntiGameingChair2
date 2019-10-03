package club.mineman.antigamingchair.check.impl.killaura;

import club.mineman.antigamingchair.check.checks.*;
import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.data.*;
import org.bukkit.entity.*;
import club.mineman.antigamingchair.event.player.*;
import club.mineman.antigamingchair.check.*;
import net.minecraft.server.v1_8_R3.*;

public class KillAuraF extends PacketCheck
{
    private boolean sent;
    
    public KillAuraF(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData);
    }
    
    @Override
    public void handleCheck(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInBlockDig) {
            final PacketPlayInBlockDig.EnumPlayerDigType digType = ((PacketPlayInBlockDig)packet).c();
            if (digType == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK || digType == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                this.sent = true;
            }
        }
        else if (packet instanceof PacketPlayInUseEntity && this.sent) {
            if (this.alert(PlayerAlertEvent.AlertType.RELEASE, player, "failed Kill Aura Check F.")) {
                final int violations = this.playerData.getViolations(this, 60000L);
                if (!this.playerData.isBanning() && violations > 2) {
                    this.ban(player, "Kill Aura Check F");
                }
            }
        }
        else if (packet instanceof PacketPlayInFlying) {
            this.sent = false;
        }
    }
}
