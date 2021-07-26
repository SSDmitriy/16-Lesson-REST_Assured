import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

public class ReqresInApiTest {

    //check GET /api/users/3
    //user#3 that "first_name":"Emma"
    @Test
    void checkGETusers3() {
        given()
                .when()
                .get("https://reqres.in/api/users/3")
                .then()
                .body("first_name", is("Emma"));
    }
}
