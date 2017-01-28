package model
import org.joda.time.DateTime

case class Event[IC, IS, C](idCommand: IC, idAggregate: IS, command: C, time: DateTime)
