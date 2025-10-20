package otus

import io.gatling.core.Predef.{atOnceUsers, closedInjectionProfileFactory, openInjectionProfileFactory}
import io.gatling.core.scenario.Simulation

class Debug extends Simulation{
  setUp(
    CommonScenario()
      .inject(atOnceUsers(1))
  ).protocols(otus.httpProtocol)

}
