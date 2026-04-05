/* 这个个类负责外部调用UpdeateChecker来检查更新
  制作人·:huangzuan
  你喜欢这个项目吗？*/


package com.huangzuan.autoupdate;

public class Updater {

    private final String appName;
    private final String currentVersion;
    private final String githubApi;

    public Updater(String appName, String currentVersion, String repoName) {
        this.appName = appName;         // appName 应用名称，用于显示和 GitHub 请求的 User-Agent
        this.currentVersion = currentVersion;       // currentVersion 当前程序版本号，用于和 GitHub 最新版本比较
        this.githubApi = "https://api.github.com/repos/huangzuan/" + repoName + "/releases/latest";
        // githubApi 是 GitHub API 的 URL，用于获取最新 Release 信息
        // repoName 是 GitHub 仓库的名字，格式通常是 "username/repo"，例如 "huangzuan/NBTEditor"
    }

    public void checkUpdate() {
        UpdateChecker checker = new UpdateChecker(appName, currentVersion, githubApi);
        checker.checkOnStart();
    }
}