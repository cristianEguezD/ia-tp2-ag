package com.cristianeguez.ag

import com.fasterxml.jackson.annotation.JsonIgnore

trait AptitudSerializer {

  def aptitud: Double

  @JsonIgnore
  lazy val aptitudSerialized: String = new java.math.BigDecimal(aptitud).setScale(2, java.math.BigDecimal.ROUND_HALF_UP).toString

}
