package model

package object models {
  type URL = String
}
case class Resume(title: String, price: String, url: models.URL, isPro: Boolean, source: String)

object Resume {
  def getNewResumes(viewedUrls: List[models.URL], resumes: List[Resume]): List[Resume] = {
    val urls: List[models.URL] = resumes.map(_.url)
    val newUrls = urls diff viewedUrls
    resumes.filter(resume => newUrls.contains(resume.url))
  }
}