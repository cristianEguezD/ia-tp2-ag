package com.cristianeguez.ag

import com.cristianeguez.ag.Constantes.CANTIDAD_DE_ACTIVIDADES_TOTALES
import com.typesafe.scalalogging.StrictLogging

object Main extends App with AlgoritmoGenetico with Loader with StrictLogging {

  // formas de paro del AG
  val ITERACIONES_MAXIMAS: Int = 500000
  val TIEMPO_LIMITE_EN_SEGUNDOS: Int = 60 * 10
  val VALOR_APTITUD_ACEPTABLE: Int = 270

  val actividades: Seq[Actividad] = cargarActividadesDeJson
  val poolDeActividades: Map[Int, Actividad] = actividades.map(a => a.id -> a).toMap

  val resultado = correrAlgoritmoGenetico

  logger.info("---------------POBLACION FINAL---------------")
  resultado.foreach(cromosoma => logger.info(s"$cromosoma"))
  logger.info("---------------MEJOR RESULTADO---------------")
  val mejorResultado: Cromosoma = resultado.maxBy(_.aptitud)
  logger.info(s"$mejorResultado")
  logger.info(s"Sus actividades son: ${mejorResultado.actividades}")

  // aux para enerar las actividades
  def generarPoolDeActividades: Map[Int, Actividad] = {
    val actividades: Seq[Actividad] = (0 until CANTIDAD_DE_ACTIVIDADES_TOTALES).map(Actividad.from)
    logger.info("---------------ACTIVIDADES---------------")
    actividades.foreach(actividad => logger.info(s"$actividad"))
    actividades.map(a => a.id -> a).toMap
  }

}
