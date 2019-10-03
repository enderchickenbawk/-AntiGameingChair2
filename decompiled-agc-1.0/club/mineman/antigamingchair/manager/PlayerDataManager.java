package club.mineman.antigamingchair.manager;

import club.mineman.antigamingchair.data.*;
import club.mineman.antigamingchair.*;
import org.bukkit.entity.*;
import java.util.*;

public class PlayerDataManager
{
    private final Map<UUID, PlayerData> playerDataMap;
    private final AntiGamingChair plugin;
    
    public void addPlayerData(final Player player) {
        this.playerDataMap.put(player.getUniqueId(), new PlayerData(this.plugin));
    }
    
    public void removePlayerData(final Player player) {
        this.playerDataMap.remove(player.getUniqueId());
    }
    
    public PlayerData getPlayerData(final Player player) {
        return this.playerDataMap.get(player.getUniqueId());
    }
    
    public PlayerDataManager(final AntiGamingChair plugin) {
        this.playerDataMap = new HashMap<UUID, PlayerData>();
        this.plugin = plugin;
    }
}
