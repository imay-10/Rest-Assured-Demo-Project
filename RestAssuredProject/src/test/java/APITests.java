import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class APITests {

    // Automate POST Request
    public String getToken;
    @Test
    public void authToken(){
        String payload = "{\n" + "  \"username\" : \"admin\",\n" +
                "   \"password\" : \"password123\"\n" + "}";
        Response response = given().
                baseUri("https://restful-booker.herokuapp.com").
                contentType("application/json").
                body(payload).
                when().
                post("/auth").
                then().
                log().all().extract().response();
        getToken = response.jsonPath().getString("token");
        System.out.println("Token : " + getToken);
    }

    // Automate GET Request
    @Test
    public void getbookingIds(){

        int bookingID = given().
                            baseUri("https://restful-booker.herokuapp.com").
                            contentType("application/json").
                        when().
                            get("/booking").
                        then().
                            extract().response().path("bookingid[0]");

        System.out.println("Booking ID : " + bookingID);
        Assert.assertEquals(bookingID, 1684);
    }

    /*
    // Automate PUT Request
    // Should generally be avoided in all circumstances
    @Test
    public void updateBooking(){
        String payload = "{\n" +
                "   \"firstname\" : \"James\",\n" +
                "   \"lastname\" : \"Brown\",\n" +
                "   \"totalprice\" : 444,\n" +
                "   \"depositpaid\" : true,\n" +
                "   \"bookingdates\" : {\n" +
                "       \"checkin\" : \"2018-01-01\",\n" +
                "       \"checkout\" : \"2019-01-01\"\n" +
                "   },\n" +
                "   \"additionalneeds\" : \"Breakfast\",\n" +
                "}";

        given().
                baseUri("https://restful-booker.herokuapp.com").
                contentType("application/json").
                body(payload).
                header("Cookie", "token= " + getToken).
                log().all().
        when().
                put("/booking/1").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }
    */

    // Automate PATCH Request
    @Test
    public void partialUpdateBooking(){
        String payload = "{\n" +
                "   \"firstname\" : \"Abhishek\",\n" +
                "   \"lastname\" : \"Chauhan\"\n" +
                "}";
        given().
                baseUri("https://restful-booker.herokuapp.com").
                contentType("application/json").
                header("Cookie", "token= " + getToken).
                body(payload).
                log().all().
        when().
                patch("/booking/15").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    // Automate DELETE Request
    @Test
    public void deleteBooking(){
        given().
                baseUri("https://restful-booker.herokuapp.com").
                contentType("application/json").
                header("Cookie", "token= "+getToken).
                log().all().
        when().
                delete("/booking/10").
        then().
                log().all().
                assertThat().
                statusCode(201);
    }
}
