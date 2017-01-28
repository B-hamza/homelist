package model
import play.api.libs.json._

sealed trait TypeLogement {
  def leboncoinProp: String
}
case object Maison extends TypeLogement {
  def leboncoinProp: String = "1"

}
case object Appartement extends TypeLogement {
  def leboncoinProp: String = "2"
}
object TypeLogement {
   def apply(typelog: String): TypeLogement = typelog match {
     case "maison" | "Maison" => Maison
     case "appartement" | "Appartement" => Appartement
   }

   val writes = Writes[TypeLogement] {
     case Maison => JsString("Maison")
     case Appartement => JsString("Appartement")
   }
   val reads = Reads[TypeLogement] {
     case JsString(s) => s match {
       case "Maison" => JsSuccess(Maison)
       case "Appartement" => JsSuccess(Appartement)
     }
     case _ => JsError("can not read propertie logement")
   }
   implicit val typeLogementFmt = Format(reads, writes)
}

case class SearchProperties(
    codePostal: Option[String],
    typeLogement: Seq[TypeLogement],
    city: Option[String],
    surface: Option[String],
    loyerMin: Option[String],
    loyerMax: Option[String],
    ges: Option[String])

object SearchProperties {
  implicit val propertiesFormat = Json.format[SearchProperties]

  def getLeBoncoinProp(prop: SearchProperties): String = {
    val _cp: Option[String] = prop.codePostal.map(cp => "location=" + cp)
    val _city: Option[String] = prop.city.map(c => "location=" + c)
    val _logement: String = prop.typeLogement.foldLeft("") {
      case ("", t) => "ret=" + t.leboncoinProp
      case (acc, t) => acc + "&ret=" + t.leboncoinProp
    }
    val _logOpt = if(_logement.isEmpty) None else Some(_logement)
    List(_cp, _city, _logOpt).flatten.foldLeft("?")((acc, el) => acc + "&" + el)
  }
}


