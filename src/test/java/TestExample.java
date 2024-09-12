import io.qameta.allure.Step;
import io.qameta.allure.AllureId;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

public class TestExample extends InitTest {

    @Test
    @AllureId("T2") // Test case ID for TestRail
    @Description("test1")
    public void testExample() {
        step1();
        step2();
    }

    @Step("Step 1")
    public void step1() {
        subStep1();
        subStep2();
    }

    @Step("Sub-step 1")
    public void subStep1() {
    }

    @Step("Sub-step 2")
    public void subStep2() {
    }

    @Step("Step 2")
    public void step2() {
    }
}
