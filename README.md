# Copy Text Anywhere

![Build](https://github.com/peterzparker/copy-text-anywhere/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)

**Copy Text Anywhere** 是一个 IntelliJ IDEA 插件，在 IDE 几乎所有右键菜单中添加「复制文本」(Copy Text) 选项，让你可以轻松地从任何组件中复制文字到剪贴板。

## 功能特性

- **编辑器右键** — 复制选中文本；无选中时自动复制当前行内容
- **项目视图 / 结构视图** — 右键复制节点文字
- **控制台** — 复制控制台输出的文字内容
- **差异查看器 (Diff Viewer)** — 复制对比区域文本
- **VCS 日志 (Git History)** — 复制提交信息、变更列表等
- **VCS 变更文件列表 / 文件历史** — 复制文件名或路径
- **变更视图** — 复制变更列表中的信息
- **提交对话框** — 复制提交注释等内容
- **其他 Swing 组件** — 支持文本框 (`JTextComponent`)、标签 (`JLabel`)、按钮 (`AbstractButton`)、树 (`JTree`)、表格 (`JTable`)、列表 (`JList`) 等多种组件

**使用方法：** 在任何显示文字的地方右键，选择 **Copy Text** (或 **复制文本**) 即可将文字复制到剪贴板。

## 安装

- **IDE 插件市场安装**

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > 搜索 "Copy Text Anywhere" > <kbd>Install</kbd>

- **手动安装**

  下载 [latest release](https://github.com/peterzparker/copy-text-anywhere/releases/latest) 并在 IDE 中通过
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd> 安装

## 兼容性

- 基于 IntelliJ Platform，兼容所有基于 IntelliJ 的 IDE（IntelliJ IDEA、PyCharm、WebStorm、GoLand 等）
- 最低支持版本：2025.2.6.2

## 构建

本项目使用 [IntelliJ Platform Gradle Plugin](https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html) 构建。

```bash
./gradlew build
```

## 技术栈

- Java
- IntelliJ Platform SDK
- IntelliJ Platform Gradle Plugin
- Swing

## 许可证

本项目基于 [MIT License](LICENSE) 开源。
