# Weather AI Service

AI-powered backend that turns raw weather forecasts into human-friendly advice.

This backend is built to pair with the [weather-app](https://github.com/arkaman/weather-app) frontend.

## Tech Stack
- Spring Boot
- Gemini API
- OpenWeather API (data source)
- Redis (caching)

## Features
- Filters today's forecast
- Generates concise weather advice using AI
- REST API endpoint for frontend integration
- Smart city-based cache handling with automatic expiration

## API

### POST `/api/weather/advice`

Request:
```json
{
  "city": [ ...city name...],
  "list": [ ...forecast data... ]
}
```

Response:
```json
{
  "advice": "Light clothing is fine during the day..."
}
```

## Setup

1. Set environment variables:

```
GOOGLE_API_KEY=your_api_key
REDIS_URL=your_redis_url
```

2. Run the Spring Boot application

3. Call the API endpoint
