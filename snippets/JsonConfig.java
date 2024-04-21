
//
// like the built in config spigot system, but instead of yaml its json
// by aabss
// requires spigotmc/papermc api, gson and jetbrains annotations
//

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class JsonConfig {
    public JsonConfig(JavaPlugin plugin){
        this.plugin = plugin;
    }

    private File configFile = null;
    private JsonObject config = null;
    private final JavaPlugin plugin;

    public JsonObject getConfig(){
        return config;
    }

    public void saveDefaultConfig(){
        if (configFile == null || !configFile.exists()){
            saveResource("config.json", false);
        }
        reloadConfig();
    }

    public void saveConfig(){
        if (configFile.exists()) {
            try (FileWriter writer = new FileWriter(configFile)) {
                writer.write(config.toString());
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }

    public void reloadConfig(){
        try {
            Scanner scanner = new Scanner(new File(plugin.getDataFolder(), "config.json"));
            StringBuilder json = new StringBuilder();
            while (scanner.hasNextLine()){
                json.append(scanner.nextLine());
            }
            config = JsonParser.parseString(json.toString()).getAsJsonObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveResource(@NotNull String resourcePath, boolean replace) {
        if (resourcePath.isEmpty()) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        resourcePath = resourcePath.replace('\\', '/');
        InputStream in = getResource(resourcePath);
        if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found");
        }
        File dataFolder = plugin.getDataFolder();
        File outFile = new File(dataFolder, resourcePath);
        int lastIndex = resourcePath.lastIndexOf('/');
        File outDir = new File(dataFolder, resourcePath.substring(0, Math.max(lastIndex, 0)));

        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        try {
            if (!outFile.exists() || replace) {
                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
                configFile = outFile;
            }
        } catch (IOException ignored) {}
    }

    @Nullable
    public InputStream getResource(@NotNull String filename) {
        try {
            URL url = this.getClass().getClassLoader().getResource(filename);

            if (url == null) {
                return null;
            }

            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException ex) {
            return null;
        }
    }
}
