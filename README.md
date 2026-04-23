# Weather AI Service

AI-powered backend that turns raw weather forecasts into human-friendly advice.

This backend is built to pair with the [weather-app](https://github.com/arkaman/weather-app) frontend.

## Tech Stack
- Spring Boot
- Gemini API
- OpenWeather API (data source)

## Features
- Filters today's forecast
- Generates concise weather advice using AI
- REST API endpoint for frontend integration

## API

### POST `/api/weather/advice`

Request:
```json
{
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

1. Set your API key:

```
GOOGLE_API_KEY=your_api_key
```

2. Run the Spring Boot application

3. Call the API endpoint
