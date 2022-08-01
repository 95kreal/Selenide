import com.codeborne.selenide.Selenide;
import org.openqa.selenium.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class DeliveryOrderTest {
    @BeforeEach
    void open() {
        Selenide.open("http://localhost:7777/");
    }

    public String getDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void completedBlank() {
        $("[data-test-id=city] input").setValue("Пермь");
        $("[data-test-id=date] .input__control").click();
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] .input__control").sendKeys(BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(getDate(10));
        $("[data-test-id=name] .input__control").setValue("Мадс Миккельсен");
        $("[data-test-id=phone] .input__control").setValue("+79810591703");
        $("[data-test-id=agreement]").click();
        $x("//button[contains(@class, 'button_view_extra')]").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification] [class='notification__content']").shouldHave(exactText("Встреча успешно забронирована на " + getDate(10)));
    }
}