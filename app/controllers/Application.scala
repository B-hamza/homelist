package controllers
import model.SearchProperties
import play.api.mvc._
import play.api.libs.json._
import javax.inject._

import play.api.Configuration

import scala.concurrent.{ExecutionContext, Future}

class Application @Inject()(conf: Configuration)(implicit exec: ExecutionContext) extends Controller {

  def index = Action {
    Ok(views.html.index("Hello"))
  }

  def parseElement = Action.async(parse.json[SearchProperties]) { request =>
    val properties = request.body


    Future.successful(Ok(Json.toJson("test")))
  }

  def constructUrl(properties: SearchProperties): Option[String] = {
    val urlOpt = conf.getString("urls.leboncoin")
    urlOpt.map(url => url + SearchProperties.getLeBoncoinProp(properties))

  }

}
