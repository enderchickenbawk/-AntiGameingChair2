package club.mineman.antigamingchair.check.impl.autoclicker;

import club.mineman.antigamingchair.check.checks.*;
import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.data.*;
import org.bukkit.entity.*;
import club.mineman.antigamingchair.check.*;
import club.mineman.antigamingchair.event.player.*;
import net.minecraft.server.v1_8_R3.*;

public class AutoClickerC extends PacketCheck
{
    private boolean sent;
    
    public AutoClickerC(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData);
    }
    
    @Override
    public void handleCheck(final Player player, final Packet packet) {
        if (packet instanceof PacketPlayInBlockDig) {
            final PacketPlayInBlockDig.EnumPlayerDigType digType = ((PacketPlayInBlockDig)packet).c();
            if (digType == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                this.sent = true;
            }
            else if (digType == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                int vl = (int)this.playerData.getCheckVl(this);
                if (this.sent) {
                    if (++vl >= 10 && !this.alert(PlayerAlertEvent.AlertType.RELEASE, player, "failed Auto Clicker Check C. VL " + vl + ".") && !this.playerData.isBanWave() && vl >= 20) {
                        this.banWave(player, "Auto Clicker Check C");
                    }
                }
                else {
                    vl -= 3;
                }
                this.playerData.setCheckVl(vl, this);
            }
        }
        else if (packet instanceof PacketPlayInArmAnimation) {
            this.sent = false;
        }
    }
}
