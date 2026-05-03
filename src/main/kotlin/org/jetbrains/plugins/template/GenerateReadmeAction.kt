package org.jetbrains.plugins.template

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.command.WriteCommandAction

class GenerateReadmeAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        val context = StringBuilder("Files in this project:\n")
        project.baseDir.children.forEach {
            context.append("- ${it.name}\n")
        }

        val aiService = AiService("OPENAI_API_KEY")

        Thread {
            val generatedReadme = aiService.generateReadme(context.toString())

            // 3. Сохраняем результат в файл
            WriteCommandAction.runWriteCommandAction(project) {
                val readmeFile = project.baseDir.findOrCreateChildData(this, "README.md")
                VfsUtil.saveText(readmeFile, generatedReadme)
            }
        }.start()
    }
}