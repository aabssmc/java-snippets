
//
// returns a head of a player as a string
// by aabss
// requires spigotmc/papermc api
//

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public final class ChatHead implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        sender.sendMessage(sendHead(sender.getName(), true));
        return true;
    }
}

/*

for a player just use `sendHead(player.getName(), helm)`

*/
