package restassured;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ecom {
    String token;

   @BeforeSuite
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
    token = response.jsonPath().getString("[0].token");
    System.out.println("Token: " + token);
   }
   @Test
   public void toPostObject(){

    String requestBody = "{\n" + //
                "\n" + //
                " \"name\": \"Appleku\",\n" + //
                " \"data\": {\n" + //
                " \"year\": 2019,\n" + //
                " \"price\": 1849.99,\n" + //
                " \"cpu_model\": \"Intel Core i9\",\n" + //
                " \"hard_disk_size\": \"1 TB\",\n" + //
                " \"capacity\": \"2 cpu\",\n" + //
                " \"screen_size\": \"14 Inch\",\n" + //
                " \"color\": \"red\"\n" + //
                " }\n" + //
                "}";

    RestAssured.baseURI = "https://whitesmokehouse.com";
    Response response = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + token)
            .body(requestBody)
            .log().all()
            .when()
            .post("/api/objects");
    //then validate the response
    System.out.println("Response: " + response.asPrettyString());
    assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();
    assert response.jsonPath().getString("name").equals("Appleku") : "Expected name Appleku but got " + response.jsonPath().getString("name");
   }

   
   @Test
   
   public void testGetListObject() {
       // Define the endpoint URL
        RestAssured.baseURI = "https://whitesmokehouse.com";
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get("/api/objects");

//get the response
       System.out.println("Response: " + response.asPrettyString());
       //validate the response 
       // assert status code : 200
       assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();
       assert response.jsonPath().getString("[0].name").equals("Appleku") : "Expected name Apple MacBook Pro 16 but got " + response.jsonPath().getString("[0].email");
       }

       @Test
   
   public void testGetSingleObject() {
       // Define the endpoint URL
        RestAssured.baseURI = "https://whitesmokehouse.com";
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/88");

//get the response
       System.out.println("Response: " + response.asPrettyString());
       //validate the response 
       // assert status code : 200
       assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();
       assert response.jsonPath().getString("[0].name").equals("Apple ") : "Expected name Apple MacBook Pro 16 but got " + response.jsonPath().getString("[0].email");
       }

    @Test
    public void testGetDepartment() {
        // Define the endpoint URL
        RestAssured.baseURI = "https://whitesmokehouse.com";
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get("/webhook/api/department");

                System.err.println("response: " + response.asPrettyString());
                assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();
                assert response.jsonPath().getString("[0].department").equals("Finance ") : "Expected name Apple MacBook Pro 16 but got " + response.jsonPath().getString("[0].email");

}

}