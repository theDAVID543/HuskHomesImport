package the.huskhomesimport;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Containers.CMIUser;
import com.Zrips.CMI.Modules.Homes.CmiHome;
import net.william278.huskhomes.BukkitHuskHomes;
import net.william278.huskhomes.HuskHomes;
import net.william278.huskhomes.api.HuskHomesAPI;
import net.william278.huskhomes.position.Position;
import net.william278.huskhomes.position.World;
import net.william278.huskhomes.user.SavedUser;
import net.william278.huskhomes.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedHashMap;

public class ImportThread extends BukkitRunnable{
	@Override
	public void run(){
		HuskHomesAPI huskHomesAPI = HuskHomesAPI.getInstance();
		OfflinePlayer[] offlinePlayers = Bukkit.getOfflinePlayers();
		for(OfflinePlayer offlinePlayer : offlinePlayers){
			new BukkitRunnable(){
				@Override
				public void run(){
					Bukkit.getLogger().info("importing " + offlinePlayer.getName() + " : " + offlinePlayer.getUniqueId());
					CMIUser user = CMI.getInstance().getPlayerManager().getUser(offlinePlayer);
					LinkedHashMap<String, CmiHome> homes = user.getHomes();
					if(homes != null){
						for(String homeName : homes.keySet()){
							CmiHome home = homes.get(homeName);
							Location bukkitLocation = home.getLoc().getBukkitLoc();
							Bukkit.getLogger().info(homeName + " : " + bukkitLocation);
							User huskUser = User.of(offlinePlayer.getUniqueId(), offlinePlayer.getName());
							SavedUser savedUser = new SavedUser(huskUser, homes.size(), false);
							HuskHomes huskHomes = JavaPlugin.getPlugin(BukkitHuskHomes.class);
							huskHomes.getDatabase().ensureUser(huskUser);
							huskHomesAPI.saveUserData(savedUser);
							net.william278.huskhomes.position.Location huskLocation = net.william278.huskhomes.position.Location.at(bukkitLocation.getX(), bukkitLocation.getY(), bukkitLocation.getZ(), bukkitLocation.getYaw(), bukkitLocation.getPitch(), World.from(bukkitLocation.getWorld().getName(), bukkitLocation.getWorld().getUID()));
							Position position = Position.at(huskLocation, huskHomesAPI.getServer());
							huskHomesAPI.createHome(huskUser, homeName, position);
							huskHomesAPI.saveUserData(savedUser);
							Bukkit.getLogger().info("created user and import " + homeName + " succeed");
						}
					}else{
						Bukkit.getLogger().info("No homes found");
					}
				}
			}.runTaskLater(HuskHomesImport.instance, 1L);
		}
	}
}
