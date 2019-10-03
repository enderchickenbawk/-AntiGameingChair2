package club.mineman.antigamingchair.listener;

import club.mineman.antigamingchair.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import io.netty.buffer.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;
import org.bukkit.entity.*;
import club.mineman.antigamingchair.data.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import club.mineman.antigamingchair.util.*;
import club.mineman.antigamingchair.check.*;
import club.mineman.antigamingchair.event.*;
import net.minecraft.server.v1_8_R3.*;
import java.text.*;
import org.bukkit.*;
import java.util.*;
import club.mineman.antigamingchair.event.player.*;
import org.bukkit.command.*;

public class PlayerListener implements Listener
{
    private final AntiGamingChair plugin;
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        this.plugin.getPlayerDataManager().addPlayerData(event.getPlayer());
        final PlayerConnection playerConnection = ((CraftPlayer)event.getPlayer()).getHandle().playerConnection;
        final PacketPlayOutCustomPayload packetPlayOutCustomPayload = new PacketPlayOutCustomPayload("REGISTER", new PacketDataSerializer(Unpooled.wrappedBuffer("CB-Client".getBytes())));
        final PacketPlayOutCustomPayload packetPlayOutCustomPayload2 = new PacketPlayOutCustomPayload("REGISTER", new PacketDataSerializer(Unpooled.wrappedBuffer("CC".getBytes())));
        final PlayerConnection playerConnection2;
        final Packet packet;
        final Packet packet2;
        this.plugin.getServer().getScheduler().runTaskLater((Plugin)this.plugin, () -> {
            playerConnection2.sendPacket(packet);
            new PacketPlayOutCustomPayload("REGISTER", new PacketDataSerializer(Unpooled.wrappedBuffer("CC".getBytes())));
            playerConnection2.sendPacket(packet2);
        }, 10L);
    }
    
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        if (this.plugin.getAlertsManager().hasAlertsToggled(event.getPlayer())) {
            this.plugin.getAlertsManager().toggleAlerts(event.getPlayer());
        }
        this.plugin.getPlayerDataManager().removePlayerData(event.getPlayer());
    }
    
    @EventHandler
    public void onTeleport(final PlayerTeleportEvent event) {
        final Player player = event.getPlayer();
        final PlayerData playerData = this.plugin.getPlayerDataManager().getPlayerData(player);
        if (playerData != null) {
            playerData.setLastTeleportTime(System.currentTimeMillis());
            playerData.setSendingVape(true);
        }
    }
    
    @EventHandler
    public void onDeath(final PlayerDeathEvent event) {
        final Player player = event.getEntity();
        final PlayerData playerData = this.plugin.getPlayerDataManager().getPlayerData(player);
        if (playerData != null) {
            playerData.setInventoryOpen(false);
        }
    }
    
    @EventHandler
    public void onPlayerChangedWorld(final PlayerChangedWorldEvent event) {
        final Player player = event.getPlayer();
        final PlayerData playerData = this.plugin.getPlayerDataManager().getPlayerData(player);
        if (playerData != null) {
            playerData.setInventoryOpen(false);
        }
    }
    
    @EventHandler
    public void onPlayerUpdatePosition(final PlayerUpdatePositionEvent event) {
        final Player player = event.getPlayer();
        if (player.getAllowFlight()) {
            return;
        }
        final PlayerData playerData = this.plugin.getPlayerDataManager().getPlayerData(player);
        if (playerData == null) {
            return;
        }
        playerData.setOnGround(BlockUtil.isOnGround(event.getTo(), 0) || BlockUtil.isOnGround(event.getTo(), 1));
        if (playerData.isOnGround()) {
            playerData.setLastGroundY(event.getTo().getY());
        }
        playerData.setInLiquid(BlockUtil.isOnLiquid(event.getTo(), 0) || BlockUtil.isOnLiquid(event.getTo(), 1));
        playerData.setInWeb(BlockUtil.isOnWeb(event.getTo(), 0));
        playerData.setOnIce(BlockUtil.isOnIce(event.getTo(), 1) || BlockUtil.isOnIce(event.getTo(), 2));
        playerData.setOnStairs(BlockUtil.isOnStairs(event.getTo(), 0) || BlockUtil.isOnStairs(event.getTo(), 1));
        playerData.setUnderBlock(BlockUtil.isOnGround(event.getTo(), -2));
        if (event.getTo().getY() != event.getFrom().getY() && playerData.getVelocityV() > 0) {
            playerData.setVelocityV(playerData.getVelocityV() - 1);
        }
        if (Math.hypot(event.getTo().getX() - event.getFrom().getX(), event.getTo().getZ() - event.getFrom().getZ()) > 0.0 && playerData.getVelocityH() > 0) {
            playerData.setVelocityH(playerData.getVelocityH() - 1);
        }
        Class<? extends ICheck>[] checks;
        for (int length = (checks = PlayerData.CHECKS).length, i = 0; i < length; ++i) {
            final Class<? extends ICheck> checkClass = checks[i];
            final ICheck check = (ICheck)playerData.getCheck(checkClass);
            if (check.getType() == PlayerUpdatePositionEvent.class) {
                check.handleCheck(player, event);
            }
        }
        if (playerData.getVelocityY() > 0.0 && event.getTo().getY() > event.getFrom().getY()) {
            playerData.setVelocityY(0.0);
        }
    }
    
    @EventHandler
    public void onPlayerUpdateRotation(final PlayerUpdateRotationEvent event) {
        final Player player = event.getPlayer();
        if (player.getAllowFlight()) {
            return;
        }
        final PlayerData playerData = this.plugin.getPlayerDataManager().getPlayerData(player);
        if (playerData == null) {
            return;
        }
        Class<? extends ICheck>[] checks;
        for (int length = (checks = PlayerData.CHECKS).length, i = 0; i < length; ++i) {
            final Class<? extends ICheck> checkClass = checks[i];
            final ICheck check = (ICheck)playerData.getCheck(checkClass);
            if (check.getType() == PlayerUpdateRotationEvent.class) {
                check.handleCheck(player, event);
            }
        }
    }
    
    @EventHandler
    public void onPlayerBanWave(final PlayerBanWaveEvent event) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        club/mineman/antigamingchair/listener/PlayerListener.plugin:Lclub/mineman/antigamingchair/AntiGamingChair;
        //     4: invokevirtual   club/mineman/antigamingchair/AntiGamingChair.isAntiCheatEnabled:()Z
        //     7: ifne            24
        //    10: aload_1         /* event */
        //    11: invokevirtual   club/mineman/antigamingchair/event/player/PlayerBanWaveEvent.getReason:()Ljava/lang/String;
        //    14: ldc_w           "Manual"
        //    17: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    20: ifne            24
        //    23: return         
        //    24: aload_1         /* event */
        //    25: invokevirtual   club/mineman/antigamingchair/event/player/PlayerBanWaveEvent.getPlayer:()Lorg/bukkit/entity/Player;
        //    28: astore_2        /* player */
        //    29: aload_2         /* player */
        //    30: ifnonnull       34
        //    33: return         
        //    34: aload_0         /* this */
        //    35: getfield        club/mineman/antigamingchair/listener/PlayerListener.plugin:Lclub/mineman/antigamingchair/AntiGamingChair;
        //    38: invokevirtual   club/mineman/antigamingchair/AntiGamingChair.getLogger:()invokevirtual  !!! ERROR
        //    41: new             Ljava/lang/StringBuilder;
        //    44: dup            
        //    45: ldc_w           "Added "
        //    48: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //    51: aload_2         /* player */
        //    52: invokeinterface org/bukkit/entity/Player.getName:()Ljava/lang/String;
        //    57: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    60: ldc_w           " to the ban wave."
        //    63: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    66: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    69: invokevirtual   invokevirtual  !!! ERROR
        //    72: return         
        //    StackMapTable: 00 02 18 FC 00 09 07 00 9F
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Invalid BootstrapMethods attribute entry: 2 additional arguments required for method java/lang/invoke/StringConcatFactory.makeConcatWithConstants, but only 1 specified.
        //     at com.strobel.assembler.ir.Error.invalidBootstrapMethodEntry(Error.java:244)
        //     at com.strobel.assembler.ir.MetadataReader.readAttributeCore(MetadataReader.java:267)
        //     at com.strobel.assembler.metadata.ClassFileReader.readAttributeCore(ClassFileReader.java:261)
        //     at com.strobel.assembler.ir.MetadataReader.inflateAttributes(MetadataReader.java:426)
        //     at com.strobel.assembler.metadata.ClassFileReader.visitAttributes(ClassFileReader.java:1134)
        //     at com.strobel.assembler.metadata.ClassFileReader.readClass(ClassFileReader.java:439)
        //     at com.strobel.assembler.metadata.ClassFileReader.readClass(ClassFileReader.java:377)
        //     at com.strobel.assembler.metadata.MetadataSystem.resolveType(MetadataSystem.java:129)
        //     at com.strobel.assembler.metadata.MetadataSystem.resolveCore(MetadataSystem.java:81)
        //     at com.strobel.assembler.metadata.MetadataResolver.resolve(MetadataResolver.java:104)
        //     at com.strobel.assembler.metadata.CoreMetadataFactory$UnresolvedType.resolve(CoreMetadataFactory.java:616)
        //     at com.strobel.assembler.metadata.MetadataResolver.resolve(MetadataResolver.java:128)
        //     at com.strobel.assembler.metadata.CoreMetadataFactory$UnresolvedType.resolve(CoreMetadataFactory.java:626)
        //     at com.strobel.assembler.metadata.MethodReference.resolve(MethodReference.java:177)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferCall(TypeAnalysis.java:2438)
        //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:1029)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:672)
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:655)
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:365)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:109)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.base/java.lang.Thread.run(Thread.java:835)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @EventHandler
    public void onPlayerAlert(final PlayerAlertEvent event) {
        if (!this.plugin.isAntiCheatEnabled()) {
            event.setCancelled(true);
            return;
        }
        final Player player = event.getPlayer();
        if (player == null) {
            return;
        }
        final PlayerData playerData = this.plugin.getPlayerDataManager().getPlayerData(player);
        if (playerData == null) {
            return;
        }
        final double tps = MinecraftServer.getServer().tps1.getAverage();
        String fixedTPS = new DecimalFormat(".##").format(tps);
        if (tps > 20.0) {
            fixedTPS = "20.0";
        }
        final String alert = String.valueOf(event.getAlert()) + ChatColor.LIGHT_PURPLE + " Ping " + playerData.getPing() + " ms. TPS " + fixedTPS + ".";
        final Set<UUID> playerUUIDs = new HashSet<UUID>(this.plugin.getAlertsManager().getAlertsToggled());
        playerUUIDs.addAll(playerData.getPlayersWatching());
        final PlayerAlertEvent.AlertType type = event.getAlertType();
        Bukkit.broadcastMessage(String.valueOf(event.getPlayer().getName()) + " | " + alert);
    }
    
    @EventHandler
    public void onPlayerBan(final PlayerBanEvent event) {
        if (!this.plugin.isAntiCheatEnabled()) {
            event.setCancelled(true);
            return;
        }
        final Player player = event.getPlayer();
        if (player == null) {
            return;
        }
        this.plugin.getServer().broadcastMessage(ChatColor.STRIKETHROUGH + "--------------------------------------------------\n" + ChatColor.RED + "\u2718 " + ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.DARK_PURPLE + " was banned by " + ChatColor.LIGHT_PURPLE + "AntiGamingChair" + ChatColor.DARK_PURPLE + " for cheating.\n" + ChatColor.RED + ChatColor.STRIKETHROUGH + "--------------------------------------------------\n");
        this.plugin.getServer().getScheduler().runTask((Plugin)this.plugin, () -> this.plugin.getServer().dispatchCommand((CommandSender)this.plugin.getServer().getConsoleSender(), "ban " + player.getName() + " Unfair Advantage -s"));
    }
    
    public PlayerListener(final AntiGamingChair plugin) {
        this.plugin = plugin;
    }
}
