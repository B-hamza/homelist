package services.stream

import akka.stream._
import akka.stream.scaladsl._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.ActorSystem

object publisher {

  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()

  def main(args: Array[String]): Unit = {

    val filename = "resources/text-source.txt"
    for (line <- scala.io.Source.fromFile(filename).getLines()) {
      println(line)
    }

    val text =
      """|Lorem Ipsum is simply dummy text of the printing and typesetting industry.
         |Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
         |when an unknown printer took a galley of type and scrambled it to make a type
         |specimen book.""".stripMargin

    Source.fromIterator(() => text.split("\\s").iterator).
      map(_.toUpperCase).
      runForeach(println).
      onComplete(_ => system.terminate())
  }

}
