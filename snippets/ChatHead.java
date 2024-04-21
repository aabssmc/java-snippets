
//
// returns a head of a player as a string
// by aabss
// requires spigotmc/papermc api
//

import net.md_5.bungee.api.ChatColor;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ChatHead {
  
    @SuppressWarnings("deprecation")
    public static String sendHead(String name, boolean helm) {
        try {
            BufferedImage img = ImageIO.read(new URL("https://minotar.net/" + (helm ? "helm" : "avatar") + "/" + name + "/8.png"));
            String[] result = new String[8];
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    ChatColor c = ChatColor.of(new Color(img.getRGB(x, y)));
                    if (result[y] == null) result[y] = "";
                    result[y] += (c.toString() + "\u2588").replaceAll("\\?", "");
                }
            }
            return String.join("\n", result);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}

/*

for a player just use `sendHead(player.getName(), helm)`

*/
