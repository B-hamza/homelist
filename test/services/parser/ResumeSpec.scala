package services.parser

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import org.scalatest._

class ResumeSpec extends FlatSpec {
  val browser = JsoupBrowser()
  val doc = browser.get("http://www.leboncoin.fr/locations/offres/ile_de_france/?th=1&parrot=0&ret=2")

  "Resume" should "parse document from leboncoin" in {
      assert(Resume.parse(doc).isRight)
  }

  "Resume" should "find elements in leboncoin" in {
    assert(Resume.parse(doc) match {
      case Right(list) => list.nonEmpty
      case Left(e) => false
    })
  }

}
