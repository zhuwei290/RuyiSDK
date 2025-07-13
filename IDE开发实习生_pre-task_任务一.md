
# IDE开发实习生 pre-task 任务一

## RuyiSDK简介

RuyiSDK 是一个由 PLCT Lab 所启动的开源项目，该项目旨在为 RISC-V 开发者提供一个便捷、完善的开发环境。其提供了相关最新的硬件信息、软件支持，例如在支持的设备中有提供相关支持硬件情况；软件层面提供了镜像（如 RevyOS）、工具链、包管理器等。

其最终目标是希望为 RISC-V 开发者提供一个完善、便捷的开发环境，使得 RISC-V 成为主流架构，以及建设并运营一个完善的社区以便开发者交流。最终希望 RuyiSDK 可以走向国际化，为全球的 RISC-V 开发者提供开发的便捷。

---

## 1. 完成 ruyi 安装，编译 coremark 并在 qemu 上运行

### （1）RuyiSDK安装

```bash
$ uname -m   # 检查系统架构并下载对应的二进制

$ wget https://mirror.iscas.ac.cn/ruyisdk/ruyi/tags/0.36.0/ruyi-0.36.0.amd64  # x86_64 架构

$ chmod +x ./ruyi.amd64

$ sudo cp -v ruyi.amd64 /usr/local/bin/ruyi

$ ruyi version   # 验证安装

$ ruyi --help  # 帮助信息
```

---

### （2）编译 coremark 并在 qemu 上运行

```bash
ruyi install llvm-upstream gnu-plct qemu-user-riscv-upstream

ruyi venv -t llvm-upstream --sysroot-from gnu-plct -e qemu-user-riscv-upstream generic venv
. venv/bin/ruyi-activate
```

虚拟环境说明：

- 工具链：LLVM 上游版本
- 系统根目录：从 GNU 工具链提取
- 模拟器：QEMU 用户模式 RISC-V

退出虚拟环境：

```bash
ruyi-deactivate
```

#### 解包 coremark 并编译：

```bash
mkdir coremark
cd coremark
ruyi extract coremark

sed -i 's/\bgcc\b/riscv64-unknown-linux-gnu-gcc/g' linux64/core_portme.mak
make PORT_DIR=linux64 link
```

#### 使用 QEMU 运行：

```bash
ruyi-qemu coremark.exe
```

---

## 2. 下载 RuyiSDK IDE，为 milkv duo 编译 Helloworld

### RuyiSDK IDE简介

RuyiSDK IDE 是一款基于 Eclipse 开发的图形化集成开发环境，面向 RISC-V 开发者，计划集成主流开发板 SDK，使开发更加便捷。

- 下载地址：https://fast-mirror.isrc.ac.cn/ruyisdk/ide/0.0.3/

#### 安装工具链

```bash
ruyi install gnu-milkv-milkv-duo-bin
```

#### 创建虚拟环境：

```bash
ruyi venv -t gnu-milkv-milkv-duo-musl-bin milkv-duo ./venv-milkvduo
```

> 在 IDE 中执行构建

---

## 3. 下载 RuyiSDK IDE 插件，使用当前的插件功能

### （1）包资源管理

- 一键下载资源包，无需命令行输入

### （2）欢迎页面

- 有时候会加载不出来

### （3）RuyiSDK 配置插件

- 便于开发板配置

---

## 4. 我的感受和需要改进的点

RuyiSDK 自 2023 年筹备，目前开发约 2 年，基本功能和环境已初步完善，但仍存在不足。

我对 RuyiSDK IDE 的看法：

1. 插件安装不便，不能直接从 Eclipse 插件市场获取。
2. 基于 Eclipse，界面复杂，新手难以上手。希望未来 IDE 类似 VSCode 简洁直观。
