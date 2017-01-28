package services.parser

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Element
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.{element, elementList}
import net.ruippeixotog.scalascraper.dsl.DSL._

object Main {
  val browser = JsoupBrowser()
  val doc = browser.get("http://www.leboncoin.fr/locations/offres/ile_de_france/?th=1&parrot=0&ret=2")

  def main(args: Array[String]): Unit = {
    val items: Option[List[Element]] = for {
      section <- doc >?> element(".tabsContent")
      li <- section >?> elementList("li")
    } yield li

    println(Resume.parse(doc))

  }
}
