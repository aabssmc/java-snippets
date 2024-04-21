//
// like the built in config spigot system, but instead of yaml its json
// by aabss
// requires spigotmc/papermc api, gson and jetbrains annotations
//

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class ExamplePlugin extends JavaPlugin implements CommandExecutor {

    private static JsonConfig config;

    @Override
    public void onEnable() {
        config = new JsonConfig(this);
        config.saveDefaultConfig();
        getCommand("testcommand").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length > 0){
            if (args[0].equals("reload")) {
                config.reloadConfig();
                sender.sendMessage("config reloaded");
            } else{
                sender.sendMessage("/testcommand [reload]");
            }
            return true;
        }
        sender.sendMessage(config.getConfig().get("message").getAsString());
        return true;
    }
}

/*
example json:
```json
{
  "message": "hi i am really cool!"
}```

put in a file called "config.json" in your resources folder.
*/
