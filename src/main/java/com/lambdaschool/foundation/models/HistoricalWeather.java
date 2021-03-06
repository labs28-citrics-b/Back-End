package com.lambdaschool.foundation.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Model for City's historical weather data
 */
@Entity
@Table(name = "historical_weather")
public class HistoricalWeather extends Auditable {

  /**
   * Weather data ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonIgnore
  private long weatherId;

  /**
   * Month of entry
   */
  @NotNull
  private String month;

  /**
   * City's average recorded precipitation
   */
  @NotNull
  private Double precipitation;

  /**
   * City's average recorded temperature
   */
  @NotNull
  private Double temperature;

  /**
   * City entry belongs to
   */
  @ManyToOne
  @JoinColumn(name = "city_id")
  @NotNull
  @JsonIgnore
  private City city;

  /**
   * Default constructor
   */
  public HistoricalWeather() {}

  /**
   * Main constructor
   * @param month Month of entry
   * @param precipitation Average precipitation
   * @param temperature Average temperature
   * @param city City entry belongs to
   */
  public HistoricalWeather(
    @NotNull String month,
    @NotNull Double precipitation,
    @NotNull Double temperature,
    @NotNull City city
  ) {
    this.month = month;
    this.precipitation = precipitation;
    this.temperature = temperature;
    this.city = city;
  }

  /**
   * Getters and setters for HistoricalWeather's fields
   *
   *******************************************************************************/
  public long getWeatherId() {
    return weatherId;
  }

  public void setWeatherId(long weatherId) {
    this.weatherId = weatherId;
  }

  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public Double getPrecipitation() {
    return precipitation;
  }

  public void setPrecipitation(Double precipitation) {
    this.precipitation = precipitation;
  }

  public Double getTemperature() {
    return temperature;
  }

  public void setTemperature(Double temperature) {
    this.temperature = temperature;
  }

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  /**
   * Override default toString()
   * @return String of HistoricalWeather object
   */
  @Override
  public String toString() {
    return (
      "HistoricalWeather{" +
      "month='" +
      month +
      '\'' +
      ", precipitation=" +
      precipitation +
      ", temperature=" +
      temperature +
      '}'
    );
  }
}
