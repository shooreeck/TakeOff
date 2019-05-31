package apitest;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;

public class BasicTestClass {

    public static final String URL = "https://reqres.in/api";

    public static String generateString() {
        return generateString(5);
    }

    public static String generateString(int n) {
        return RandomStringUtils.randomAlphanumeric(n);
    }

    protected void assertEquals(Object actual, Object expected) {
        Assert.assertEquals(actual, expected);
    }

    protected void assertTrue(boolean bool) {
        Assert.assertTrue(bool);
    }
}
