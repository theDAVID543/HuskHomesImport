package the.huskhomesimport;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class HuskHomesImport extends JavaPlugin {
    public CommandManager commandManager;
    public static JavaPlugin instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        commandManager = new CommandManager();
        Bukkit.getPluginCommand("huskhomesimport").setExecutor(commandManager);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
