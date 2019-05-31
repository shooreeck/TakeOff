package apitest.positive;

import apitest.BasicTestClass;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import objects.User;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserTests extends BasicTestClass {

    private String userName;
    private String job;
    private JSONObject body;

    @BeforeMethod
    public void beforeMethod() {
        userName = generateString();
        job = generateString();
        body = new JSONObject();
        body.put("name", userName);
        body.put("job", job);
    }

    @Test
    public void createNewUserTest() {
        Response response = createNewUserGetResponse(body);
        response.then()
                .statusCode(201)
                .and().assertThat()
                .contentType(ContentType.JSON)
                .and().assertThat()
                .body(
                        "name", equalTo(userName),
                        "job", equalTo(job));

        String responseString = response.thenReturn().body().asString();
        assertTrue(responseString.contains("\"createdAt\""));
        assertTrue(responseString.contains("\"name\""));
        assertTrue(responseString.contains("\"job\""));
        assertTrue(responseString.contains("\"id\""));
    }

    @Test
    public void updateUserTest() throws IOException {
        JSONObject updatedBody = new JSONObject();
        updatedBody.put("name", generateString());
        updatedBody.put("job", generateString());

        String userId = new User()
                .getUser(createNewUserGetResponse(body)).getId();

        Response response = given()
                .contentType(ContentType.JSON)
                .body(updatedBody)
                .baseUri(URL)
                .put("/users/" + userId);

        response.then()
                .statusCode(200)
                .and().assertThat()
                .contentType(ContentType.JSON);

        String responseString = response.thenReturn().body().asString();
        assertTrue(responseString.contains("\"updatedAt\""));
        assertTrue(responseString.contains("\"name\""));
        assertTrue(responseString.contains("\"job\""));
    }

    private Response createNewUserGetResponse(JSONObject body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .baseUri(URL)
                .post("/users");
    }
}
