package org.jetbrains.plugins.template

import com.google.gson.JsonParser

class PromptLoader {
    fun loadSystemPrompt(): String {
        val inputStream = this::class.java.getResourceAsStream("/prompts/technical_writer.json")
        val jsonText = inputStream?.bufferedReader()?.use { it.readText() } ?: return ""

        val jsonObject = JsonParser.parseString(jsonText).asJsonObject
        val specialization = jsonObject.getAsJsonObject("specialization")

        val identity = specialization.get("professional_identity").asString
        val style = specialization.get("communication_style").asString
        val constraints = specialization.getAsJsonArray("constraints").joinToString("\n- ")

        return """
            Role: $identity
            Style: $style
            Constraints: 
            - $constraints
        """.trimIndent()
    }
}