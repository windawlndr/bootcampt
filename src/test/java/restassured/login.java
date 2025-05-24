package restassured;

import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class login {
    String token;

   @Test
   public void setUp(){
/* define url
 * string baseUrl = "https://whitesmokehouse.com */
    RestAssured.baseURI = "https://whitesmokehouse.com";
    //login request bddy 
    String requestBody = "{\n" + //
                " \"email\": \"albertsimanjuntak12@gmail.com\",\n" + //
                " \"password\": \"@dmin123\"\n" + //
                "}";

    Response response = RestAssured.given()
            .contentType("application/json")
            .header("Content-Type", "application/json")
            .body(requestBody)
            .log().all()
            .when()
            .post("/webhook/api/login");
    token = response.jsonPath().getString("token");
    System.out.println("Token: " + token);
   }
}