package model

case class Detail(description: Option[String], images: Seq[models.URL], properties: Map[String, String])

