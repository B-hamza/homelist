package services.parser

import scala.util.{Success, Try}

object Error {
  type ParseError = String
}
import Error._
trait Parser[D, T] {
 def apply(s: D): Either[ParseError, T]
}

trait TryParser[D, T] extends Parser[D, T] {

  def parse(s: D): T

  def apply(s: D): Either[ParseError, T] =
    Try(parse(s)).transform(
      s => Success(Right(s)),
      f => Success(Left(f.getMessage))).get

 /* def apply(s: List[D]): Either[List[ParseError], List[T]] = {
    val list: List[Either[ParseError, T]] = s.map(apply).
  }*/
}
