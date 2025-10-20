package otus
import io.gatling.core.Predef._
import io.gatling.core.session.Session
import io.gatling.core.structure.ChainBuilder

import scala.util.Random

object CitySelector {

  def selectRandomCities: ChainBuilder = exec(session => {
    val departureCities = session("departure_cities").as[Vector[String]]
    val arrivalCities = session("arrival_cities").as[Vector[String]]

    if (departureCities.isEmpty || arrivalCities.isEmpty) {
      println("ERROR: No cities found in lists!")
      session
    } else {
      // Выбираем случайный город отправления
      val randomDepartIndex = Random.nextInt(departureCities.length)
      val departCity = departureCities(randomDepartIndex)

      // Выбираем случайный город прибытия, который не совпадает с городом отправления
      var arriveCity: String = null
      var arriveIndex: Int = 0

      do {
        arriveIndex = Random.nextInt(arrivalCities.length)
        arriveCity = arrivalCities(arriveIndex)
      } while (arriveCity == departCity && arrivalCities.length > 1)

//      println(s"Selected: depart=$departCity, arrive=$arriveCity")

      session
        .set("depart_city", departCity)
        .set("arrive_city", arriveCity)
    }
  })
}

