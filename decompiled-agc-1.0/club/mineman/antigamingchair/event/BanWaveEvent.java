package club.mineman.antigamingchair.event;

import org.bukkit.event.*;

public class BanWaveEvent extends Event
{
    private static final HandlerList HANDLER_LIST;
    private final String instigator;
    
    static {
        HANDLER_LIST = new HandlerList();
    }
    
    public static HandlerList getHandlerList() {
        return BanWaveEvent.HANDLER_LIST;
    }
    
    public HandlerList getHandlers() {
        return BanWaveEvent.HANDLER_LIST;
    }
    
    public BanWaveEvent(final String instigator) {
        this.instigator = instigator;
    }
    
    public String getInstigator() {
        return this.instigator;
    }
}
