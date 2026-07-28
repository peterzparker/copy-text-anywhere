package com.github.peterzparker.copytextanywhere.services;

import com.github.peterzparker.copytextanywhere.MyBundle;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.project.DumbAware;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

/**
 * 全局右键复制 Action - 可出现在所有包含文字的右键菜单中。
 * <p>
 * 当用户在任意组件的右键菜单中点击 "Copy Text" 时，该 Action 会尝试从
 * 多种 Swing 组件（编辑器、文本框、标签、树、表格、列表等）中提取文字并复制到剪贴板。
 */
public class CopyTextAction extends AnAction implements DumbAware {

    @Override
    public void update(@NotNull AnActionEvent e) {
        boolean visible = canCopyText(e);
        e.getPresentation().setEnabledAndVisible(visible);
        if (visible) {
            e.getPresentation().setText(MyBundle.message("action.CopyTextAction.text"));
            e.getPresentation().setDescription(MyBundle.message("action.CopyTextAction.description"));
        }
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        String text = extractText(e);
        if (text != null && !text.isEmpty()) {
            CopyPasteManager.getInstance().setContents(new StringSelection(text));
        }
    }

    /**
     * 判断当前上下文是否可以提取文字。
     */
    private static boolean canCopyText(@NotNull AnActionEvent e) {
        // 1. 编辑器（编辑区右键）
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor != null) return true;

        // 2. 上下文组件
        Component component = e.getData(PlatformDataKeys.CONTEXT_COMPONENT);
        if (canExtractFromComponent(component)) return true;

        // 3. 焦点拥有者
        Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        return canExtractFromComponent(focused);
    }

    /**
     * 判断该组件是否支持文字提取。
     */
    private static boolean canExtractFromComponent(@Nullable Component component) {
        if (component == null) return false;
        return component instanceof JTextComponent
                || component instanceof JLabel
                || component instanceof AbstractButton
                || component instanceof JTree
                || component instanceof JTable
                || component instanceof JList;
    }

    /**
     * 从当前上下文中提取文字，使用多种策略依次尝试。
     */
    @Nullable
    private static String extractText(@NotNull AnActionEvent e) {
        // 策略 1: 编辑器选中文本
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor != null) {
            String text = getEditorSelectedText(editor);
            if (text != null) return text;
        }

        // 策略 2: 上下文组件（右键点击的组件）
        Component component = e.getData(PlatformDataKeys.CONTEXT_COMPONENT);
        if (component != null) {
            String text = extractTextFromComponent(component);
            if (text != null && !text.isEmpty()) return text;
        }

        // 策略 3: 当前焦点组件
        Component focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        if (focused != null && focused != component) {
            String text = extractTextFromComponent(focused);
            if (text != null && !text.isEmpty()) return text;
        }

        return null;
    }

    /**
     * 获取编辑器中的选中文本，若无选中则复制当前行内容。
     */
    @Nullable
    private static String getEditorSelectedText(@NotNull Editor editor) {
        SelectionModel sm = editor.getSelectionModel();
        if (sm.hasSelection()) {
            String selected = sm.getSelectedText();
            if (selected != null && !selected.isEmpty()) return selected;
        }

        // 无选中时复制当前行
        com.intellij.openapi.editor.CaretModel caretModel = editor.getCaretModel();
        com.intellij.openapi.editor.Document document = editor.getDocument();
        int line = caretModel.getLogicalPosition().line;
        if (line >= 0 && line < document.getLineCount()) {
            int start = document.getLineStartOffset(line);
            int end = document.getLineEndOffset(line);
            String lineText = document.getText(new com.intellij.openapi.util.TextRange(start, end));
            if (!lineText.isEmpty()) return lineText;
        }
        return null;
    }

    /**
     * 从 Swing 组件中提取文字，支持多种组件类型。
     */
    @Nullable
    private static String extractTextFromComponent(@Nullable Component component) {
        if (component == null) return null;

        // 文本组件（JTextField, JTextArea, JTextPane 等）
        if (component instanceof JTextComponent tc) {
            String selected = tc.getSelectedText();
            if (selected != null && !selected.isEmpty()) return selected;
            String allText = tc.getText();
            if (allText != null && !allText.isEmpty()) return allText;
            return null;
        }

        // 标签（JLabel, JBLabel 等 - 常用于提交信息、commit 注释等）
        if (component instanceof JLabel label) {
            String text = label.getText();
            if (text != null && !text.isEmpty()) return text;
            return null;
        }

        // 按钮（JButton, JCheckBox 等）
        if (component instanceof AbstractButton button) {
            String text = button.getText();
            if (text != null && !text.isEmpty()) return text;
            return null;
        }

        // 树组件（项目视图、结构视图等）
        if (component instanceof JTree tree) {
            if (tree.getSelectionCount() > 0) {
                StringBuilder sb = new StringBuilder();
                javax.swing.tree.TreePath[] paths = tree.getSelectionPaths();
                if (paths != null) {
                    for (int i = 0; i < paths.length; i++) {
                        if (i > 0) sb.append("\n");
                        Object last = paths[i].getLastPathComponent();
                        sb.append(last != null ? last.toString() : "");
                    }
                    String result = sb.toString();
                    if (!result.isEmpty()) return result;
                }
            }
            return null;
        }

        // 表格组件（常用于 VCS Log、搜索结果等）
        if (component instanceof JTable table) {
            int[] rows = table.getSelectedRows();
            int[] cols = table.getSelectedColumns();
            if (rows.length == 0 || cols.length == 0) return null;
            StringBuilder sb = new StringBuilder();
            for (int row : rows) {
                for (int col : cols) {
                    if (!sb.isEmpty()) sb.append(" ");
                    Object val = table.getValueAt(row, col);
                    sb.append(val != null ? val.toString() : "");
                }
                sb.append("\n");
            }
            return sb.toString().trim();
        }

        // 列表组件
        if (component instanceof JList) {
            JList<?> list = (JList<?>) component;
            java.util.List<?> values = list.getSelectedValuesList();
            if (values.isEmpty()) return null;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < values.size(); i++) {
                if (i > 0) sb.append("\n");
                Object val = values.get(i);
                sb.append(val != null ? val.toString() : "");
            }
            return sb.toString();
        }

        return null;
    }
}
