package club.mineman.antigamingchair.manager;

import club.mineman.antigamingchair.*;
import java.util.*;

public class BanWaveManager
{
    private final AntiGamingChair plugin;
    private final Deque<Long> cheaters;
    private final Map<Long, String> cheaterNames;
    private boolean banWaveStarted;
    
    public void loadCheaters() {
    }
    
    public void clearCheaters() {
        this.cheaters.clear();
        this.cheaterNames.clear();
    }
    
    public long queueCheater() {
        return this.cheaters.poll();
    }
    
    public String getCheaterName(final long id) {
        return this.cheaterNames.get(id);
    }
    
    public AntiGamingChair getPlugin() {
        return this.plugin;
    }
    
    public Deque<Long> getCheaters() {
        return this.cheaters;
    }
    
    public Map<Long, String> getCheaterNames() {
        return this.cheaterNames;
    }
    
    public boolean isBanWaveStarted() {
        return this.banWaveStarted;
    }
    
    public BanWaveManager(final AntiGamingChair plugin) {
        this.cheaters = new LinkedList<Long>();
        this.cheaterNames = new HashMap<Long, String>();
        this.plugin = plugin;
    }
    
    public void setBanWaveStarted(final boolean banWaveStarted) {
        this.banWaveStarted = banWaveStarted;
    }
}
