package io.github.arkaman.weatherai.domain.dto;

import java.util.List;

public class ForecastItem {
    public Main main;
    public List<Weather> weather;
    public Wind wind;
    public String dt_txt;
}
