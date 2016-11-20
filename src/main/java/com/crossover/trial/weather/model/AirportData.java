package com.crossover.trial.weather.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Basic airport information.
 *
 * @author code test administrator
 */
public class AirportData {

  /**  the three letter IATA code. */
  private String iata;

  /**  latitude value in degrees. */
  private double latitude;

  /**  longitude value in degrees. */
  private double longitude;

  /**
   * Instantiates a new airport data.
   */
  public AirportData() {
    super();
  }

  /**
   * Instantiates a new airport data.
   *
   * @param iata the iata
   * @param latitude the latitude
   * @param longitude the longitude
   */
  public AirportData(String iata, double latitude, double longitude) {
    super();
    this.iata = iata;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  /**
   * Gets the iata.
   *
   * @return the iata
   */
  public String getIata() {
    return iata;
  }

  /**
   * Sets the iata.
   *
   * @param iata the new iata
   */
  public void setIata(String iata) {
    this.iata = iata;
  }

  /**
   * Gets the latitude.
   *
   * @return the latitude
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * Sets the latitude.
   *
   * @param latitude the new latitude
   */
  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  /**
   * Gets the longitude.
   *
   * @return the longitude
   */
  public double getLongitude() {
    return longitude;
  }

  /**
   * Sets the longitude.
   *
   * @param longitude the new longitude
   */
  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((iata == null) ? 0 : iata.hashCode());
    long temp;
    temp = Double.doubleToLongBits(latitude);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(longitude);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AirportData other = (AirportData) obj;
    if (iata == null) {
      if (other.iata != null)
        return false;
    } else if (!iata.equals(other.iata))
      return false;
    if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
      return false;
    if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
      return false;
    return true;
  }

  

}
