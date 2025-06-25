![Build](https://github.com/embabel/embabel-agent/actions/workflows/maven.yml/badge.svg)

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Tomcat](https://img.shields.io/badge/apache%20tomcat-%23F8DC75.svg?style=for-the-badge&logo=apache-tomcat&logoColor=black)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![ChatGPT](https://img.shields.io/badge/chatGPT-74aa9c?style=for-the-badge&logo=openai&logoColor=white)
![JSON](https://img.shields.io/badge/JSON-000?logo=json&logoColor=fff)
![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)

<img align="left" src="https://github.com/embabel/embabel-agent/blob/main/embabel-agent-api/images/315px-Meister_der_Weltenchronik_001.jpg?raw=true" width="180">

&nbsp;&nbsp;&nbsp;&nbsp;

&nbsp;&nbsp;&nbsp;&nbsp;

# Movie Finder Agent

## API keys you'll need

export OMDB_API_KEY="your_omdb_key"           # http://www.omdbapi.com/
export X_RAPIDAPI_KEY="your_rapidapi_key"

### 🎬 **Advanced: Movie Recommendation Engine**

> **Available in:** Kotlin | **Concept:** Complex Domain-Driven Workflows

An intelligent movie recommendation agent that analyzes taste profiles and suggests streaming-available movies.

**What It Teaches:**

- 🏗️ **Domain-Driven Design** with rich domain models
- 🔄 **Complex workflows** with conditions and retries
- 📊 **Spring Data integration** with repositories
- 🎭 **Persona-based prompting** for creative content
- 🛠️ **Multiple API integration** (OMDB, streaming services)
- 📈 **Progress tracking** and event publishing
- 🤝 **Human-in-the-loop** confirmations

**Domain Model:**

```kotlin
data class MovieBuff(
    override val name: String,
    val movieRatings: List<MovieRating>,
    val countryCode: String,
    val streamingServices: List<String>
) : Person

data class DecoratedMovieBuff(
    val movieBuff: MovieBuff,
    val tasteProfile: String  // AI-generated analysis
)
```

**How It Works:**

1. Find MovieBuff from repository (with confirmation)
2. Analyze their taste profile using AI
3. Research current news for inspiration
4. Generate movie suggestions (excluding seen movies)
5. Filter by streaming availability
6. Create Roger Ebert-style writeup

**Try It:**

```bash
# Requires OMDB_API_KEY and X_RAPIDAPI_KEY
"Suggest movies for Rod tonight"
```

**Key Spring Patterns:**

```kotlin
@ConfigurationProperties(prefix = "embabel.examples.moviefinder")
data class MovieFinderConfig(
    val suggestionCount: Int = 5,
    val suggesterPersona: Persona = Roger,
    val model: String = OpenAiModels.GPT_41_MINI
)

interface MovieBuffRepository : CrudRepository<MovieBuff, String>
```

**Advanced Workflow Control:**

```kotlin
@Action(
    post = [HAVE_ENOUGH_MOVIES],  // Condition check
    canRerun = true               // Retry if needed
)
fun suggestMovies(/* params */): StreamableMovies

@Condition(name = HAVE_ENOUGH_MOVIES)
fun haveEnoughMovies(context: OperationContext): Boolean
```

**Location:** `examples-kotlin/src/main/kotlin/com/embabel/example/movie/`

---

# To run

Run the shell script to start Embabel under Spring Shell:

```bash
cd scripts
./shell.sh
```

There is a single example agent, `WriteAndReviewAgent`.
It will be under your package name.
It uses one LLM with a high temperature and creative persona to write a story based on your input,
then another LLM with a low temperature and different persona to review the story.

When the Embabel shell comes up, use the story agent like this:

```
x "Tell me a story about...[your topic]"
```


