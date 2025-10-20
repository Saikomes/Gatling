package otus

import io.gatling.core.Predef._
import io.gatling.core.feeder.{BatchableFeederBuilder, FileBasedFeederBuilder}
import io.gatling.core.structure.ChainBuilder

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.LocalDate

object Feeders {
  val user: FileBasedFeederBuilder[Any] = jsonFile("testUser.json").random
  val passenger: BatchableFeederBuilder[String] = csv("testPassenger.csv").random

  val fmt: DateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
  val setDates: ChainBuilder = exec { s =>
    val today = LocalDate.now()
    s
      .set("departDate", today.plusDays(1).format(fmt))
      .set("returnDate", today.plusDays(2).format(fmt))
  }

  val setPassengerFullName: ChainBuilder = exec { s =>
    val fn = s("firstName").as[String]
    val ln = s("lastName").as[String]
    s.set("passengerFullName", s"$fn $ln")
  }
}
