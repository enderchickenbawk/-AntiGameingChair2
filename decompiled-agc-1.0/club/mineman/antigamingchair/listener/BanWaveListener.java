package club.mineman.antigamingchair.listener;

import club.mineman.antigamingchair.*;
import club.mineman.antigamingchair.event.*;
import org.bukkit.*;
import club.mineman.antigamingchair.runnable.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;

public class BanWaveListener implements Listener
{
    private final AntiGamingChair plugin;
    
    @EventHandler
    public void onBanWave(final BanWaveEvent e) {
        this.plugin.getServer().broadcastMessage(ChatColor.STRIKETHROUGH + "--------------------------------------------------\n" + ChatColor.RED + "\u2718 " + ChatColor.LIGHT_PURPLE + "AntiGamingChair has been ordered to commence a ban wave by " + ChatColor.DARK_PURPLE + ((e.getInstigator() == null) ? "CONSOLE" : e.getInstigator()) + ChatColor.LIGHT_PURPLE + ".\n" + ChatColor.RED + ChatColor.STRIKETHROUGH + "--------------------------------------------------\n");
        this.plugin.getBanWaveManager().setBanWaveStarted(true);
        this.plugin.getBanWaveManager().loadCheaters();
        new BanWaveRunnable(this.plugin).runTaskTimerAsynchronously((Plugin)this.plugin, 20L, 20L);
    }
    
    public BanWaveListener(final AntiGamingChair plugin) {
        this.plugin = plugin;
    }
}
