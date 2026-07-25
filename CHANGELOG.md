# Copy Text Anywhere Changelog

## [1.0.0] - 2026-07-26

### Added
- 全局右键「复制文本」(Copy Text) 功能，支持编辑器、项目视图、结构视图、控制台等多种场景
- 编辑器选中文本复制，无选中时自动复制当前行内容
- 多种 Swing 组件支持：`JTextComponent`、`JLabel`、`AbstractButton`、`JTree`、`JTable`、`JList`
- VCS 日志、变更文件列表、文件历史、变更视图等右键菜单集成
- 提交编辑器右键菜单集成（通过 `vcs-actions.xml` 可选依赖配置）
- 国际化支持：中文 (`MyBundle_zh.properties`) 和英文 (`MyBundle.properties`)
- 基于 IntelliJ Platform Gradle Plugin 构建，最低支持版本 2025.2.6.2

### Fixed
- 修复 `vcs-actions.xml` 中 VCS Log 菜单组的多 `add-to-group` 结构解析问题
