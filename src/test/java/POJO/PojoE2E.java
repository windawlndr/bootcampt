
package POJO;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.demo.model.requestObject;
import com.demo.model.responseObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class PojoE2E {

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

    

    @Test(priority = 1)

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
         System.out.println("Response Post Object: " + responsePostObject.asPrettyString());
         responseObject [] ressObject = responsePostObject.as(responseObject[].class);
        
        Assert.assertNotNull(ressObject[0].id,"Expected id but current result is null");
        Assert.assertEquals(ressObject[0].name, "Ipad Winda", "Expected name to be 'Ipad Winda' but got " + ressObject[0].name);
        Assert.assertEquals(ressObject[0].data.year, "2022", "Expected year to be '2022' but got " + ressObject[0].data.year);
        Assert.assertEquals(ressObject[0].data.price, 599.0, "Expected price to be '599' but got " + ressObject[0].data.price);
        Assert.assertEquals(ressObject[0].data.cpu_model, "Chipset A13", "Expected cpu_model to be 'Chipset A13' but got " + ressObject[0].data.cpu_model);
        Assert.assertEquals(ressObject[0].data.hard_disk_size, "64GB", "Expected hard_disk_size to be '64GB' but got " + ressObject[0].data.hard_disk_size);
        Assert.assertEquals(ressObject[0].data.color, "Silver", "Expected color to be 'Silver' but got " + ressObject[0].data.color);
        Assert.assertEquals(ressObject[0].data.capacity, "1", "Expected capacity to be '1' but got " + ressObject[0].data.capacity);
        Assert.assertEquals(ressObject[0].data.screen_size, "10.9 Inch", "Expected screen_size to be '10.9 Inch' but got " + ressObject[0].data.screen_size);
       

        //serialize the object to json 
        requestObject resObject = new requestObject("Ipad Winda", new requestObject.Data());
        // Convert the requestObject to JSON string
        ObjectMapper objectMapper = new ObjectMapper();

        String requestAddObject;
        try {
            requestAddObject = objectMapper.writeValueAsString(resObject);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing request object", e);
        }
        System.out.println("Serialized Request Body: " + requestAddObject);

        responsePostObject = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Tokenlogin)
                .body(requestAddObject)
                .log().all()
                .when()
                .post("/webhook/api/objects");
        
        

        System.out.println("Response Post Object: " + responsePostObject.asPrettyString());
        
        responseObject[] resGet = responsePostObject.as(responseObject[].class);
        Assert.assertEquals(responsePostObject.getStatusCode(), 200,
            "Expected status code 200 but got " + responsePostObject.getStatusCode());
        Assert.assertNotNull(resGet[0].id, "Expected id to be present in the response but it was null or missing. Response: " + responsePostObject.asPrettyString());
        System.out.println("Extracted Object ID: " + resGet[0].id);
        objectId = resGet[0].id;
        Assert.assertEquals(resGet[0].name, "Ipad Winda", "Expected name to be 'Ipad Winda' but got " + resGet[0].name);
        Assert.assertEquals(resGet[0].data.year, "2022", "Expected year to be '2022' but got " + resGet[0].data.year);
        Assert.assertEquals(resGet[0].data.price, 599.0, "Expected price to be '599' but got " + resGet[0].data.price);
        Assert.assertEquals(resGet[0].data.cpu_model, "Chipset A13", "Expected cpu_model to be 'Chipset A13' but got " + resGet[0].data.cpu_model);
        Assert.assertEquals(resGet[0].data.hard_disk_size, "64GB", "Expected hard_disk_size to be '64GB' but got " + resGet[0].data.hard_disk_size);
        Assert.assertEquals(resGet[0].data.color, "Silver", "Expected color to be 'Silver' but got " + resGet[0].data.color);
        Assert.assertEquals(resGet[0].data.capacity, "1", "Expected capacity to be '1' but got " + resGet[0].data.capacity);
        Assert.assertEquals(resGet[0].data.screen_size, "10.9 Inch", "Expected screen_size to be '10.9 Inch' but got " + resGet[0].data.screen_size);
       
        // Assert.assertEquals(responsePostObject.getStatusCode(), 200,


         //  Assert.assertEquals(responsePostObject.getStatusCode(), 200,
        //     "Expected status code 200 but got " + responsePostObject.getStatusCode());
        
        //  System.out.println("Response: " + responsePostObject.asPrettyString());
        //  objectId = responsePostObject.jsonPath().getInt("[0].id");      
    
        // Assert.assertNotNull(responsePostObject.jsonPath().get("id"),
        // "Expected id to be present in the response but it was null or missing. Response: " + responsePostObject.asPrettyString());
        // System.out.println("Extracted Object ID: " + objectId);

        //2. get list object
