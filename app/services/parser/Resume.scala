package services.parser

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.{Document, Element}
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._

import model.{Resume => ResumeModel}


case class ParseError(tagElement: String, msg: String)

object Resume {

  val browser = JsoupBrowser()
  val doc = browser.get("http://www.leboncoin.fr/locations/offres/ile_de_france/?th=1&parrot=0&ret=2")

  def parse(page: Document): Either[ParseError, List[Option[ResumeModel]]] = {
    val items: Either[ParseError, List[Element]]  = for {
      section <- (page >?> element(".tabsContent")).toRight(ParseError(".tabsContent", "can not parse global section container")).right
      li <- (section >?> elementList("li")).toRight(ParseError(".li", "can not parse elements li in the global section")).right
    } yield li
    items.right.map(_.map(parseResumeElement))
  }

  def parseResumeElement(item: Element) : Option[ResumeModel] = {
    for {
      headline <- item >?> element("a")
      info <- headline >?> text(".item_infos")
      price <- headline >?> text(".item_price")
      url = headline.attr("href")
    } yield ResumeModel(info, price, url, false, "leboncoin.fr")
  }

  def main(args: Array[String]): Unit = {
    val items: Option[List[Element]] = for {
      section <- doc >?> element(".tabsContent")
      li <- section >?> elementList("li")
    } yield li

    println(parse(doc))

  }
}


