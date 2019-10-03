package club.mineman.antigamingchair.event;

import org.bukkit.event.player.*;
import org.bukkit.event.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import com.google.common.base.*;

public class PlayerUpdatePositionEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private Location from;
    private Location to;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerUpdatePositionEvent(final Player player, final Location from, final Location to) {
        super(player);
        this.cancel = false;
        this.from = from;
        this.to = to;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public Location getFrom() {
        return this.from;
    }
    
    public void setFrom(final Location from) {
        this.validateLocation(from);
        this.from = from;
    }
    
    public Location getTo() {
        return this.to;
    }
    
    public void setTo(final Location to) {
        this.validateLocation(to);
        this.to = to;
    }
    
    private void validateLocation(final Location loc) {
        Preconditions.checkArgument(loc != null, (Object)"Cannot use null location!");
        Preconditions.checkArgument(loc.getWorld() != null, (Object)"Cannot use null location with null world!");
    }
    
    public HandlerList getHandlers() {
        return PlayerUpdatePositionEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerUpdatePositionEvent.handlers;
    }
}
