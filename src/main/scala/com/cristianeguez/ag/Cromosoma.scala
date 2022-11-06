package com.cristianeguez.ag

import com.cristianeguez.ag.Constantes.CANTIDAD_ACTIVIDADES_A_REALIZAR

import scala.util.Random

case class Cromosoma(
                      actividades: Seq[Actividad]
                    ) extends AptitudSerializer {

  private val penalizacionPorRepetidos = actividades.groupBy(_.id).collect {
    case (_, actividades) if actividades.size > 1 => actividades.map(_.aptitud).sum
  }.sum * 2

  lazy val id: String = actividades.map(_.id).mkString("-")

  lazy val aptitud: Double = actividades.foldLeft(0.0) { case (accum, actividad) =>
    accum + actividad.aptitud
  } - penalizacionPorRepetidos

  def mutar(nuevaActividad: Actividad, indiceDeActividadAfectada: Int): Cromosoma = {

    val actividadAfectada = actividades(indiceDeActividadAfectada)

    copy(
      actividades = actividades.map { actividad =>
        if (actividad.id != actividadAfectada.id)
          actividad
        else
          nuevaActividad
      }
    )

  }

  override def toString = s"id-compuesto: [$id]aptitud: [$aptitudSerialized]"

}

object Cromosoma {

  def from(actividades: Seq[Actividad]) = Cromosoma(
    actividades = actividades
  )

  def init(actividades: Seq[Actividad]): Cromosoma = {

    var actividadesDelCromosoma = Seq.empty[Actividad]

    while (actividadesDelCromosoma.size < CANTIDAD_ACTIVIDADES_A_REALIZAR) {
      val actividadSeleccionada = actividades(Random.nextInt(actividades.size))
      if (!actividadesDelCromosoma.contains(actividadSeleccionada)) // se generan cromosomas con actividades distintas
        actividadesDelCromosoma = actividadesDelCromosoma :+ actividadSeleccionada
    }

    Cromosoma(
      actividades = actividadesDelCromosoma
    )

  }

}
