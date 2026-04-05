/*这个类负责下载更新并执行更新脚本
  制作人·:huangzuan
所以*/


package com.huangzuan.autoupdate;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.nio.file.*;

/**
 * 下载更新并执行更新脚本
 */
public class AutoUpdater {

    public static void downloadAndUpdate(String zipUrl) {
        try {
            // 临时下载 zip 文件
            Path tempZip = Files.createTempFile("update_temp", ".zip");
            try (InputStream in = new URL(zipUrl).openStream()) {
                Files.copy(in, tempZip, StandardCopyOption.REPLACE_EXISTING);
            }

            String os = System.getProperty("os.name").toLowerCase();

            // 当前运行的 Jar 路径
            String jarPath = new File(
                    AutoUpdater.class.getProtectionDomain()
                            .getCodeSource().getLocation().toURI()
            ).getPath();

            // 临时脚本
            Path script = Files.createTempFile("update", os.contains("win") ? ".bat" : ".sh");

            try (BufferedWriter writer = Files.newBufferedWriter(script)) {
                if (os.contains("win")) {
                    writer.write("@echo off\n");
                    writer.write("timeout /t 2\n");
                    writer.write("powershell -Command \"Expand-Archive -Force '" + tempZip + "' .\"\n");
                    writer.write("start java -jar \"" + jarPath + "\"\n");
                } else {
                    writer.write("#!/bin/sh\n");
                    writer.write("sleep 2\n");
                    writer.write("unzip -o \"" + tempZip + "\" -d .\n");
                    writer.write("chmod +x \"" + jarPath + "\"\n");
                    writer.write("java -jar \"" + jarPath + "\" &\n");
                }
            }

            script.toFile().setExecutable(true);

            // 执行更新脚本
            new ProcessBuilder(script.toAbsolutePath().toString()).start();

            // 退出当前程序
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "自动更新失败！");
        }
    }
}