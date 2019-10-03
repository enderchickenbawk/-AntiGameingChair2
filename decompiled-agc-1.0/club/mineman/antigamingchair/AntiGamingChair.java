package club.mineman.antigamingchair;

import org.bukkit.plugin.java.*;
import club.mineman.antigamingchair.manager.*;
import net.minecraft.server.v1_8_R3.*;
import gg.ragemc.spigot.*;
import club.mineman.antigamingchair.packet.*;
import gg.ragemc.spigot.handler.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import club.mineman.antigamingchair.listener.*;

public class AntiGamingChair extends JavaPlugin
{
    private static AntiGamingChair instance;
    private PlayerDataManager playerDataManager;
    private BanWaveManager banWaveManager;
    private AlertsManager alertsManager;
    private LogManager logManager;
    private double rangeVl;
    private double rangeVlLow;
    
    public AntiGamingChair() {
        this.rangeVl = 25.0;
        this.rangeVlLow = 25.0;
    }
    
    public void onEnable() {
        (AntiGamingChair.instance = this).registerPacketHandler();
        this.registerManagers();
        this.registerListeners();
        this.registerCommands();
        this.registerRunnables();
    }
    
    public void onDisable() {
    }
    
    public boolean isAntiCheatEnabled() {
        return MinecraftServer.getServer().tps1.getAverage() > 19.0 && MinecraftServer.LAST_TICK_TIME + 100L > System.currentTimeMillis();
    }
    
    private void registerPacketHandler() {
        RageSpigot.INSTANCE.addMovementHandler((MovementHandler)new CustomMoveHandler(this));
        RageSpigot.INSTANCE.addPacketHandler((PacketHandler)new CustomPacketHandler(this));
    }
    
    private void registerManagers() {
        this.alertsManager = new AlertsManager(this);
        this.banWaveManager = new BanWaveManager(this);
        this.playerDataManager = new PlayerDataManager(this);
        this.logManager = new LogManager();
    }
    
    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerListener(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new BanWaveListener(this), (Plugin)this);
    }
    
    private void registerCommands() {
    }
    
    private void registerRunnables() {
    }
    
    public PlayerDataManager getPlayerDataManager() {
        return this.playerDataManager;
    }
    
    public BanWaveManager getBanWaveManager() {
        return this.banWaveManager;
    }
    
    public AlertsManager getAlertsManager() {
        return this.alertsManager;
    }
    
    public LogManager getLogManager() {
        return this.logManager;
    }
    
    public static AntiGamingChair getInstance() {
        return AntiGamingChair.instance;
    }
    
    public double getRangeVl() {
        return this.rangeVl;
    }
    
    public double getRangeVlLow() {
        return this.rangeVlLow;
    }
    
    public void setRangeVl(final double rangeVl) {
        this.rangeVl = rangeVl;
    }
    
    public void setRangeVlLow(final double rangeVlLow) {
        this.rangeVlLow = rangeVlLow;
    }
}
