package otus
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.{DurationInt, FiniteDuration}

class ReliabilitySimulation extends Simulation{

  val scn: ScenarioBuilder = CommonScenario()

  setUp(
    scn.inject(
      rampConcurrentUsers(0) to otus.reliabilityUsers during otus.reliabilityWarmUp,
      constantConcurrentUsers(otus.reliabilityUsers) during otus.reliabilitySteadyHold
    )
  )
    .protocols(otus.httpProtocol)
    .maxDuration(otus.reliabilityWarmUp + otus.reliabilitySteadyHold + 30.seconds)
    .assertions(
      details("TXN_Login").responseTime.percentile2.lte(10000),
      details("TXN_Login").failedRequests.percent.lte(5),
      details("TXN_SelectAndBuy").responseTime.percentile2.lte(20000),
      details("TXN_SelectAndBuy").failedRequests.percent.lte(5),
      global.failedRequests.percent.lte(5)
    )

}
