package com.myskillskit.unicode

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.util.TextRange

abstract class AbstractUnicodeAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val project = e.getRequiredData(CommonDataKeys.PROJECT)
        val document = editor.document

        val primaryCaret = editor.caretModel.primaryCaret
        val start = primaryCaret.selectionStart
        val end = primaryCaret.selectionEnd

        val selectedText = document.getText(TextRange.from(start, end - start))
        val result = convert(selectedText)

        WriteCommandAction.runWriteCommandAction(project) {
            document.replaceString(start, end, result)
        }
    }

    override fun update(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isEnabledAndVisible =
            editor != null && editor.caretModel.primaryCaret.hasSelection()
    }

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

    protected abstract fun convert(text: String): String
}
