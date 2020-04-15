package com.mmabas77;

import com.mmabas77.web.i18n.I18NService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class SpringBootCourseApplicationTests {

    @Autowired
    private I18NService i18NService;

    @Test
    public void testMessageByLocalService() throws Exception {
        String key = "index.main.callout";
        String expectedResult = "Bootstrap starter template";
        String actual = i18NService.getMessage(key);
        Assert.isTrue(actual.equals(expectedResult),
                "Error With i18n -- expected != result");
    }

}
