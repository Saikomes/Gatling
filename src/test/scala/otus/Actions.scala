package otus
import io.gatling.core.Predef.{css, _}
import io.gatling.http.Predef._
import io.gatling.http.request.builder.{Http, HttpRequestBuilder}


object Actions {
  val mainPageWelcome: HttpRequestBuilder = http("UC01_RootPage-1")
    .get("/cgi-bin/welcome.pl")
    .queryParam("signOff", "true")
    .check(status.is(200))

  val mainPageNav: HttpRequestBuilder = http("UC01_RootPage-2")
    .get("/cgi-bin/nav.pl")
    .queryParam("in", "home")
    .check(
      css("input[name='userSession']", "value")
        .saveAs("userSession")
    )
    .check(status.is(200))

  val loginPost: HttpRequestBuilder = http("UC02_Login-1")
    .post("/cgi-bin/login.pl")
//    .formParam("userSession", "#{userSession}")
//    .formParam("username", "#{username}")
//    .formParam("password", "#{password}")
//    .formParam("login.x", "47")
//    .formParam("login.y", "4")
//    .formParam("JSFormSubmit", "off")
    .body(ElFileBody("bodies/login_form.txt"))
    .check(status.is(200))

  val loginNav: HttpRequestBuilder = http("UC02_Login-2")
    .get("/cgi-bin/nav.pl")
    .queryParam("in", "home")
    .queryParam("page", "menu")
    .check(status.is(200))

  val loginGet: HttpRequestBuilder = http("UC02_Login-3")
    .get("/cgi-bin/login.pl")
    .queryParam("intro", "true")
    .check(status.is(200))

  val flightsWelcome: HttpRequestBuilder = http("UC03_Flights-1")
    .get("/cgi-bin/welcome.pl")
    .queryParam("page", "search")
    .check(status.is(200))

  val flightsNav: HttpRequestBuilder = http("UC03_Flights-2")
    .get("/cgi-bin/nav.pl")
    .queryParam("in", "flights")
    .queryParam("page", "menu")
    .check(status.is(200))

  val flightsReservations: HttpRequestBuilder = http("UC03_Flights-3")
    .get("/cgi-bin/reservations.pl")
    .queryParam("page", "welcome")
    .check(
      css("select[name='depart'] option", "value")
        .findAll
        .saveAs("departure_cities"),
      css("select[name='arrive'] option", "value")
        .findAll
        .saveAs("arrival_cities")

    )

  val searchRoundTripFlight: HttpRequestBuilder = http("UC04_searchRoundTripFlight-1")
    .post("/cgi-bin/reservations.pl")
//    .formParam("advanceDiscount", "0")
//    .formParam("depart", "#{depart_city}")
//    .formParam("departDate", "10/08/2025")
//    .formParam("arrive", "#{arrive_city}")
//    .formParam("returnDate", "10/09/2025")
//    .formParam("numPassengers", "1")
//    .formParam("roundtrip", "on")
//    .formParam("seatPref", "None")
//    .formParam("seatType", "Coach")
//    .formParam("findFlights.x", "39")
//    .formParam("findFlights.y", "11")
//    .formParam(".cgifields", "roundtrip")
//    .formParam(".cgifields", "seatPref")
    .body(ElFileBody("bodies/searchRoundTrip_form.txt"))
    .check(
      css("input[name='outboundFlight']", "value")
        .findRandom
        .saveAs("selected_outbound_flight"),
      css("input[name='returnFlight']", "value")
        .findRandom
        .saveAs("selected_return_flight")

    )

  val selectFlightRound: HttpRequestBuilder = http("UC05_SelectFlightRound-1")
    .post("/cgi-bin/reservations.pl")
    .body(ElFileBody("bodies/selectFlight_form.txt"))
//    .formParam("outboundFlight", "#{selected_outbound_flight}")
//    .formParam("returnFlight", "#{selected_return_flight}")
//    .formParam("numPassengers", "1") .formParam("advanceDiscount", "0")
//    .formParam("seatType", "Coach") .formParam("seatPref", "None")
//    .formParam("reserveFlights.x", "40") .formParam("reserveFlights.y", "6")

  val paymentDetailsRound: HttpRequestBuilder = http("UC06_PaymentDetailsRound-1")
    .post("/cgi-bin/reservations.pl")
    .formParam("outboundFlight", "#{selected_outbound_flight}")
    .formParam("returnFlight", "#{selected_return_flight}")
    .formParam("firstName", "#{firstName}")
    .formParam("lastName", "#{lastName}")
    .formParam("address1", "#{address1}")
    .formParam("address2", "#{address2}")
    .formParam("pass1", "#{passengerFullName}")
    .formParam("numPassengers", "1")
    .formParam("creditCard", "#{creditCard}")
    .formParam("expDate", "#{expDate}")
    .formParam("oldCCOption", "")
    .formParam("seatType", "Coach")
    .formParam("seatPref", "None")
    .formParam("advanceDiscount", "0")
    .formParam("JSFormSubmit", "off")
    .formParam("buyFlights.x", "54")
    .formParam("buyFlights.y", "6")
    .formParam(".cgifields", "saveCC")
//    .body(ElFileBody("bodies/payment_form.txt")) не работает как должно, возможно какая-то проблема кодировок
    .check(status.is(200))



  val returnMainWelcome: HttpRequestBuilder = http("UC07_ReturnMain-1")
    .post("/cgi-bin/welcome.pl")
    .queryParam("page", "menus")
    .check(status.is(200))

  val returnMainLogin: HttpRequestBuilder = http("UC07_ReturnMain-2")
    .get("/cgi-bin/login.pl")
    .queryParam("intro", "true")
    .check(status.is(200))

  val returnMainNav: HttpRequestBuilder = http("UC07_ReturnMain-3")
    .get("/cgi-bin/nav.pl")
    .queryParam("in", "home")
    .queryParam("page", "menu")
    .check(status.is(200))

}
