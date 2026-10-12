# Spring Boot News Application

A small Java Spring Boot REST API that retrieves top headlines from [NewsAPI.org](https://newsapi.org/).

## Requirements

- Java 17 or newer
- Maven 3.9+ (optional if using the included Maven wrapper; this starter zip does not include wrapper files)
- Docker Desktop
- A NewsAPI.org API key

## 1. Get your NewsAPI key

1. Create an account at https://newsapi.org/.
2. Copy your API key.
3. Keep the key private. Do not commit it to GitHub.

## 2. Run from VS Code

1. Extract the ZIP.
2. Open the `springboot-news-app` folder in VS Code.
3. Install the **Extension Pack for Java** if prompted.
4. Open a terminal in the project folder.
5. Set your API key in the terminal.

### Windows PowerShell

```powershell
$env:NEWS_API_KEY="paste_your_real_api_key_here"
$env:NEWS_COUNTRY="in"
mvn spring-boot:run
```

### macOS / Linux

```bash
export NEWS_API_KEY="paste_your_real_api_key_here"
export NEWS_COUNTRY="in"
mvn spring-boot:run
```

Open these endpoints in a browser:

- `http://localhost:8080/` — app information
- `http://localhost:8080/api/health` — health check
- `http://localhost:8080/api/news` — top headlines from NewsAPI.org

A successful `/api/news` response should contain fields such as `status`, `totalResults`, and `articles`. The exact articles depend on the API response and country.

## 3. Build and run with Docker

Run these commands from the project root. Replace the API key placeholder with your actual key.

### Windows PowerShell

```powershell
docker build -t YOUR_DOCKERHUB_USERNAME/springboot-news-app:1.0 .
docker run -d --name news-app -p 8080:8080 -e NEWS_API_KEY="paste_your_real_api_key_here" -e NEWS_COUNTRY="in" YOUR_DOCKERHUB_USERNAME/springboot-news-app:1.0
docker ps
docker logs news-app
```

### macOS / Linux

```bash
docker build -t YOUR_DOCKERHUB_USERNAME/springboot-news-app:1.0 .
docker run -d --name news-app -p 8080:8080 -e NEWS_API_KEY="paste_your_real_api_key_here" -e NEWS_COUNTRY="in" YOUR_DOCKERHUB_USERNAME/springboot-news-app:1.0
docker ps
docker logs news-app
```

Verify:

```text
http://localhost:8080/api/health
http://localhost:8080/api/news
```

## 4. Push the Docker image to Docker Hub

1. Create/sign in to Docker Hub: https://hub.docker.com/
2. Use your actual Docker Hub username in place of `YOUR_DOCKERHUB_USERNAME`.
3. Run:

```bash
docker login
docker push YOUR_DOCKERHUB_USERNAME/springboot-news-app:1.0
```

If you built the image using a different username/tag, push that exact image name.

## 5. Create and push the GitHub repository

Create an empty repository on GitHub, for example `springboot-news-app`. Do not add a README there if you already have this one locally. Then run from the project folder:

```bash
git init
git add .
git commit -m "Initial Spring Boot News API application"
git branch -M main
git remote add origin https://github.com/YOUR_GITHUB_USERNAME/springboot-news-app.git
git push -u origin main
```

Replace `YOUR_GITHUB_USERNAME` with your GitHub username. The `.env` file is ignored; do not commit API keys.

## Troubleshooting

- **401 / 403 from NewsAPI:** check the key, account access, and plan restrictions.
- **429 response:** API quota/rate limit may have been reached.
- **No API key error:** set `NEWS_API_KEY` in the same terminal or pass it to `docker run` using `-e NEWS_API_KEY=...`.
- **Port 8080 already in use:** stop the other service or map another host port, e.g. `-p 8081:8080`, then open `http://localhost:8081/api/news`.
- **Maven command not found:** install Maven, or use the Maven extension in VS Code. Docker builds Maven inside the build stage, so Docker-based builds do not require Maven installed locally.
- NewsAPI's free/developer plan can have usage and availability restrictions. If the API responds with an error, the app returns an HTTP error with a helpful message; it cannot bypass NewsAPI plan limits.
