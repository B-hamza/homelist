package model

import play.api.libs.json._

package object models {
  type URL = String
  sealed trait Source
  object Source {
    def apply(name: String): Source = name match {
      case "leboncoin.fr" => Leboncoin
      case "pap" => PAP
      case "seloger" => SeLoger
    }

    val writes = Writes[Source] {
      case Leboncoin => JsString("leboncoin.fr")
      case PAP => JsString("pap.fr")
      case SeLoger => JsString("seloger.fr")
    }
    val reads = Reads[Source] {
      case JsString(s) => s match {
        case "leboncoin.fr" => JsSuccess(Leboncoin)
        case "pap.fr" => JsSuccess(PAP)
        case "seloger.fr" => JsSuccess(SeLoger)
      }
    }
    implicit val sourceFmt = Format(reads, writes)
  }
  case object Leboncoin extends Source
  case object PAP extends Source
  case object SeLoger extends Source
}

import models._

case class Resume(title: String, price: Option[String], url: URL, isPro: Boolean, source: Source)

object Resume {
  def getNewResumes(viewedUrls: List[models.URL], resumes: List[Resume]): List[Resume] = {
    val urls: List[models.URL] = resumes.map(_.url)
    val newUrls = urls diff viewedUrls
    resumes.filter(resume => newUrls.contains(resume.url))
  }

  implicit val resumeFormat = Json.format[Resume]
}