package com.crossover.trial.weather.service;

import com.crossover.trial.weather.exception.WeatherException;
import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.model.AtmosphericInformation;
import com.crossover.trial.weather.model.DataPoint;
import com.crossover.trial.weather.model.DataPointType;
import com.google.gson.Gson;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import static com.crossover.trial.weather.service.RestWeatherQueryEndpoint.*;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * A REST implementation of the WeatherCollector API. Accessible only to airport weather collection
 * sites via secure VPN.
 *
 * @author code test administrator
 */

@Path("/collect")
public class RestWeatherCollectorEndpoint implements WeatherCollectorEndpoint {

  /** The Constant LOGGER. */
  public static final Logger LOGGER =
      Logger.getLogger(RestWeatherCollectorEndpoint.class.getName());

  /** shared gson json to object factory. */
  public static final  Gson gson = new Gson();

  /*
   * (non-Javadoc)
   * 
   * @see com.crossover.trial.weather.service.WeatherCollectorEndpoint#ping()
   */
  @Override
  public Response ping() {
    return Response.status(Response.Status.OK).entity("ready").build();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.crossover.trial.weather.service.WeatherCollectorEndpoint#updateWeather(java.lang.String,
   * java.lang.String, java.lang.String)
   */
  @Override
  public Response updateWeather(@PathParam("iata") String iataCode,
      @PathParam("pointType") String pointType, String datapointJson) {
    try {
      addDataPoint(iataCode, pointType, gson.fromJson(datapointJson, DataPoint.class));
    } catch (WeatherException e) {
      LOGGER.info(e.getMessage());
    }
    return Response.status(Response.Status.OK).build();
  }


  /*
   * (non-Javadoc)
   * 
   * @see com.crossover.trial.weather.service.WeatherCollectorEndpoint#getAirports()
   */
  @Override
  public Response getAirports() {
    Set<String> retval = new HashSet<>();
    for (AirportData ad : airportData) {
      retval.add(ad.getIata());
    }
    return Response.status(Response.Status.OK).entity(retval).build();
  }


  /*
   * (non-Javadoc)
   * 
   * @see com.crossover.trial.weather.service.WeatherCollectorEndpoint#getAirport(java.lang.String)
   */
  @Override
  public Response getAirport(@PathParam("iata") String iata) {
    AirportData ad = findAirportData(iata);
    return Response.status(Response.Status.OK).entity(ad).build();
  }


  /*
   * (non-Javadoc)
   * 
   * @see com.crossover.trial.weather.service.WeatherCollectorEndpoint#addAirport(java.lang.String,
   * java.lang.String, java.lang.String)
   */
  @Override
  public Response addAirport(@PathParam("iata") String iata, @PathParam("lat") String latString,
      @PathParam("long") String longString) {
    addAirport(iata, Double.valueOf(latString), Double.valueOf(longString));
    return Response.status(Response.Status.OK).build();
  }


  /*
   * (non-Javadoc)
   * 
   * @see
   * com.crossover.trial.weather.service.WeatherCollectorEndpoint#deleteAirport(java.lang.String)
   */
  @Override
  public Response deleteAirport(@PathParam("iata") String iata) {
    return Response.status(Response.Status.NOT_IMPLEMENTED).build();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.crossover.trial.weather.service.WeatherCollectorEndpoint#exit()
   */
  @Override
  public Response exit() {
    System.exit(0);
    return Response.noContent().build();
  }
  //
  // Internal support methods
  //

  /**
   * Update the airports weather data with the collected data.
   *
   * @param iataCode the 3 letter IATA code
   * @param pointType the point type {@link DataPointType}
   * @param dp a datapoint object holding pointType data
   *
   * @throws WeatherException if the update can not be completed
   */
  public void addDataPoint(String iataCode, String pointType, DataPoint dp)
      throws WeatherException {
    int airportDataIdx = getAirportDataIdx(iataCode);
    AtmosphericInformation ai = atmosphericInformation.get(airportDataIdx);
    updateAtmosphericInformation(ai, pointType, dp);
  }

  /**
   * update atmospheric information with the given data point for the given point type.
   *
   * @param ai the atmospheric information object to update
   * @param pointType the data point type as a string
   * @param dp the actual data point
   * @throws WeatherException the weather exception
   */
  public void updateAtmosphericInformation(AtmosphericInformation ai, String pointType,
      DataPoint dp) throws WeatherException {

    if (pointType.equalsIgnoreCase(DataPointType.WIND.name()) && dp.getMean() >= 0) {
      ai.setWind(dp);
      ai.setLastUpdateTime(System.currentTimeMillis());

    } else if (pointType.equalsIgnoreCase(DataPointType.TEMPERATURE.name())
        && (dp.getMean() >= -50 && dp.getMean() < 100)) {
      ai.setTemperature(dp);
      ai.setLastUpdateTime(System.currentTimeMillis());

    } else if (pointType.equalsIgnoreCase(DataPointType.HUMIDTY.name())
        && (dp.getMean() >= 0 && dp.getMean() < 100)) {
      ai.setHumidity(dp);
      ai.setLastUpdateTime(System.currentTimeMillis());

    } else if (pointType.equalsIgnoreCase(DataPointType.PRESSURE.name())
        && (dp.getMean() >= 650 && dp.getMean() < 800)) {
      ai.setPressure(dp);
      ai.setLastUpdateTime(System.currentTimeMillis());

    } else if (pointType.equalsIgnoreCase(DataPointType.CLOUDCOVER.name())
        && (dp.getMean() >= 0 && dp.getMean() < 100)) {
      ai.setCloudCover(dp);
      ai.setLastUpdateTime(System.currentTimeMillis());

    } else if (pointType.equalsIgnoreCase(DataPointType.PRECIPITATION.name())
        && (dp.getMean() >= 0 && dp.getMean() < 100)) {
      ai.setPrecipitation(dp);
      ai.setLastUpdateTime(System.currentTimeMillis());

    } else {
      throw new IllegalStateException("couldn't update atmospheric data");
    }
  }

  /**
   * Add a new known airport to our list.
   *
   * @param iataCode 3 letter code
   * @param latitude in degrees
   * @param longitude in degrees
   *
   * @return the added airport
   */
  public static AirportData addAirport(String iataCode, double latitude, double longitude) {
    AirportData ad = new AirportData();
    airportData.add(ad);

    AtmosphericInformation ai = new AtmosphericInformation();
    atmosphericInformation.add(ai);
    ad.setIata(iataCode);
    ad.setLatitude(latitude);
    ad.setLatitude(longitude);
    return ad;
  }
}
