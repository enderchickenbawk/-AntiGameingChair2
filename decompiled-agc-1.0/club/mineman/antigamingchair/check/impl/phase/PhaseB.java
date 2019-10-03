package club.mineman.antigamingchair.check.impl.phase;

import club.mineman.antigamingchair.check.checks.*;
import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.data.*;
import org.bukkit.entity.*;
import net.minecraft.server.v1_8_R3.*;

public class PhaseB extends PacketCheck
{
    private int stage;
    
    public PhaseB(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData);
    }
    
    @Override
    public void handleCheck(final Player player, final Packet packet) {
        final String className;
        final String simpleName = className = packet.getClass().getSimpleName();
        final String s;
        switch (s = simpleName) {
            case "PacketPlayInFlying": {
                if (this.stage == 0) {
                    ++this.stage;
                    break;
                }
                this.stage = 0;
                break;
            }
            case "PacketPlayInPosition": {
                if (this.stage >= 2) {
                    ++this.stage;
                    break;
                }
                break;
            }
            case "PacketPlayInEntityAction": {
                if (((PacketPlayInEntityAction)packet).b() == PacketPlayInEntityAction.EnumPlayerAction.START_SNEAKING) {
                    if (this.stage == 1) {
                        ++this.stage;
                        break;
                    }
                    this.stage = 0;
                    break;
                }
                else {
                    if (((PacketPlayInEntityAction)packet).b() == PacketPlayInEntityAction.EnumPlayerAction.START_SNEAKING && this.stage >= 3) {
                        this.plugin.getAlertsManager().forceAlert("Might be phasing: " + this.stage, player);
                        break;
                    }
                    break;
                }
                break;
            }
            default:
                break;
        }
    }
}
