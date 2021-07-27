import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.core.Is.is;


//Пять API-тестов
//#1 проверка метода GET "SINGLE USER" (получить конкретного юзера)
//#2 проверка метода GET "SINGLE USER NOT FOUND" (пользователь не найден
//#3 проверка метода POST "CREATE" (создание нового юзера)
//#4 проверка метода PATCH "UPDATE" (обновление данных юзера)
//#5 проверка метода DELETE "DELETE" (удаление юзера)

public class ReqresInApiTest {

    //#1 проверка метода GET SINGLE USER (получить конкретного юзера)
    //  /api/users/3
    //  ОР: "first_name":"Emma"
    @Test
    void checkGETusers() {
        given()
                .when()
                .get("https://reqres.in/api/users/3")
                .then()
                .body("data.id", is(3));
    }

    //#2 проверка метода GET SINGLE USER NOT FOUND (пользователь не найден
    //  /api/users/33
    //  ОР1: statusCode == 404
    //  ОР2: пустое тело "{}"
    @Test
    void checkGETemptyUsers() {
        given()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .statusCode(404)
                .body(is("{}"));
    }

    //#3 проверка метода POST CREATE (создание нового юзера)
    //  /api/users
    //  body {"name": "morpheus",
    //        "job": "leader"}
    //
    //  ОР: statusCode == 201
    @Test
    void checkPOSTcreateUser() {
        given()
                .contentType(JSON)
                .body("{\"name\": \"morpheus\"," +
                        "\"job\": \"leader\"}")
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201);
    }

    //#4 проверка метода PATCH UPDATE (обновление данных юзера)
    //  /api/users/2
    //  body {"name": "lenin",
    //        "job": "comrad"}
    //
    //  ОР1: "name": "lenin",
    //  ОР2: "job": "comrad",
    @Test
    void checkPATCHupdateUser() {
        String testName = "lenin";
        String testJob = "comrad";

        given()
                .contentType(JSON)
                .body("{\"name\": \"" + testName + "\"," +
                        "\"job\": \"" + testJob + "\"}")
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("name", is(testName))
                .body("job", is(testJob));
    }

    //#5 проверка метода DELETE DELETE (удаление юзера)
    //  /api/users/3
    //  ОР1: statusCode == 204
    @Test
    void checkDELETEdeleteUser() {
        given()
                .when()
                .delete("https://reqres.in/api/users/3")
                .then()
                .statusCode(204);
    }
}
