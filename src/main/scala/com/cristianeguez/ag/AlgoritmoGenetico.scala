package com.cristianeguez.ag

import com.cristianeguez.ag.Constantes._
import com.typesafe.scalalogging.StrictLogging

import scala.util.Random

trait AlgoritmoGenetico {

  this: StrictLogging =>

  val poolDeActividades: Map[Int, Actividad]
  val actividades: Seq[Actividad]
  val ITERACIONES_MAXIMAS: Int
  val TIEMPO_LIMITE_EN_SEGUNDOS: Int
  val VALOR_APTITUD_ACEPTABLE: Int

  def correrAlgoritmoGenetico: Seq[Cromosoma] = {

    logger.info(s"---------------EJECUTANDO ALGORITMO GENÉTICO---------------")

    val inicio = System.currentTimeMillis()

    var poblacion: Seq[Cromosoma] = generarPoblacionInicial()
    var i = 0

    while (seDebeContinuar(poblacion, inicio, i)) {
      logger.info(s"Iteracion $i")
      val seleccionados: Seq[Cromosoma] = METODO_DE_SELECCION.seleccionar(poblacion)
      val cromosomasCruzados: Seq[Cromosoma] = cruzar(seleccionados)
      i = i + 1
      poblacion = mutacion(cromosomasCruzados)
      logger.info(s"---------------RESULTADO DE LA ITERACION---------------")
      poblacion.foreach(cromosoma => logger.info(s"$cromosoma"))
      logger.info(s"---------------EL MEJOR DE LA ITERACION: ${poblacion.maxBy(_.aptitud)}---------------")
    }

    poblacion
  }

  private def seDebeContinuar(poblacion: Seq[Cromosoma], inicio: Long, iteraciones: Int): Boolean = {
    (System.currentTimeMillis() - inicio) < TIEMPO_LIMITE_EN_SEGUNDOS * 1000 &&
      iteraciones < ITERACIONES_MAXIMAS &&
      poblacion.forall(_.aptitud < VALOR_APTITUD_ACEPTABLE)
  }

  private def generarPoblacionInicial(): Seq[Cromosoma] = {
    val poblacionInicial = (0 to CANTIDAD_POBLACION_INICIAL).map(_ => Cromosoma.init(actividades))
    logger.info("---------------POBLACION INICIAL---------------")
    poblacionInicial.foreach(cromosoma => logger.info(s"$cromosoma"))
    poblacionInicial
  }

  private def cruzar(seleccionados: Seq[Cromosoma]): Seq[Cromosoma] = seleccionados.toList match {
    case Nil => Nil
    case cromosoma :: Nil => METODO_DE_CRUZAMIENTO.cruzar(cromosoma, seleccionados.head)
    case cromosoma :: otroCromosoma :: others => METODO_DE_CRUZAMIENTO.cruzar(cromosoma, otroCromosoma) ++ cruzar(others)
  }

  private def mutacion(cruzados: Seq[Cromosoma]): Seq[Cromosoma] = {

    if (Random.nextDouble() > PROBABILIDAD_DE_MUTACION) return cruzados

    logger.info("---------------SE EJECUTA MUTACION---------------")

    val nuevaActividad: Actividad = poolDeActividades(Random.nextInt(actividades.size))
    val actividadAMutarIndiceMatriz = Random.nextInt(CANTIDAD_DE_SOLUCIONES_A_SELECCIONAR * CANTIDAD_ACTIVIDADES_A_REALIZAR)
    val (fila, columna) = encontrarIndices(actividadAMutarIndiceMatriz)

    val cromosomaAfectado: Cromosoma = cruzados(fila)

    cruzados.map { cromosoma =>
      if (cromosoma.id != cromosomaAfectado.id)
        cromosoma
      else
        cromosoma.mutar(nuevaActividad, columna)
    }

  }

  /**
   * El 1er numero de la tupla corresponde a la fila o numero de cromosa
   * El 2do numero de la tupla correponde a la columna o numero de actividad dentro del cromosoma
   * Soporta que se generen números mayores a la cantidad de genes en la solución.
   */
  private def encontrarIndices(n: Int): (Int, Int) = {
    val filas = CANTIDAD_DE_SOLUCIONES_A_SELECCIONAR
    val columnas = CANTIDAD_ACTIVIDADES_A_REALIZAR
    val totalMatriz = filas * columnas
    val restoParaFila = if (n > totalMatriz) n % totalMatriz else n
    val fila = if (restoParaFila < columnas) 0 else restoParaFila % columnas
    fila -> n % columnas
  }

}
