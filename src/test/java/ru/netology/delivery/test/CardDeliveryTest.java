package ru.netology.delivery.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }


    @Test
    void validFiled() {
        $("[placeholder='Город']").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL, "a");
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        val daysToAddForFirstMeeting = 4;
        $("[data-test-id='date'] input").sendKeys(DataGenerator.generateDate(daysToAddForFirstMeeting));
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName("ru"));
        $("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(60000));
        $(".notification__content").shouldBe(visible).shouldHave(exactText("Встреча успешно запланирована на " + DataGenerator.generateDate(daysToAddForFirstMeeting)));
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL, "a");
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        val daysToAddForSecondMeeting = 7;
        $("[data-test-id='date'] input").sendKeys(DataGenerator.generateDate(daysToAddForSecondMeeting));
        $$("button").find(exactText("Запланировать")).click();
        $(withText("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(visible, Duration.ofSeconds(60000));
        $$(".button__text").find(text("Перепланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(60000));
        $(".notification__content").shouldBe(visible).shouldHave(exactText("Встреча успешно запланирована на " + DataGenerator.generateDate(daysToAddForSecondMeeting)));
    }

    @Test
    void invalidFiledCity() {
        $("[placeholder='Город']").setValue(DataGenerator.generateInvalidCity("ru"));
        $(byText("Запланировать")).click();
        $("[data-test-id='city']").shouldBe(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void emptyFiledCity() {
        $(byText("Запланировать")).click();
        $("[data-test-id='city']").shouldBe(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void invalidFileDateV1() {
        $("[placeholder='Город']").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL, "a");
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        val daysToAddForFirstMeeting = 2;
        $("[data-test-id='date'] input").setValue(DataGenerator.generateDate(daysToAddForFirstMeeting));
        $(byText("Запланировать")).click();
        $(".calendar-input__custom-control ").shouldBe(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void invalidFileDateV2() {
        $("[placeholder='Город']").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL, "a");
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $(byText("Запланировать")).click();
        $(".calendar-input__custom-control ").shouldBe(exactText("Неверно введена дата"));
    }

    @Test
    void invalidFiledName() {
        $("[placeholder='Город']").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL, "a");
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        val daysToAddForFirstMeeting = 4;
        $("[data-test-id='date'] input").sendKeys(DataGenerator.generateDate(daysToAddForFirstMeeting));
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName("en"));
        $(byText("Запланировать")).click();
        $("[data-test-id='name']").shouldBe(text("Имя и Фамилия указаные неверно."));

    }

    @Test
    void emptyFiledName() {
        $("[placeholder='Город']").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL, "a");
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        val daysToAddForFirstMeeting = 4;
        $("[data-test-id='date'] input").sendKeys(DataGenerator.generateDate(daysToAddForFirstMeeting));
        $(byText("Запланировать")).click();
        $("[data-test-id='name'] .input__sub").shouldBe(exactText("Поле обязательно для заполнения"));

    }

    @Test
    void emptyFiledNumber() {
        $("[placeholder='Город']").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL, "a");
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        val daysToAddForFirstMeeting = 4;
        $("[data-test-id='date'] input").sendKeys(DataGenerator.generateDate(daysToAddForFirstMeeting));
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName("ru"));
        $(byText("Запланировать")).click();
        $("[data-test-id='phone']").shouldBe(text("Поле обязательно для заполнения"));
    }

    @Test
    void invalidCheckbox() {
        $("[placeholder='Город']").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL, "a");
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        val daysToAddForFirstMeeting = 4;
        $("[data-test-id='date'] input").sendKeys(DataGenerator.generateDate(daysToAddForFirstMeeting));
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName("ru"));
        $("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $(byText("Запланировать")).click();
        $(".checkbox_checked").shouldBe(hidden);
    }

    @Test
    void invalidSecondFiledDate() {
        $("[placeholder='Город']").setValue(DataGenerator.generateCity("ru"));
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL, "a");
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        val daysToAddForFirstMeeting = 4;
        $("[data-test-id='date'] input").sendKeys(DataGenerator.generateDate(daysToAddForFirstMeeting));
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName("ru"));
        $("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(60000));
        $(".notification__content").shouldBe(visible).shouldHave(exactText("Встреча успешно запланирована на " + DataGenerator.generateDate(daysToAddForFirstMeeting)));
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL, "a");
        $("[data-test-id='date'] input").sendKeys(Keys.DELETE);
        $("[data-test-id='date'] input").setValue(DataGenerator.generateDate(2));
        $$("button").find(exactText("Запланировать")).click();
        $(".calendar-input__custom-control ").shouldBe(exactText("Заказ на выбранную дату невозможен"));
    }

}