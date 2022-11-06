package com.cristianeguez.ag

object Constantes {

  val CANTIDAD_ACTIVIDADES_A_REALIZAR: Int = 5 // 1 por día durante 5 días por ejemplo
  val CANTIDAD_DE_ACTIVIDADES_TOTALES: Int = CANTIDAD_ACTIVIDADES_A_REALIZAR * 10
  val CANTIDAD_POBLACION_INICIAL: Int = 100
  val CANTIDAD_DE_SOLUCIONES_A_SELECCIONAR: Int = 10
  val METODO_DE_SELECCION: MetodoDeSeleccion = TORNEO
  val METODO_DE_CRUZAMIENTO: MetodoDeCruzamiento = MetodoDeCruzamiento.cruzamientoSimple
  val PROBABILIDAD_DE_MUTACION: Double = 0.2

  val PRECIO_MAXIMO = 100000
  val FELICIDAD_MAXIMA = 100000

}
