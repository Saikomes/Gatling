package otus

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration.{DurationInt, FiniteDuration}

object otus {
  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("http://webtours.load-test.ru:1080")
    .acceptLanguageHeader("ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .contentTypeHeader("application/x-www-form-urlencoded")

  var maxUsers = 70
  var maxSearchSteps = 10
  var maxSearchStepDuration = 60
  var maxSearchRampDuration = 60
  var reliabilityUsers: Int = Math.round((maxUsers - maxUsers / maxSearchSteps) * 0.8).toInt
  var reliabilityWarmUp: FiniteDuration = 5.minutes
  var reliabilitySteadyHold: FiniteDuration = 60.minutes

}
