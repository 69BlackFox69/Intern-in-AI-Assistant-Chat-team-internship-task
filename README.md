# AI-Powered README Generator Plugin for IntelliJ

A sophisticated IntelliJ Platform plugin that leverages artificial intelligence to automatically generate professional README.md files for software projects. This plugin streamlines documentation creation by analyzing project structure and generating comprehensive, well-structured documentation using OpenAI's GPT-4o model.

## Features

- **AI-Powered Documentation**: Automatically generates professional README files using OpenAI's GPT-4o language model
- **Specialized AI Agent**: Utilizes a custom-trained technical writer AI persona for consistent, high-quality documentation
- **One-Click Generation**: Simple context menu integration for effortless README generation
- **Project Awareness**: Analyzes project structure to provide context-aware documentation
- **Professional Standards**: Enforces technical writing standards through specialized prompts and constraints

## Architecture Overview

### Core Components

#### 1. **AiService** (`AiService.kt`)
Responsible for communication with OpenAI API:
- Constructs and sends requests to GPT-4o via OpenAI Chat Completion API
- Integrates system prompts from `PromptLoader`
- Handles API responses and error management
- Dependencies: OkHttp 4.12.0, Gson 2.10.1

#### 2. **PromptLoader** (`PromptLoader.kt`)
Manages AI agent configuration:
- Loads specialized technical writer persona from `technical_writer.json`
- Extracts professional identity, communication style, and constraints
- Constructs system prompt dynamically for consistent AI behavior

#### 3. **GenerateReadmeAction** (`GenerateReadmeAction.kt`)
IntelliJ Platform integration layer:
- Implements `AnAction` for IDE context menu integration
- Gathers project context (files and structure)
- Invokes `AiService` for README generation
- Saves generated content to `README.md` using WriteCommandAction

#### 4. **Technical Writer Persona** (`technical_writer.json`)
Specialized AI configuration:
```json
{
  "domain": "Professional README.md generation",
  "specialization": {
    "professional_identity": "Seasoned technical writer",
    "communication_style": "Clear, concise, structured with bullet points",
    "constraints": [
      "No personal opinions or speculation",
      "Define technical jargon",
      "Exclude irrelevant information"
    ]
  }
}
```

## Installation & Setup

### Prerequisites
- IntelliJ IDEA 2025.2.6.1 or compatible JetBrains IDE
- JDK 11+
- OpenAI API key with GPT-4o access
- Gradle 8.x

### Build Instructions

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd intellij-platform-plugin-template
   ```

2. **Configure API Key**
   Set your OpenAI API key as an environment variable or directly in the code:
   ```kotlin
   val aiService = AiService("your-openai-api-key")
   ```

3. **Build the plugin**
   ```bash
   ./gradlew build
   ```

4. **Run in IDE**
   ```bash
   ./gradlew runIde
   ```

### Installation in IntelliJ
1. Open IntelliJ IDEA
2. Go to **Settings** → **Plugins** → **Install Plugin from Disk**
3. Select the built plugin JAR from `build/distributions/`

## Usage

### Generating README

1. Open your project in IntelliJ IDEA
2. Right-click on the project root in Project View
3. Select **Generate AI README** from the context menu
4. Wait for the AI agent to analyze the project and generate documentation
5. A new `README.md` file will be created in the project root

### Customizing AI Behavior

Modify `src/main/resources/prompts/technical_writer.json` to adjust:
- Professional identity and expertise level
- Communication style and tone
- Content constraints and guidelines

## Dependencies

```kotlin
// Build Configuration (build.gradle.kts)
intellijIdea("2025.2.6.1")                    // IntelliJ Platform
junit:junit:4.13.2                            // Testing
okhttp3:okhttp:4.12.0                         // HTTP Client
gson:gson:2.10.1                              // JSON Processing
```

## Configuration

### Plugin Manifest (`plugin.xml`)
- **ID**: `org.jetbrains.plugins.template`
- **Action ID**: `com.example.GenerateReadmeAction`
- **Tool Window**: `MyToolWindow`
- **Startup Activity**: `MyProjectActivity`

### Gradle Configuration (`gradle.properties`)
```properties
pluginRepositoryUrl=https://plugins.jetbrains.com/plugin/...
```

## Technical Details

### API Flow

```
User Action (Context Menu)
        ↓
GenerateReadmeAction.actionPerformed()
        ↓
Collect Project Context
        ↓
AiService.generateReadme()
        ↓
PromptLoader.loadSystemPrompt()
        ↓
OpenAI API (GPT-4o)
        ↓
Generated README
        ↓
Write to README.md (WriteCommandAction)
```

### Error Handling
- API failures return error codes and messages
- Empty responses handled gracefully
- WriteCommandAction ensures thread-safe file operations

## Security Considerations

⚠️ **Important**: Never commit API keys to version control
- Store OpenAI API key in environment variables
- Use IntelliJ's plugin configuration for sensitive data in production
- Consider implementing OAuth or secure credential storage

## Extensibility

This plugin can be extended with:
- Additional AI models (Claude, Llama, etc.)
- Multiple language support
- Custom README templates
- Interactive configuration UI
- Real-time preview functionality

## Project Structure

```
src/
├── main/
│   ├── kotlin/
│   │   └── org/jetbrains/plugins/template/
│   │       ├── AiService.kt
│   │       ├── PromptLoader.kt
│   │       └── GenerateReadmeAction.kt
│   └── resources/
│       ├── META-INF/plugin.xml
│       ├── messages/MyBundle.properties
│       └── prompts/technical_writer.json
└── test/
    ├── kotlin/
    └── testData/
```

## Testing

Run tests with:
```bash
./gradlew test
```

Test data includes sample project structures in `src/test/testData/`

## License

This project is licensed under the JetBrains Plugin License Agreement. See [LICENSE](LICENSE) file for details.

## Code of Conduct

This project adheres to the [Contributor Covenant Code of Conduct](CODE_OF_CONDUCT.md).

## Technical Internship Project

This plugin was developed as a technical specification project for the JetBrains internship program, demonstrating:
- IntelliJ Platform Plugin development expertise
- AI integration and LLM API usage
- Professional software architecture
- Clean code practices and design patterns

---

**Generated with AI Excellence** 🚀

*Leveraging specialized AI personas to create professional technical documentation.*
