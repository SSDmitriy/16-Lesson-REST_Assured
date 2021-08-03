import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static io.qameta.allure.Allure.step;

public class AddToCartTest {

    private static final String BASE_URL = "http://demowebshop.tricentis.com/";

    @Test
    void setCookieTest() {

        step("Получить токен авторизации", () -> {

            String authorizationToken =
                    given()
                            .baseUri(BASE_URL)
                            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                            //положить в корзину 2 товара
                            .body("product_attribute_16_5_4=14&product_attribute_16_6_5=15" +
                                    "&product_attribute_16_3_6=18&product_attribute_16_4_7=44" +
                                    "&product_attribute_16_8_8=22&addtocart_16.EnteredQuantity=2").
                    when()
                            .post("addproducttocart/details/16/1").
                    then()
                            .statusCode(200)
                            .extract()
                            .cookie("Nop.customer");

            step("Открыть любую картинку с сайта для инициализации сессии", () ->
                open("http://demowebshop.tricentis.com/Themes/DefaultClean/Content/images/logo.png")
                );

            step("Подставить куки в браузер", () ->
                    getWebDriver().manage().addCookie(new Cookie("Nop.customer", authorizationToken))
                );
        });

        step("Открыть сайт", () ->
                open(BASE_URL)
        );

        step("Проверить через UI количество товаров в корзине", () -> {
            //проверить, что в корзине 2 товара
            $(".cart-qty").shouldHave(text("2"));
        });
    }
}
