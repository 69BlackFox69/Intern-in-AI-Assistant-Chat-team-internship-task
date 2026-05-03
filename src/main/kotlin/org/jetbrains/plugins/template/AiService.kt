package org.jetbrains.plugins.template

import com.google.gson.JsonArray
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import com.google.gson.JsonObject
import com.google.gson.JsonParser

class AiService(private val apiKey: String) {
    private val client = OkHttpClient()
    private val promptLoader = PromptLoader()

    fun generateReadme(projectContext: String): String {
        val systemPrompt = promptLoader.loadSystemPrompt()

        val json = JsonObject().apply {
            addProperty("model", "gpt-4o")
            add("messages", JsonArray().apply {
                add(JsonObject().apply {
                    addProperty("role", "system")
                    addProperty("content", systemPrompt) // Вставляем динамический промпт
                })
                add(JsonObject().apply {
                    addProperty("role", "user")
                    addProperty("content", "Analyze this project structure:\n$projectContext")
                })
            })
        }

        val body = json.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .header("Authorization", "Bearer $apiKey")
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return "Error: ${response.code}"
            val responseBody = response.body?.string() ?: return "Empty response"
            val jsonResponse = JsonParser.parseString(responseBody).asJsonObject
            return jsonResponse["choices"].asJsonArray[0].asJsonObject["message"].asJsonObject["content"].asString
        }
    }
}