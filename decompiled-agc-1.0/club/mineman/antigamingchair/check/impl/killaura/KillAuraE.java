package club.mineman.antigamingchair.check.impl.killaura;

import club.mineman.antigamingchair.check.checks.*;
import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.data.*;
import org.bukkit.entity.*;
import net.minecraft.server.v1_8_R3.*;
import club.mineman.antigamingchair.check.*;
import club.mineman.antigamingchair.event.player.*;
import club.mineman.antigamingchair.location.*;
import java.util.*;

public class KillAuraE extends PacketCheck
{
    private final Deque<Long> delays;
    
    public KillAuraE(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData);
        this.delays = new LinkedList<Long>();
    }
    
    @Override
    public void handleCheck(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInArmAnimation && System.currentTimeMillis() - this.playerData.getLastDelayedMovePacket() > 110L && !this.playerData.isAllowTeleport()) {
            final CustomLocation lastMovePacket = this.playerData.getLastMovePacket();
            if (lastMovePacket == null) {
                return;
            }
            final long delay = System.currentTimeMillis() - lastMovePacket.getTimestamp();
            this.delays.add(delay);
            if (this.delays.size() == 40) {
                double average = 0.0;
                for (final long loopDelay : this.delays) {
                    average += loopDelay;
                }
                average /= this.delays.size();
                double vl = this.playerData.getCheckVl(this);
                if (average <= 45.0) {
                    if (++vl >= 4.0 && this.alert(PlayerAlertEvent.AlertType.RELEASE, player, String.format("failed Kill Aura Check E. AVG %.3f. VL %.2f.", average, vl)) && !this.playerData.isBanning() && vl >= 20.0) {
                        this.ban(player, "Kill Aura Check E");
                    }
                }
                else {
                    vl -= 1.25;
                }
                this.playerData.setCheckVl(vl, this);
                this.delays.clear();
            }
        }
    }
}
