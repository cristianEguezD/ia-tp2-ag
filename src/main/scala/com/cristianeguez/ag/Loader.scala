package com.cristianeguez.ag

import com.cristianeguez.ag.ObjectMapper.mapper
import com.cristianeguez.utils.FileUtils
import com.typesafe.scalalogging.StrictLogging

trait Loader extends FileUtils {
  this: StrictLogging =>

  def cargarActividadesDeJson: Seq[Actividad] = {
    logger.info("---------------CARGANDO ACTIVIDADES---------------")
    val json = getResourceFileAsString("/actividades.json")
    val rs = mapper.readValue(json, classOf[Array[Actividad]]).toSeq
    logger.info("---------------LAS ACTIVIDADES SON---------------")
    rs.foreach(a => logger.info(s"$a"))
    rs
  }

}
