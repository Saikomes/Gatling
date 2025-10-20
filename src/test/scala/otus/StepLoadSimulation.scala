package otus
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class StepLoadSimulation extends Simulation {

  val scn: ScenarioBuilder = CommonScenario()


  setUp(
    scn.inject(
      incrementConcurrentUsers(otus.maxUsers / otus.maxSearchSteps)
        .times(otus.maxSearchSteps)
        .eachLevelLasting(otus.maxSearchStepDuration)
        .separatedByRampsLasting(otus.maxSearchRampDuration)
        .startingFrom(0) // Int
    )
  )
    .protocols(otus.httpProtocol)
    .assertions(
//      details("TXN_Login").responseTime.percentile3.lte(2000),
//      details("TXN_Login").failedRequests.percent.lte(5),
//      details("TXN_SelectAndBuy").responseTime.percentile3.lte(2000),
//      details("TXN_SelectAndBuy").failedRequests.percent.lte(5)
    )
    .maxDuration(20.minutes)
}
