package services.parser

import net.ruippeixotog.scalascraper.model.{Document, Element}
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._

import model.{Resume => ResumeModel}
import model.models._
import Error._

object Resume {

  def parse(page: Document): Either[ParseError, List[Option[ResumeModel]]] = {
    val items: Either[ParseError, List[Element]]  = for {
      section <- (page >?> element(".tabsContent")).toRight("can not parse global section container").right
      li <- (section >?> elementList("li")).toRight("can not parse elements li in the global section").right
    } yield li
    items.right.map(_.map(parseResumeElement))
  }

  def parseResumeElement(item: Element) : Option[ResumeModel] = {
    for {
      headline <- item >?> element("a")
      info <- headline >?> text(".item_infos")
      price = headline >?> text(".item_price")
      url = headline.attr("href")
    } yield ResumeModel(info, price, url, false, Leboncoin)
  }
}
