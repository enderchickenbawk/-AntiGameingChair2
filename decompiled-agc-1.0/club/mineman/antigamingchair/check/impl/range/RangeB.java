package club.mineman.antigamingchair.check.impl.range;

import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.data.*;
import org.bukkit.entity.*;
import club.mineman.antigamingchair.check.*;
import java.util.*;

public class RangeB extends AbstractCheck<double[]>
{
    private final Deque<Double> distances;
    
    public RangeB(final AntiGamingChair plugin, final PlayerData playerData) {
        super(plugin, playerData, double[].class);
        this.distances = new LinkedList<Double>();
    }
    
    @Override
    public void handleCheck(final Player player, final double[] doubles) {
        final double range = doubles[0];
        final double over = doubles[1];
        this.distances.addLast(range - over);
        if (this.distances.size() == 30) {
            int count = 0;
            double average = 0.0;
            for (final double d : this.distances) {
                if (d < -1.0) {
                    continue;
                }
                ++count;
                average += d;
            }
            average /= count;
            final double threshold = -0.2;
            double vl = this.playerData.getCheckVl(this);
            if (average > -0.2 && count > 10) {
                this.plugin.getAlertsManager().forceAlert(String.format("failed Range Check B (Development). %.3f. %.2f. VL %.2f.", average, -0.2, vl), player);
            }
            else {
                vl -= 0.3;
            }
            this.playerData.setCheckVl(vl, this);
            this.distances.clear();
        }
    }
}
