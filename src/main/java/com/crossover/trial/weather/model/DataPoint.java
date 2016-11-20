package com.crossover.trial.weather.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A collected point, including some information about the range of collected values.
 *
 * @author code test administrator
 */
public class DataPoint {

  /** The mean. */
  private double mean;

  /** The first. */
  private int first;

  /** The second. */
  private int second;

  /** The third. */
  private int third;

  /** The count. */
  private int count;

  /**
   * public constructor, use the builder to create this object.
   */
  public DataPoint() {
    super();
  }

  /**
   * Instantiates a new data point.
   *
   * @param first the first
   * @param second the second
   * @param mean the mean
   * @param third the third
   * @param count the count
   */
  public DataPoint(int first, int second, double mean, int third, int count) {
    super();
    this.mean = mean;
    this.first = first;
    this.second = second;
    this.third = third;
    this.count = count;
  }

  /**
   * Gets the mean.
   *
   * @return the mean
   */
  public double getMean() {
    return mean;
  }

  /**
   * Sets the mean.
   *
   * @param mean the new mean
   */
  public void setMean(double mean) {
    this.mean = mean;
  }

  /**
   * Gets the first.
   *
   * @return the first
   */
  public int getFirst() {
    return first;
  }

  /**
   * Sets the first.
   *
   * @param first the new first
   */
  public void setFirst(int first) {
    this.first = first;
  }

  /**
   * Gets the second.
   *
   * @return the second
   */
  public int getSecond() {
    return second;
  }

  /**
   * Sets the second.
   *
   * @param second the new second
   */
  public void setSecond(int second) {
    this.second = second;
  }

  /**
   * Gets the third.
   *
   * @return the third
   */
  public int getThird() {
    return third;
  }

  /**
   * Sets the third.
   *
   * @param third the new third
   */
  public void setThird(int third) {
    this.third = third;
  }

  /**
   * Gets the count.
   *
   * @return the count
   */
  public int getCount() {
    return count;
  }

  /**
   * Sets the count.
   *
   * @param count the new count
   */
  public void setCount(int count) {
    this.count = count;
  }

  /*
   * (non-Javadoc)
   * 
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
    result = prime * result + count;
    result = prime * result + first;
    long temp;
    temp = Double.doubleToLongBits(mean);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + second;
    result = prime * result + third;
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
    DataPoint other = (DataPoint) obj;
    if (count != other.count)
      return false;
    if (first != other.first)
      return false;
    if (Double.doubleToLongBits(mean) != Double.doubleToLongBits(other.mean))
      return false;
    if (second != other.second)
      return false;
    if (third != other.third)
      return false;
    return true;
  }

}
