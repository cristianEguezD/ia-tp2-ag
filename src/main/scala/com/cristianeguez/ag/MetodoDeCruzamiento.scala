package com.cristianeguez.ag

import com.cristianeguez.ag.Constantes.CANTIDAD_ACTIVIDADES_A_REALIZAR
import com.cristianeguez.ag.CruzamientoMultiPunto.PUNTOS_DE_CORTE

trait MetodoDeCruzamiento {

  /** normalmente dan 2 cromosomas como resultado */
  def cruzar(cromosoma: Cromosoma, otherCromosoma: Cromosoma): Seq[Cromosoma]

}

object MetodoDeCruzamiento {

  def cruzamientoSimple: MetodoDeCruzamiento = CruzamientoSimple

  def cruzamientoMultiPunto(puntosDeCorte: Seq[Int]): MetodoDeCruzamiento = CruzamientoMultiPunto.from(puntosDeCorte)

}

private object CruzamientoSimple extends MetodoDeCruzamiento {

  private lazy val PUNTO_DE_CORTE: Int = CANTIDAD_ACTIVIDADES_A_REALIZAR / 2

  def cruzar(cromosoma: Cromosoma, otherCromosoma: Cromosoma): Seq[Cromosoma] = {

    val (firstsActivitiesCromosoma, otherActivitiesCromosoma) = cromosoma.actividades.splitAt(PUNTO_DE_CORTE)
    val (firstsActivitiesOtherCromosoma, otherActivitiesOtherCromosoma) = otherCromosoma.actividades.splitAt(PUNTO_DE_CORTE)

    Cromosoma.from(firstsActivitiesCromosoma ++ otherActivitiesOtherCromosoma) :: Cromosoma
      .from(firstsActivitiesOtherCromosoma ++ otherActivitiesCromosoma) :: Nil
  }

}

private object CruzamientoMultiPunto extends MetodoDeCruzamiento {

  private var PUNTOS_DE_CORTE: Seq[Int] = Seq.empty

  def from(puntosDecorte: Seq[Int]): MetodoDeCruzamiento = {
    if (puntosDecorte.size != puntosDecorte.toSet.size) throw new RuntimeException("Los puntos de corte deberian ser distintos.")
    if (puntosDecorte.sorted != puntosDecorte) throw new RuntimeException("Los puntos de corte deberian estar ordenados.")
    if (!puntosDecorte.forall(n => (1 until CANTIDAD_ACTIVIDADES_A_REALIZAR).contains(n))) throw new RuntimeException(s"Los puntos de corte deberian estar entre 1  y $CANTIDAD_ACTIVIDADES_A_REALIZAR.")
    PUNTOS_DE_CORTE = (0 +: puntosDecorte.sorted) :+ CANTIDAD_ACTIVIDADES_A_REALIZAR
    this
  }

  def cruzar(cromosoma: Cromosoma, otroCromosoma: Cromosoma): Seq[Cromosoma] = {

    val actividadesPrimerCromosoma = cromosoma.actividades
    val actividadesSegundoCromosoma = otroCromosoma.actividades

    val (secondNewChromosomeActivities, firstNewChromosomeActivities) = PUNTOS_DE_CORTE.sliding(2)
      .foldLeft((Seq.empty[Actividad], Seq.empty[Actividad])) { case ((firstAccum, secondAccum), from :: to :: Nil) =>
        (secondAccum ++ actividadesSegundoCromosoma.slice(from, to)) -> (firstAccum ++ actividadesPrimerCromosoma.slice(from, to))
      }

    Cromosoma.from(firstNewChromosomeActivities) :: Cromosoma.from(secondNewChromosomeActivities) :: Nil

  }

}
