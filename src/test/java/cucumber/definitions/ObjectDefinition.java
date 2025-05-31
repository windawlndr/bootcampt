package cucumber.definitions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;

import org.testng.Assert;
import com.demo.model.ResponseObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ObjectDefinition {
    public static String Tokenlogin;
    public static String baseUrl;
    public static String token;
    public static Response response;
    public static int objectId;

    @Given("the base url is {string}")
    public void set_base_Url (String baseUrl) {
        ObjectDefinition.baseUrl = baseUrl;
    }
     
    @When("Send a http {string} request to {string} with body:")
    public void login_auth (String method, String url, String body) {
        response = RestAssured
        .given()
        .contentType("application/json")
        .body(body)
        .header("Authorization", ObjectDefinition.token != null ? "Bearer " + ObjectDefinition.token : "")
        .when()
        .request(method, ObjectDefinition.baseUrl + url);

    }

    @Then("The response status code should be {int}") 
    public void The_response_status_must_be(int status) {
        response.then().statusCode(status);
    }
    //save token to local storage
    @And ("Save the token from the response to local storage")
    public void saveToken() {
        ObjectDefinition.token = response.jsonPath().getString("token");
    }

    @And("Save the object id from the response to local storage")
    public void saveObjectId() {
        int objectId = response.jsonPath().getInt("[0].id");
        System.out.println("Object ID: " + objectId);
    }
     
    

    
    @Given("Make sure token in local storage not empty")
    public void checkToken() {
        assert ObjectDefinition.token != null : "Token null";

    }

    

    //update object
    @When("I send a http {string} request to {string} with body:")
    public void updateObject(String method, String url, String body) {
        response = RestAssured
        .given()
        .contentType("application/json")
        .body(body)
        .header("Authorization", ObjectDefinition.token != null ? "Bearer " + ObjectDefinition.token : "")
        .when()
        .request(method, ObjectDefinition.baseUrl + url + ObjectDefinition.objectId);
        System.out.println("Response Winda: " + response.body().asString());
        }

    @And("Name in the response must be {string}")
    public void checkName(String name) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResponseObject> responseObject = objectMapper.readValue(response.body().asString(), new TypeReference<List<ResponseObject>>() {});
        assert responseObject.get(0).getName().equals(name) : "Expected name: " + name + ", but got: " + responseObject.get(0).getName();
        
    }

    @And("CPU Model in the response must be {string}")
    public void checkCPUModel(String cpuModel) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResponseObject> responseObject = objectMapper.readValue(response.body().asString(), new TypeReference<List<ResponseObject>>() {});
        Assert.assertEquals(responseObject.get(0).getData().getCpuModel(), cpuModel, "expected CPU Model: " + cpuModel + ", but got: " + responseObject.get(0).getData().getCpuModel());
    }

    @And("Screen size in the response must be {string}")
    public void checkScreenSize(String screenSize) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResponseObject> responseObject = objectMapper.readValue(response.body().asString(), new TypeReference<List<ResponseObject>>() {});
        Assert.assertEquals(responseObject.get(0).getData().getScreenSize(), screenSize, "expected CPU Model: " + screenSize + ", but got: " + responseObject.get(0).getData().getScreenSize());
    
    }

    @Given("Make sure object id in local storage not empty")
    public void generateId() {
        assert ObjectDefinition.objectId != -1 : "objectId null";

        }
    @When("User Send a http {string} request to {string} with body:")
    public void deletebject(String method, String url, String body) {
        response = RestAssured
        .given()
        .contentType("application/json")
        .body(body)
        .header("Authorization", ObjectDefinition.token != null ? "Bearer " + ObjectDefinition.token : "")
        .when()
        .request(method, ObjectDefinition.baseUrl + url + ObjectDefinition.objectId);
        System.out.println("Response Delete: " + response.body().asString());
        }

}

    

           

    

    



    
