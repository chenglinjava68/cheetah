package org.cheetah.common.utils;

import org.cheetah.commons.utils.Assert;
import org.junit.Test;

/**
 * Created by Max on 2016/9/11.
 */
public class AssertTest {

    @Test
    public void testNotGt() {
        int a = 1;
        int b = 2;
        Assert.notGt(a, b, "test");
    }

    @Test
    public void testNotGte() {
        float a = 1;
        float b = 2;
        Assert.notGte(a, b, "test");
    }

    @Test
    public void testNotLt() {
        double a = 1;
        double b = 2;
        Assert.notLt(a, b, "test");
    }

    @Test
    public void testNotLte() {
        byte a = 21;
        byte b = 2;
        Assert.notLte(a, b, "test");
    }

    @Test
    public void testNotEquals() {
        long a = 2;
        long b = 2;
        Assert.notEquals(a, b, "test");
    }
}
