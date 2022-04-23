import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@DisplayName("Класс с демонстрационными тестами")
public class ParameterTest5 {

    @Before
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        Configuration.baseUrl = "https://www.wildberries.ru/";
        Configuration.browserSize = "1920x1080";

    }

    @ValueSource(strings = {"Mary"})
    @ParameterizedTest(name = "Заполнение формы {0}")
    void loginTextBox(String testData) {
        Selenide.open("https://demoqa.com/text-box");
        $("#userName").setValue(testData);
        $("#userEmail").setValue(testData + "@mail.ru");
        $("#currentAddress").setValue("Address 123456");
        $("#permanentAddress").setValue("Address 789630");
        $("#submit").click();
        $("[class='border col-md-12 col-sm-12']").shouldHave(
                text("Mary"),
                text("bulaneva@mail.ru"),
                text("Address test"),
                text ("Address 458"));
    }
    static Stream<Arguments> checkMethod() {
        return Stream.of(
                Arguments.of("Marymary" ,"Qwerty123#")
        );
    }

    @CsvSource(value = {
            "Mary | marymary@yandex.ru | Moscow, Orehovo station | Tula, Tula",
            "Irina | alekseeva@mail.com | Pskov, Velikie Luki | Smolensk, Lenina"
    },
            delimiter = '|')
    @ParameterizedTest(name = "Проверка формы регистрации {0}")
    void RegistrationFormTest(String nameInfo, String emailInfo, String currentAddress, String permanentAddress) {
        Selenide.open("https://demoqa.com/text-box");
        $("#userName").setValue(nameInfo);
        $("#userEmail").setValue(emailInfo);
        $("#currentAddress").setValue(currentAddress);
        $("#permanentAddress").setValue(permanentAddress);
        $("#submit").click();

        $$("#name.mb-1").find(text(nameInfo)).shouldBe(visible);
        $$("#email.mb-1").find(text(emailInfo)).shouldBe(visible);
        $$("#currentAddress.mb-1").find(text(currentAddress)).shouldBe(visible);
        $$("#permanentAddress.mb-1").find(text(permanentAddress)).shouldBe(visible);
    }

    static Stream<Arguments> methodSourceExampleTest() {
        return Stream.of(
                Arguments.of("Mary", "Dmitrievna"),
                Arguments.of("Sara", "Alekseevna"),
                Arguments.of("Mihail", "Borisovich"),
                Arguments.of("Andrey", "Andreevich")

        );
    }

    @MethodSource("methodSourceExampleTest")
    @ParameterizedTest
    void methodSourceExampleTest(String firstName, String secondName) {
        Selenide.open("https://demoqa.com/text-box");
        $("#userName").setValue(firstName + " " + secondName);
        $("#submit").click();

        $$("#name.mb-1").find(text(firstName + " " + secondName)).shouldBe(visible);
    }

}
