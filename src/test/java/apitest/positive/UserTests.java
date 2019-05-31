package apitest.positive;

import apitest.BasicTestClass;
import io.restassured.response.Response;
import objects.User;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;

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
    public void createNewUserTest() throws IOException {
        Response response = createNewUserGetResponse(body);

        assertCodeAndContentType(response, 201);
        User user = new User().getUser(response);

        assertEquals(user.getName(), userName);
        assertEquals(user.getJob(), job);
    }

    @Test
    public void updateUserTest() throws IOException {
        JSONObject updatedBody = new JSONObject();
        updatedBody.put("name", generateString());
        updatedBody.put("job", generateString());

        String userId = new User()
                .getUser(createNewUserGetResponse(body)).getId();

        Response response = given()
                .header("Content-type", "application/json")
                .body(updatedBody)
                .baseUri(URL)
                .put("/users/" + userId);

        assertCodeAndContentType(response, 200);

        String responseString = response.as(JSONObject.class).toJSONString();
        assertTrue(responseString.contains("\"updatedAt\""));
        assertTrue(responseString.contains("\"name\""));
        assertTrue(responseString.contains("\"job\""));
    }

    private Response createNewUserGetResponse(JSONObject body) {
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .baseUri(URL)
                .post("/users");
    }

    private void assertCodeAndContentType(Response response, int code) {
        assertEquals(response.getStatusCode(), code);
        assertTrue(response.getContentType().contains("json"));
    }
}
