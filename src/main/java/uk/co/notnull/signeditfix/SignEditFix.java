package uk.co.notnull.signeditfix;

import com.destroystokyo.paper.MaterialSetTag;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

public final class SignEditFix extends JavaPlugin implements Listener {
	private RegionQuery query;

	@Override
	public void onEnable() {
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		query = container.createQuery();

		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler()
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);

		if(event.getClickedBlock() == null) {
			return;
		}

		if(!MaterialSetTag.SIGNS.isTagged(event.getClickedBlock().getType())) {
			return;
		}

		if(event.getHand() != EquipmentSlot.HAND) {
			return;
		}

		if(!MaterialSetTag.SIGNS.isTagged(event.getMaterial())) {
			return;
		}

		if(!query.testState(BukkitAdapter.adapt(event.getClickedBlock().getLocation()), localPlayer, Flags.BUILD)) {
			event.setCancelled(true);
			player.sendMessage(LegacyComponentSerializer.legacyAmpersand()
									   .deserialize("&cYou cannot edit this sign"));
		}
	}
}
