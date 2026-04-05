/* 这个个类负责在应用启动时检查 GitHub 上的最新 Release 版本,并提示用户更新
  制作人·:huangzuan
  呃,这行该干啥*/


package com.huangzuan.autoupdate;

import javax.swing.*;
import java.io.*;
import java.net.*;
import org.json.JSONArray;
import org.json.JSONObject;

 // 检查 GitHub 最新 Release 并提示更新
 
public class UpdateChecker {

    private final String appName;
    private final String currentVersion;
    private final String githubApi;

    public UpdateChecker(String appName, String currentVersion, String githubApi) {
    this.appName = appName;                  // 你的应用名称，用于显示和 GitHub 请求的 User-Agent
    this.currentVersion = currentVersion;    // 当前程序版本号，用于和 GitHub 最新版本比较
    this.githubApi = githubApi;              // GitHub API 的 URL，用于获取最新 Release 信息，例如 "https://api.github.com/huangzuan/NBTEditor/releases/latest"
}

    public void checkOnStart() {
        new Thread(() -> {
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(githubApi).openConnection();
                conn.setRequestProperty("User-Agent", appName);
                conn.setRequestProperty("Accept", "application/vnd.github+json");
                conn.connect();

                if (conn.getResponseCode() != 200) return;

                StringBuilder sb = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) sb.append(line);
                }

                JSONObject release = new JSONObject(sb.toString());
                String latestTag = release.getString("tag_name");

                // 如果版本不一样，提示更新
                if (!latestTag.equalsIgnoreCase(currentVersion)) {
                    JSONArray assets = release.getJSONArray("assets");
                    String zipUrl = null;
                    for (int i = 0; i < assets.length(); i++) {
                        JSONObject asset = assets.getJSONObject(i);
                        String name = asset.getString("name");
                        if (name.endsWith(".zip")) {// 找到 zip 包下载链接
                         // 如果没有 zip 包,你可以修改你的发布方式
                            zipUrl = asset.getString("browser_download_url");
                            break;
                        }
                    }

                    if (zipUrl != null) {
                        String finalZipUrl = zipUrl;
                        SwingUtilities.invokeLater(() -> {
                            int option = JOptionPane.showConfirmDialog(null,
                                    "发现新版本 " + latestTag + "，是否更新？",
                                    "更新提示", JOptionPane.YES_NO_OPTION);
                            if (option == JOptionPane.YES_OPTION) {
                                AutoUpdater.downloadAndUpdate(finalZipUrl);
                            }
                        });
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}