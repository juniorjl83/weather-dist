package com.crossover.trial.weather.service;

import com.crossover.trial.weather.model.AtmosphericInformation;
import com.crossover.trial.weather.model.Builder;
import com.crossover.trial.weather.model.DataPoint;
import com.crossover.trial.weather.service.RestWeatherCollectorEndpoint;
import com.crossover.trial.weather.service.RestWeatherQueryEndpoint;
import com.crossover.trial.weather.service.WeatherCollectorEndpoint;
import com.crossover.trial.weather.service.WeatherQueryEndpoint;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import javax.ws.rs.core.Response;

import static com.crossover.trial.weather.service.RestWeatherCollectorEndpoint.addAirport;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WeatherEndpointTest {

  private WeatherQueryEndpoint query = new RestWeatherQueryEndpoint();

  private WeatherCollectorEndpoint update = new RestWeatherCollectorEndpoint();

  private Gson gson = new Gson();

  private DataPoint dp;

  @Before
  public void setUp() throws Exception {
    RestWeatherQueryEndpoint.init();
    dp = new Builder(10, 22, 20, 30, 10).build();
    update.updateWeather("BOS", "wind", gson.toJson(dp));
    query.weather("BOS", "0").getEntity();
  }

  @Test
  public void testPing() throws Exception {
    String ping = query.ping();
    JsonElement pingResult = new JsonParser().parse(ping);
    assertEquals(1, pingResult.getAsJsonObject().get("datasize").getAsInt());
    assertEquals(5,
        pingResult.getAsJsonObject().get("iata_freq").getAsJsonObject().entrySet().size());
  }

  @Test
  public void testGet() throws Exception {
    List<AtmosphericInformation> ais =
        (List<AtmosphericInformation>) query.weather("BOS", "0").getEntity();
    assertEquals(ais.get(0).getWind(), dp);
  }

  @Test
  public void testGetNearby() throws Exception {
    // check datasize response
    update.updateWeather("JFK", "wind", gson.toJson(dp));
    dp.setMean(40);
    update.updateWeather("EWR", "wind", gson.toJson(dp));
    dp.setMean(30);
    update.updateWeather("LGA", "wind", gson.toJson(dp));

    List<AtmosphericInformation> ais =
        (List<AtmosphericInformation>) query.weather("JFK", "200").getEntity();
    assertEquals(3, ais.size());
  }

  @Test
  public void testUpdate() throws Exception {

    DataPoint windDp = new Builder(10, 22, 20, 30, 10).build();
    update.updateWeather("BOS", "wind", gson.toJson(windDp));
    query.weather("BOS", "0").getEntity();

    String ping = query.ping();
    JsonElement pingResult = new JsonParser().parse(ping);
    assertEquals(1, pingResult.getAsJsonObject().get("datasize").getAsInt());

    DataPoint cloudCoverDp = new Builder(10, 50, 60, 100, 4).build();
    update.updateWeather("BOS", "cloudcover", gson.toJson(cloudCoverDp));

    List<AtmosphericInformation> ais =
        (List<AtmosphericInformation>) query.weather("BOS", "0").getEntity();
    assertEquals(ais.get(0).getWind(), windDp);
    assertEquals(ais.get(0).getCloudCover(), cloudCoverDp);
  }

  @Test
  public void testDelete() throws Exception {

    int status = update.deleteAirport("EWR").getStatus();
    assertEquals(200, status);
    
    Set<String> airports = (Set<String>) update.getAirports().getEntity();
    assertTrue(airports.size() == 4);
    
  }
}
