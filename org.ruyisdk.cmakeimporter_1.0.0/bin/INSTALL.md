# CMake Project Importer 安装指南

## 快速安装

### 方法一：直接安装（推荐）

1. **下载插件**：
   - 使用生成的 `org.ruyisdk.cmakeimporter_1.0.0.jar` 文件

2. **安装到 Eclipse**：
   - 找到你的 Eclipse 安装目录
   - 将 JAR 文件复制到 `dropins` 目录
   - 例如：`C:\eclipse\dropins\org.ruyisdk.cmakeimporter_1.0.0.jar`

3. **重启 Eclipse**：
   - 完全关闭 Eclipse
   - 重新启动 Eclipse

4. **验证安装**：
   - 打开 Eclipse
   - 选择 **File > Import...**
   - 在导入向导中应该能看到 **CMake Project Importer**

### 方法二：通过 Eclipse 安装

1. **打开 Eclipse**
2. **选择 Help > Install New Software...**
3. **点击 Add... > Archive...**
4. **选择 JAR 文件**：`org.ruyisdk.cmakeimporter_1.0.0.jar`
5. **点击 OK，然后 Next > Finish**
6. **重启 Eclipse**

## 使用说明

### 导入 CMake 项目

1. **启动导入向导**：
   - 在 Eclipse 中选择 **File > Import...**
   - 找到并选择 **CMake Project Importer**

2. **输入项目路径**：
   - **本地路径**：输入包含 CMakeLists.txt 的目录路径
   - **GitHub 地址**：输入 GitHub 仓库 URL（如：https://github.com/user/repo.git）

3. **完成导入**：
   - 点击 **Finish**
   - 插件会自动识别是否为 CMake 项目
   - 成功：项目会出现在 Project Explorer 中
   - 失败：会弹出警告对话框

### 功能特性

- ✅ **自动识别**：检查 CMakeLists.txt 文件
- ✅ **Git 支持**：自动克隆 GitHub 仓库
- ✅ **错误处理**：非 CMake 项目时弹出警告
- ✅ **Eclipse 集成**：项目在 Project Explorer 中可见

## 依赖要求

确保你的 Eclipse 安装了以下插件：

1. **Eclipse IDE for RCP and RAP Developers**（推荐）
2. **CDT (C/C++ Development Tools)**
3. **JGit**

### 安装依赖

如果缺少依赖，请按以下步骤安装：

1. **Help > Install New Software...**
2. **Work with**: `http://download.eclipse.org/releases/latest`
3. **搜索并安装**：
   - `C/C++ Development Tools`
   - `Eclipse Git Team Provider`

## 故障排除

### 插件未显示

1. **检查 dropins 目录**：确保 JAR 文件在正确位置
2. **重启 Eclipse**：完全关闭后重新启动
3. **检查错误日志**：Window > Show View > Error Log

### 导入失败

1. **检查路径**：确保路径正确且可访问
2. **检查网络**：GitHub 地址需要网络连接
3. **检查权限**：确保有读取目录的权限

### 依赖错误

1. **安装 CDT**：确保安装了 C/C++ Development Tools
2. **安装 JGit**：确保安装了 Eclipse Git Team Provider
3. **检查版本**：确保 Eclipse 版本兼容

## 技术支持

如果遇到问题，请检查：

1. Eclipse 版本（推荐 2023-12 或更新版本）
2. 已安装的插件列表
3. Error Log 中的错误信息

---

**版本**: 1.0.0  
**作者**: RuyiSDK  
**许可证**: 开源 