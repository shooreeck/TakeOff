package apitest.negative;

import apitest.BasicTestClass;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * There is no sense in this class, cause endpoints does not have any validations!
 * Wrote tests as if validation is present
 */

public class UserTestsNegative extends BasicTestClass {

    private String userName;
    private String job;

    @BeforeMethod
    public void beforeMethod() {
        userName = generateString();
        job = generateString();
    }

    @Test
    public void createNewUserEmptyNameTest() {
        JSONObject body = new JSONObject();
        body.put("name", "");
        body.put("job", job);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .baseUri(URL)
                .post("/users");

        assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void createNewUserWrongValueType() {
        JSONObject body = new JSONObject();
        body.put("name", 88888888);
        body.put("job", job);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .baseUri(URL)
                .post("/users");

        assertEquals(response.getStatusCode(), 400);
    }
}
