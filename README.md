# AutoUpdateModule

一个 **Java 自动更新模块**，可以在程序启动时检查 GitHub 最新 Release，并弹窗提示用户更新。
适用于桌面 Java 应用（Swing/JavaFX），支持 Windows、Mac、Linux。

---

## 使用方法

### 1. 集成模块

1. 将模块源码或 Jar 包加入到你项目中
2. 如果使用 Gradle，可以在 build.gradle 添加依赖：

```gradle
dependencies {
    implementation files('libs/AutoUpdateModule-0.1.jar')
}
```

---

### 2. 在主程序里调用

```java
import com.huangzuan.autoupdate.Updater;

public class Main {
    public static void main(String[] args) {
        // 创建 Updater 对象
        Updater updater = new Updater(
                "Your App Name",          // 应用名称
                "0.1",                   // 当前版本号
                "yourname/your-repo"     // GitHub 仓库，例如 "huangzuan/NBTEditor"
        );

        // 检查更新
        updater.checkUpdate();

        // 主程序逻辑
        System.out.println("Application started");
    }
}
```

---

## 注意

* GitHub 仓库名必须精确
* Release 必须包含 ZIP 文件，自动更新会下载它

---

### 3. GitHub Release 要求

1. 登录 GitHub → 打开项目 → Releases → Draft a new release
2. 填写版本号（tag），例如 v0.2
3. 上传你的可执行文件或项目 ZIP，例如 NBTEditor.zip
4. 发布 Release

模块会自动：

* 查询最新 Release
* 弹窗提示用户更新
* 下载 ZIP
* 覆盖当前程序并重启

---

### 4. 测试流程

1. 确保主程序版本号低于最新 GitHub Release
2. 运行主程序
3. 弹出更新提示框
4. 点击“是” → 下载 ZIP → 执行覆盖更新 → 自动重启程序
5. 确认程序已更新到最新版本

---

### 5. 小提示

* 测试时建议用副本程序，避免覆盖正式项目
* 支持 Windows、Mac、Linux
* GitHub API 请求限制：60 次/小时
* 可以修改 Updater 构造函数里的 appName，让提示框显示你的程序名



# AutoUpdateModule

A Java auto-update module that checks the latest GitHub Release when the application starts and prompts the user to update.
Suitable for desktop Java applications (Swing/JavaFX). Supports Windows, macOS, and Linux.

---

## Usage

### 1. Integrate the module

1. Add the module source code or Jar file into your project
2. If using Gradle, add the dependency in build.gradle:

dependencies {
    implementation files('libs/AutoUpdateModule-0.1.jar')
}

---

### 2. Call in your main program

import com.huangzuan.autoupdate.Updater;

public class Main {
    public static void main(String[] args) {
        // 创建 Updater 对象
        Updater updater = new Updater(
                "Your App Name",          //Application Name
                "0.1",               //Current version number    
                "yourname/your-repo"     //GitHub repositories, such as "huangzuan/NBTEditor"
        );

        // 检查更新
        updater.checkUpdate();

        // 主程序逻辑
        System.out.println("Application started");
    }
}

---

## Notes

- GitHub repository name must be exact
- The Release must contain a ZIP file

---

### 3. GitHub Release requirements

1. Go to your GitHub project
2. Open Releases → Draft a new release
3. Set a version tag (e.g. v0.2)
4. Upload your ZIP file
5. Publish the release

The module will:
- Check latest release
- Show update dialog
- Download ZIP
- Replace files
- Restart application

---

### 4. Testing

1. Make sure local version is lower than latest release
2. Run program
3. Update dialog appears
4. Click Yes → update
5. Confirm updated

---

### 5. Tips

- Test with a copy to avoid overwriting
- Works on Windows, macOS, Linux
- GitHub API limit: 60 requests/hour

