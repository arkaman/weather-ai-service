package io.github.arkaman.weatherai.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import io.github.arkaman.weatherai.domain.dto.ForecastItem;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GeminiService {

    private final Client client;
    private final CacheService cacheService;

    public GeminiService(Client client, CacheService cacheService) {
        this.client = client;
        this.cacheService = cacheService;
    }

    public String generateAdvice(String city, List<ForecastItem> forecastList) {
        // create cache key
        String normalizedCity = city.toLowerCase().trim();

        String cacheKey = "weather:" + normalizedCity + ":" + LocalDate.now();

        // check Redis first
        String cachedAdvice = cacheService.get(cacheKey);

        if (cachedAdvice != null) {
            System.out.println("Returning cached advice...");
            return cachedAdvice;
        }

        // generate new advice
        List<ForecastItem> todayItems = filterTodayForecast(forecastList);

        if (todayItems.isEmpty()) {
            return "No forecast data available for today.";
        }

        String summary = buildSummary(todayItems);

        String advice = requestAdviceFromGemini(summary);

        // store in redis
        cacheService.save(cacheKey, advice, 6);

        return advice;
    }

    private List<ForecastItem> filterTodayForecast(List<ForecastItem> list) {
        String today = LocalDate.now().toString();

        return list.stream()
                .filter(item -> item.dt_txt != null && item.dt_txt.startsWith(today))
                .toList();
    }

    private String buildSummary(List<ForecastItem> items) {

        StringBuilder sb = new StringBuilder();

        for (ForecastItem item : items) {

            if (item.main == null || item.weather == null || item.weather.isEmpty() || item.wind == null) {
                continue;
            }

            String time = item.dt_txt.split(" ")[1];
            double temp = item.main.temp;

            sb.append(String.format(
                    "Time: %s | Temp: %.1f°C | %s | Humidity: %d%% | Wind: %.1f m/s%n",
                    time,
                    temp,
                    item.weather.getFirst().description,
                    item.main.humidity,
                    item.wind.speed
            ));
        }

        return sb.toString();
    }

    private String requestAdviceFromGemini(String weatherSummary) {

        String prompt = """
                You are a practical weather assistant.
                
                Use ONLY the provided forecast.
                
                Give advice in EXACTLY 3 bullet points:
                - Best time to go outside
                - Clothing suggestion
                - One caution
                
                Keep it simple and realistic. No extra explanation.
                
                Forecast:
                """ + weatherSummary;

        try {
            GenerateContentResponse response =
                    client.models.generateContent(
                            "gemini-3-flash-preview",
                            prompt,
                            null
                    );

            return response.text();

        } catch (Exception e) {
            return "Unable to generate advice at the moment.";
        }
    }
}