package com.cristianeguez.ag

import com.cristianeguez.ag.Constantes.{FELICIDAD_MAXIMA, PRECIO_MAXIMO}
import com.fasterxml.jackson.annotation.JsonIgnore

import scala.util.Random

case class Actividad(id: Int, nombre: String, precio: Double, felicidadDeRetorno: Int) extends AptitudSerializer {

  @JsonIgnore
  lazy val aptitud: Double = felicidadDeRetorno / precio

  override def toString = s"id: [$id] aptitud: [$aptitudSerialized] precio: [$precio] felicidad: [$felicidadDeRetorno]"
}

object Actividad {
  def from(id: Int) = Actividad(
    id = id,
    nombre = s"actividad_$id",
    precio = Random.nextDouble() * PRECIO_MAXIMO, // de 0 a PRECIO_MAXIMO
    felicidadDeRetorno = (Random.nextDouble() * FELICIDAD_MAXIMA).toInt // de 0 a FELICIDAD_MAXIMA
  )
}
