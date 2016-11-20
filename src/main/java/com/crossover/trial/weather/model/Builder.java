package com.crossover.trial.weather.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The Class Builder.
 */
public class Builder {

  /** The first. */
  private int first;

  /** The mean. */
  private int mean;

  /** The median. */
  private int median;

  /** The last. */
  private int last;

  /** The count. */
  private int count;

  /**
   * Instantiates a new builder.
   */
  public Builder() {
    super();
  }

  /**
   * Instantiates a new builder.
   *
   * @param first the first
   * @param mean the mean
   * @param median the median
   * @param last the last
   * @param count the count
   */
  public Builder(int first, int mean, int median, int last, int count) {
    super();
    this.first = first;
    this.mean = mean;
    this.median = median;
    this.last = last;
    this.count = count;
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
   * Gets the mean.
   *
   * @return the mean
   */
  public int getMean() {
    return mean;
  }

  /**
   * Sets the mean.
   *
   * @param mean the new mean
   */
  public void setMean(int mean) {
    this.mean = mean;
  }

  /**
   * Gets the median.
   *
   * @return the median
   */
  public int getMedian() {
    return median;
  }

  /**
   * Sets the median.
   *
   * @param median the new median
   */
  public void setMedian(int median) {
    this.median = median;
  }

  /**
   * Gets the last.
   *
   * @return the last
   */
  public int getLast() {
    return last;
  }

  /**
   * Sets the last.
   *
   * @param last the new last
   */
  public void setLast(int last) {
    this.last = last;
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

  /**
   * Builds DataPoint from builder
   *
   * @return the data point
   */
  public DataPoint build() {
    return new DataPoint(this.first, this.mean, this.median, this.last, this.count);
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
    result = prime * result + last;
    result = prime * result + mean;
    result = prime * result + median;
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
    Builder other = (Builder) obj;
    if (count != other.count)
      return false;
    if (first != other.first)
      return false;
    if (last != other.last)
      return false;
    if (mean != other.mean)
      return false;
    if (median != other.median)
      return false;
    return true;
  }

}
