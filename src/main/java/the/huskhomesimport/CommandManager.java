package the.huskhomesimport;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandManager implements CommandExecutor{
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args){
		if(!(sender instanceof ConsoleCommandSender)){
			sender.sendMessage("This command can only be executed by console");
			return false;
		}
		ImportThread importThread = new ImportThread();
		importThread.runTaskAsynchronously(HuskHomesImport.instance);
		return true;
	}
}