//         Response responseGetSingleObjectId = RestAssured.given()
//                 .header("Content-Type", "application/json")
//                 .header("Authorization", "Bearer " + Tokenlogin)
//                 .log().all()
//                 .when()
//                 .get("/webhook/api/objectslistId?id=" + objectId);


//         System.out.println("Response Get Single Object ID: " + responseGetSingleObjectId.asPrettyString());
        
//         Assert.assertEquals(responseGetSingleObjectId.getStatusCode(), 200,
//             "Expected status code 200 but got " + responseGetSingleObjectId.getStatusCode());
        
//         Assert.assertEquals(responseGetSingleObjectId.jsonPath().getString("[0].name"), "Ipad Winda");

//           //3. update object
//         // define the request body for update
//         String requestBodyUpdate = "{\n" + //
//                 " \"name\": \"Ipad Winda Updated\",\n" + //
//                 " \"data\": {\n" + //
//                 " \"year\": 2022,\n" + //
//                 " \"price\": 599,\n" + //
//                 " \"cpu_model\": \"Chipset A13\",\n" + //
//                 " \"hard_disk_size\": \"64GB\",\n" + //
//                 " \"capacity\": \"1\",\n" + //
//                 " \"screen_size\": \"10.9 Inch\",\n" + //
//                 " \"color\": \"Silver\"\n" + //
//                 " }\n" + //
//                 "}";


//         Response UpdateObject = RestAssured.given()
//                 .header("Content-Type", "application/json")
//                 .header("Authorization", "Bearer " + Tokenlogin)
//                 .body(requestBodyUpdate)
//                 .log().all()
//                 .when()
//                 .put("/webhook/37777abe-a5ef-4570-a383-c99b5f5f7906/api/objects/" + objectId);

//         System.out.println("Response Update Object: " + UpdateObject.asPrettyString());
//         Assert.assertEquals(UpdateObject.getStatusCode(), 200,
//             "Expected status code 200 but got " + UpdateObject.getStatusCode());
//         Assert.assertEquals(UpdateObject.jsonPath().getString("[0].name"), "Ipad Winda Updated",
//             "Expected name to be 'Ipad Winda Updated' but got " + UpdateObject.jsonPath().getString("name"));

//         //4. validate updated object
//         Response responseUpdatedObject = RestAssured.given()
//             .header("Content-Type", "application/json")
//             .header("Authorization", "Bearer " + Tokenlogin)
//             .log().all()
//             .when()
//             .get("/webhook/api/objectslistId?id=" + objectId);


//     System.out.println("Response Get Single Object ID: " + responseUpdatedObject.asPrettyString());
    
//     Assert.assertEquals(responseGetSingleObjectId.getStatusCode(), 200,
//         "Expected status code 200 but got " + responseUpdatedObject.getStatusCode());
    
//     Assert.assertEquals(responseUpdatedObject.jsonPath().getString("[0].name"), "Ipad Winda Updated");

//     //5. Deletes Object 

//     Response responseDeleteObject = RestAssured.given()
//             .header("Content-Type", "application/json")
//             .header("Authorization", "Bearer " + Tokenlogin)
//             .log().all()
//             .when()
//             .delete("/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/" + objectId);

//     System.out.println("Response Delete Object: " + responseDeleteObject.asPrettyString());
    
//     Assert.assertEquals(responseDeleteObject.getStatusCode(), 200,
//         "Expected status code 200 but got " + responseDeleteObject.getStatusCode());
    
//     Assert.assertEquals(responseDeleteObject.jsonPath().getString("status"), "deleted",
//         "Expected message to be 'Object deleted successfully' but got " + responseDeleteObject.jsonPath().getString("status"));

// //6. validate the deleted object with hit the deleted end point for the second time
// Response responseDeleteObjects2 = RestAssured.given()
//             .header("Content-Type", "application/json")
//             .header("Authorization", "Bearer " + Tokenlogin)
//             .log().all()
//             .when()
//             .delete("/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/" + objectId);

//     System.out.println("Response Delete Object: " + responseDeleteObjects2.asPrettyString());
    
//     Assert.assertEquals(responseDeleteObjects2.getStatusCode(), 200,
//         "Expected status code 200 but got " + responseDeleteObject.getStatusCode());
    
//     Assert.assertEquals(responseDeleteObject.jsonPath().getString("status"), "failed",
//         "Expected message to be 'Object deleted " + responseDeleteObject.jsonPath().getString("status"));
      

      
    }



}
