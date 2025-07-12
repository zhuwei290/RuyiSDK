# CMake Project Importer - Eclipse 插件

## 功能描述

这是一个 Eclipse 插件，用于自动识别并导入 CMake 项目。

### 主要功能

1. **自动识别 CMake 项目**：扫描目录，检查是否存在 `CMakeLists.txt` 文件
2. **支持多种输入方式**：
   - 本地文件路径
   - GitHub 仓库地址（自动克隆）
3. **智能导入**：识别为 CMake 项目后，自动导入到 Eclipse 工作区
4. **错误处理**：非 CMake 项目时弹出警告并终止导入

### 使用方法

1. **安装插件**：
   - 将 `org.ruyisdk.cmakeimporter_1.0.0.jar` 放到 Eclipse 的 `dropins` 目录
   - 重启 Eclipse

2. **使用插件**：
   - 在 Eclipse 中选择 **File > Import...**
   - 在导入向导中找到 **CMake Project Importer**
   - 输入本地路径或 GitHub 地址
   - 点击 Finish 完成导入

### 依赖要求

- Eclipse IDE for RCP and RAP Developers
- CDT (C/C++ Development Tools) 插件
- JGit 插件

### 打包方法

1. **使用 Eclipse PDE**：
   - 导入项目到 Eclipse
   - 右键项目 → Export... → Deployable plug-ins and fragments

2. **使用批处理脚本**：
   - 运行 `build.bat` 自动打包

### 项目结构

```
org.ruyisdk.cmakeimporter_1.0.0/
├── .project                    # Eclipse 项目描述
├── build.properties           # 构建配置
├── plugin.xml                 # 插件配置
├── META-INF/
│   └── MANIFEST.MF           # 插件清单
├── src/
│   └── org/ruyisdk/cmakeimporter/
│       ├── Activator.java     # 插件激活器
│       ├── core/              # 核心功能
│       │   ├── CMakeProjectScanner.java
│       │   ├── GitUtils.java
│       │   └── CMakeProjectImporter.java
│       └── wizard/            # 导入向导
│           ├── CMakeProjectImportWizard.java
│           └── CMakeProjectImportWizardPage.java
└── build.bat                  # 打包脚本
```

### 版本信息

- 版本：1.0.0
- 作者：RuyiSDK
- 许可证：开源 