package me.combat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Arrays;
import java.util.List;

public class CombatListener implements Listener {
    private final CombatManager combatManager;
    private final List<String> blockedCommands = Arrays.asList("/home", "/spawn", "/tpa", "/rtp");

    public CombatListener(CombatManager combatManager) {
        this.combatManager = combatManager;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player victim) {
            if (event.getDamager() instanceof Player attacker) {
                // Both are players, start combat for both
                combatManager.startCombat(victim);
                combatManager.startCombat(attacker);
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage().toLowerCase();

        if (combatManager.isInCombat(player)) {
            for (String cmd : blockedCommands) {
                if (message.startsWith(cmd)) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "Вы в бою! Команда " + cmd + " недоступна.");
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        combatManager.handleQuit(player);
    }
}
