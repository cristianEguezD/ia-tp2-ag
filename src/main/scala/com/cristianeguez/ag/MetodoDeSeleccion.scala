package com.cristianeguez.ag

import com.cristianeguez.ag.Constantes.CANTIDAD_DE_SOLUCIONES_A_SELECCIONAR

trait MetodoDeSeleccion {
  def seleccionar(cromosomas: Seq[Cromosoma]): Seq[Cromosoma]
}

object MetodoDeSeleccion {

  def torneo: MetodoDeSeleccion = TORNEO

  def ranking: MetodoDeSeleccion = RANKING

}

private object TORNEO extends MetodoDeSeleccion {

  def seleccionar(cromosomas: Seq[Cromosoma]): Seq[Cromosoma] = {

    def ejecutarRonda(competidores: Seq[Cromosoma]): Seq[Cromosoma] = competidores.toList match {
      case Nil => Nil
      case x :: Nil => x :: Nil
      case x :: y :: others =>
        if (x.aptitud > y.aptitud)
          x +: ejecutarRonda(others)
        else
          y +: ejecutarRonda(others)
    }

    var ganadores: Seq[Cromosoma] = cromosomas

    /**
     * Como al ejecutar una ronda pierdo a la mitad de competidores corto antes de perder más de los que quiero
     */
    while (ganadores.size > CANTIDAD_DE_SOLUCIONES_A_SELECCIONAR * 2) {
      ganadores = ejecutarRonda(ganadores)
    }

    RANKING.seleccionar(ganadores)

  }

}

private object RANKING extends MetodoDeSeleccion {

  def seleccionar(cromosomas: Seq[Cromosoma]): Seq[Cromosoma] = {

    cromosomas
      .sortBy(_.aptitud) // ordena de menor a mayor
      .takeRight(CANTIDAD_DE_SOLUCIONES_A_SELECCIONAR)

  }

}
