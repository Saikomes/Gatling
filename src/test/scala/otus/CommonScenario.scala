package otus
import io.gatling.core.Predef.scenario
import io.gatling.core.session.Session
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt
object CommonScenario {
  def apply(): ScenarioBuilder = new CommonScenario().commonScenario
}

class CommonScenario {

  private val openMain: ChainBuilder =
    exec(Actions.mainPageWelcome, Actions.mainPageNav)

  private val doLogin: ChainBuilder =
    feed(Feeders.user)
      .exec(Actions.loginPost, Actions.loginNav, Actions.loginGet)

  private val goToFlights: ChainBuilder =
    exec(Actions.flightsWelcome, Actions.flightsNav, Actions.flightsReservations)

  private val searchRoundTrip: ChainBuilder =
    exec(Feeders.setDates, CitySelector.selectRandomCities, Actions.searchRoundTripFlight)

  private val selectRound: ChainBuilder =
    exec(Actions.selectFlightRound)

  private val payRound: ChainBuilder =
      feed(Feeders.passenger)
      .exec(Feeders.setPassengerFullName)
      .exec(Actions.paymentDetailsRound)

  private val backHome: ChainBuilder =
    exec(Actions.returnMainWelcome, Actions.returnMainLogin, Actions.returnMainNav)

  private val groupLogin: ChainBuilder =
    group("TXN_Login") {
      exec(openMain, doLogin)
    }

  private val selectAndBuyLoop: ChainBuilder =
    forever {
      group("TXN_SelectAndBuy") {
        exec(
          goToFlights,
          searchRoundTrip,
          selectRound,
          payRound,
          backHome
        )
      }
        .pace(500.millis, 1500.millis)
    }

  val commonScenario: ScenarioBuilder =
    scenario("Select and buy round flight ticket")
      .exec(groupLogin)
      .exec(selectAndBuyLoop)
}
