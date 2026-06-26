package me.combat;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CombatPlugin extends JavaPlugin {
    private CombatManager combatManager;

    @Override
    public void onEnable() {
        this.combatManager = new CombatManager(this);
        
        // Register events
        getServer().getPluginManager().registerEvents(new CombatListener(combatManager), this);
        
        // Task to update BossBars every second
        new BukkitRunnable() {
            @Override
            public void run() {
                combatManager.updateTimers();
            }
        }.runTaskTimer(this, 0L, 20L);
        
        getLogger().info("CombatCooldown plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("CombatCooldown plugin disabled!");
    }
}
