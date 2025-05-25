
package ScenarioTest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class E2ERestAssured {

    String Tokenlogin;
    int objectId;


    @BeforeClass
    public void setUp() {
        // Define the base URI for the API
        RestAssured.baseURI = "https://whitesmokehouse.com";
            //login request bddy 
            String requestBody = "{\n" + //
                        " \"email\": \"w1ndtes209@yopmail.com\",\n" + //
                        " \"password\": \"@dmin123\"\n" + //
                        "}";
        
            Response response = RestAssured.given()
                    .contentType("application/json")
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .log().all()
                    .when()
                    .post("/webhook/api/login");
            Tokenlogin = response.jsonPath().getString("token");
           
        
    
        
    

    }

    @Test (priority = 1)
public void toPostRegister() {
  
        //1. Register   
        //define the request body
        String bodyRegister ="{\n" + //
                        " \"email\": \"w1ndtes209@yopmail.com\",\n" + //
                        " \"full_name\": \"winda\",\n" + //
                        " \"password\": \"@dmin123\",\n" + //
                        " \"department\": \"Finance\",\n" + //
                        " \"phone_number\": \"089655635801\"\n" + //
                        "}";
        Response registerUser = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(bodyRegister)
                .log().all()
                .when()
                .post("/webhook/api/register");

            System.out.println("Response: " + registerUser.asPrettyString());
            
            Assert.assertEquals(registerUser.getStatusCode(), 200, 
        "Expected status code 200 but you got " + registerUser.getStatusCode());
           
            Assert.assertNotNull(registerUser.jsonPath().get("full_name"),
        "Expected full_name to be present in the response but it was null or missing. Response: " + registerUser.asPrettyString());

            Assert.assertEquals(registerUser.jsonPath().getString("full_name"), "winda",
        "Expected full_name winda " + registerUser.jsonPath().getString("full_name"));

        Assert.assertEquals(registerUser.jsonPath().getString("department"), "Finance",
        "Expected department winda but current result is  " + registerUser.jsonPath().getString("department"));
    
        Assert.assertEquals(registerUser.jsonPath().getString("phone_number"), "089655635801",
        "Expected phone_number but current result is  " + registerUser.jsonPath().getString("phone_number"));
       
    //2.login with new user

    String jsonlogin = "{\n" + //
                " \"email\": \"w1ndtes209@yopmail.com\",\n" + //
                " \"password\": \"@dmin123\"\n" + //
                "}";

    Response responseLogin = RestAssured.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .body(jsonlogin)
                .log().all()
                .when()
                .post("/webhook/api/login");;


    Tokenlogin = responseLogin.jsonPath().getString("token");
    
    //hit endpoint list object

    Response responseGetListObjectResponse = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Tokenlogin)
                .log().all()
                .when()
                .get("/webhook/api/objects");

    System.out.println("List Objects Response: " + responseGetListObjectResponse.asPrettyString());
    Assert.assertEquals(responseGetListObjectResponse.getStatusCode(), 200, 
        "Expected status code 200 but got " + responseGetListObjectResponse.getStatusCode());
                
    
    }


    @Test(priority = 2)

    /*
     * 1. add object
     * 2. get list object 
     * 3. update object 
     * 4. validate updated object 
     * 5. delete object 
     * 6. validate the deleted object wont be shows in list object
     */
    public void addObjectFlow() {
    //1. add object
    String requestBodyObject ="{\n" + //
                "\n" + //
                " \"name\": \"Ipad Winda\",\n" + //
                " \"data\": {\n" + //
                " \"year\": 2022,\n" + //
                " \"price\": 599,\n" + //
                " \"cpu_model\": \"Chipset A13\",\n" + //
                " \"hard_disk_size\": \"64GB\",\n" + //
                " \"capacity\": \"1\",\n" + //
                " \"screen_size\": \"10.9 Inch\",\n" + //
                " \"color\": \"Silver\"\n" + //
                " }\n" + //
                "}";

    Response responsePostObject = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Tokenlogin)
                .body(requestBodyObject)
                .log().all()
                .when()
                .post("/webhook/api/objects");
         // validate the response
         Assert.assertEquals(responsePostObject.getStatusCode(), 200,
         "Expected status code 200 but got " + responsePostObject.getStatusCode());
 
         System.out.println("Response: " + responsePostObject.asPrettyString());
         objectId = responsePostObject.jsonPath().getInt("[0].id");      
    
        Assert.assertNotNull(responsePostObject.jsonPath().get("id"),
        "Expected id to be present in the response but it was null or missing. Response: " + responsePostObject.asPrettyString());
        System.out.println("Extracted Object ID: " + objectId);

        //2. get list object
        Response responseGetSingleObjectId = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Tokenlogin)
                .log().all()
                .when()
                .get("/webhook/api/objectslistId?id=" + objectId);


        System.out.println("Response Get Single Object ID: " + responseGetSingleObjectId.asPrettyString());
        
        Assert.assertEquals(responseGetSingleObjectId.getStatusCode(), 200,
            "Expected status code 200 but got " + responseGetSingleObjectId.getStatusCode());
        
        Assert.assertEquals(responseGetSingleObjectId.jsonPath().getString("[0].name"), "Ipad Winda");

          //3. update object
        // define the request body for update
        String requestBodyUpdate = "{\n" + //
                " \"name\": \"Ipad Winda Updated\",\n" + //
                " \"data\": {\n" + //
                " \"year\": 2022,\n" + //
                " \"price\": 599,\n" + //
                " \"cpu_model\": \"Chipset A13\",\n" + //
                " \"hard_disk_size\": \"64GB\",\n" + //
                " \"capacity\": \"1\",\n" + //
                " \"screen_size\": \"10.9 Inch\",\n" + //
                " \"color\": \"Silver\"\n" + //
                " }\n" + //
                "}";


        Response UpdateObject = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Tokenlogin)
                .body(requestBodyUpdate)
                .log().all()
                .when()
                .put("/webhook/37777abe-a5ef-4570-a383-c99b5f5f7906/api/objects/" + objectId);

        System.out.println("Response Update Object: " + UpdateObject.asPrettyString());
        Assert.assertEquals(UpdateObject.getStatusCode(), 200,
            "Expected status code 200 but got " + UpdateObject.getStatusCode());
        Assert.assertEquals(UpdateObject.jsonPath().getString("[0].name"), "Ipad Winda Updated",
            "Expected name to be 'Ipad Winda Updated' but got " + UpdateObject.jsonPath().getString("name"));

        //4. validate updated object
        Response responseUpdatedObject = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + Tokenlogin)
            .log().all()
            .when()
            .get("/webhook/api/objectslistId?id=" + objectId);


    System.out.println("Response Get Single Object ID: " + responseUpdatedObject.asPrettyString());
    
    Assert.assertEquals(responseGetSingleObjectId.getStatusCode(), 200,
        "Expected status code 200 but got " + responseUpdatedObject.getStatusCode());
    
    Assert.assertEquals(responseUpdatedObject.jsonPath().getString("[0].name"), "Ipad Winda Updated");

    //5. Deletes Object 

    Response responseDeleteObject = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + Tokenlogin)
            .log().all()
            .when()
            .delete("/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/" + objectId);

    System.out.println("Response Delete Object: " + responseDeleteObject.asPrettyString());
    
    Assert.assertEquals(responseDeleteObject.getStatusCode(), 200,
        "Expected status code 200 but got " + responseDeleteObject.getStatusCode());
    
    Assert.assertEquals(responseDeleteObject.jsonPath().getString("status"), "deleted",
        "Expected message to be 'Object deleted successfully' but got " + responseDeleteObject.jsonPath().getString("status"));

//6. validate the deleted object with hit the deleted end point for the second time
Response responseDeleteObjects2 = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + Tokenlogin)
            .log().all()
            .when()
            .delete("/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/" + objectId);

    System.out.println("Response Delete Object: " + responseDeleteObjects2.asPrettyString());
    
    Assert.assertEquals(responseDeleteObjects2.getStatusCode(), 200,
        "Expected status code 200 but got " + responseDeleteObject.getStatusCode());
    
    Assert.assertEquals(responseDeleteObject.jsonPath().getString("status"), "failed",
        "Expected message to be 'Object deleted " + responseDeleteObject.jsonPath().getString("status"));
      

      
    }



}
