package com.carrati.chucknorrisfacts.presentationTest

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class MockServerDispatcher {

    //  response dispatcher
    class ResponseDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            if (request.path.equals("/random", true))
                return MockResponse().setResponseCode(200).setBody(
                    "{\n" +
                            "  \"categories\" : [],\n" +
                            "  \"created_at\" : \"2020-01-05 13:42:18.823766\",\n" +
                            "  \"icon_url\" : \"https://assets.chucknorris.host/img/avatar/chuck-norris.png\",\n" +
                            "  \"id\" : \"rkG_VAn9TGiJXq3glYEX5A\",\n" +
                            "  \"updated_at\" : \"2020-01-05 13:42:18.823766\",\n" +
                            "  \"url\" : \"https://api.chucknorris.io/jokes/rkG_VAn9TGiJXq3glYEX5A\",\n" +
                            "  \"value\" : \"Chuck Norris CAN see John Cena.\"\n" +
                            "}"
                )
            return MockResponse().setResponseCode(400)
        }
    }

    //  error dispatcher
    class ErrorDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse = MockResponse().setResponseCode(400)
    }

}