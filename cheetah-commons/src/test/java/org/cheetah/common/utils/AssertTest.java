package org.cheetah.common.utils;

import org.cheetah.commons.utils.Assert;
import org.junit.Test;

/**
 * Created by Max on 2016/9/11.
 */
public class AssertTest {

    @Test
    public void testNotGt() {
        int a = 21;
        int b = 2;
        Assert.notGreaterThan(a, b, "test");
    }

    @Test
    public void testNotGte() {
        float a = 12;
        float b = 2;
        Assert.notGreaterThanEquals(a, b, "test");
    }

    @Test
    public void testNotLt() {
        double a = 2;
        double b = 2;
        Assert.notLessThan(a, b, "test");
    }

    @Test
    public void testNotLte() {
        byte a = 1;
        byte b = 2;
        Assert.notLessThanEquals(a, b, "test");
    }

    @Test
    public void testNotEquals() {
        long a = 12;
        long b = 2;
        Assert.notEquals(a, b, "test");
    }
}
