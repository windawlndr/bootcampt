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
    token = response.jsonPath().getString("token");
    System.out.println("Token: " + token);
   }
   @Test
   public void toPostObject(){
    RestAssured.baseURI = "https://whitesmokehouse.com";
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

    
    Response response = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + token)
            .body(requestBody)
            .log().all()
            .when()
            .post("/webhook/api/objects");
    //then validate the response
    System.out.println("Response: " + response.asPrettyString());
    assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();
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
                .get("/webhook/api/objects");

//get the response
       System.out.println("Response: " + response.asPrettyString());
       //validate the response 
       // assert status code : 200
       assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();
       assert response.jsonPath().getString("[0].name").equals("Apple MacBook Pro 16") : "Expected name Apple MacBook Pro 16 but got " + response.jsonPath().getString("[0].email");
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
                .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/218");

//get the response
       System.out.println("Response: " + response.asPrettyString());
       //validate the response 
       // assert status code : 200
       assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();
       assert response.jsonPath().getString("[0].name").equals("Apple ") : "Expected name Apple MacBook Pro 16 but got " + response.jsonPath().getString("[0].email");
       }

    @Test
    public void testGetDepartment() {
        // Define the URL
        RestAssured.baseURI = "https://whitesmokehouse.com";
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get("/webhook/api/department");

                System.err.println("response: " + response.asPrettyString());
                assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();
                assert response.jsonPath().getString("[0].department").equals("Technology") : "Expected name Technology but got " + response.jsonPath().getString("[0].department");
                

}

@Test
public void toPostRegister01() {
        // Define the URL
        RestAssured.baseURI = "https://whitesmokehouse.com";
        String bodyRegister ="{\n" + //
                                " \"email\": \"windatest3@yopmail.com\",\n" + //
                                " \"full_name\": \"winda\",\n" + //
                                " \"password\": \"@dmin123\",\n" + //
                                " \"department\": \"Finance\",\n" + //
                                " \"phone_number\": \"082264189134\"\n" + //
                                "}";
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .log().all()
                .when()
                .body(bodyRegister)
                .post("/webhook/api/register");

                System.out.println("Response: " + response.asPrettyString());
                assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();
                assert response.jsonPath().getString("full_name").equals("winda") :"Expected Winda but the current result is " + response.jsonPath().getString("full_name");
}

public void toPostRegister02() {
        // Define the URL
        RestAssured.baseURI = "https://whitesmokehouse.com";
        String bodyRegister ="{\n" + //
                                " \"email\": \"windatest3@yopmail.com\",\n" + //
                                " \"full_name\": \"winda\",\n" + //
                                " \"password\": \"@dmin123\",\n" + //
                                " \"department\": \"Finance\",\n" + //
                                " \"phone_number\": \"082264189134\"\n" + //
                                "}";
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .log().all()
                .when()
                .body(bodyRegister)
                .post("/webhook/api/register");

                System.out.println("Response: " + response.asPrettyString());
                assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();
                assert response.jsonPath().getString("full_name").equals("winda") :"Expected Winda but the current result is " + response.jsonPath().getString("full_name");
}

@Test
public void toPostRegister03() {
        //validate empty response 
        // Define the URL
        RestAssured.baseURI = "https://whitesmokehouse.com";
        String bodyRegister ="";
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .log().all()
                .when()
                .body(bodyRegister)
                .post("/webhook/api/register");

                System.out.println("Response: " + response.asPrettyString());
                assert response.getStatusCode() == 200 : "Expected status code 200 but the current result is " + response.getStatusCode();
                assert response.jsonPath().getString("message").equals("Please check if your email has been entered correctly or is not empty.") :"Expected Winda but the current result is " + response.jsonPath().getString("message");

}

}