package objects;

import io.restassured.response.Response;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;

public class User {

    private String name;
    private String job;
    private String id;
    private String createdAt;

    public User() {

    }

    public User getUser(Response object) throws IOException {
        return new ObjectMapper().readValue(object.as(JSONObject.class).toJSONString(), User.class);
    }

    public String getName() {
        return name;
    }
    public String getJob() {
        return job;
    }
    public String getId() {
        return id;
    }
    public String getCreatedAt() {
        return createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
