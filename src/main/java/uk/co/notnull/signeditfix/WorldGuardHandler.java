package uk.co.notnull.signeditfix;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class WorldGuardHandler {
	private final WorldGuardPlugin worldGuard;
	private final boolean wgEnabled;
	private final RegionContainer container;
	private final RegionQuery query;

	public WorldGuardHandler(JavaPlugin plugin) {
		worldGuard = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
		wgEnabled = worldGuard != null;
		container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		query = container.createQuery();
	}

	public boolean checkPermission(Block block, Player player) {
		if(!wgEnabled) {
			return true;
		}

		LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);

		return query.testState(BukkitAdapter.adapt(block.getLocation()), localPlayer, Flags.BUILD);
	}
}
