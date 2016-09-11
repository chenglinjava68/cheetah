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
        int a = 1;
        int b = 2;
        Assert.notGte(a, b, "test");
    }

    @Test
    public void testNotLt() {
        int a = 1;
        int b = 2;
        Assert.notLt(a, b, "test");
    }

    @Test
    public void testNotLte() {
        int a = 1;
        int b = 2;
        Assert.notLte(a, b, "test");
    }

    @Test
    public void testNotEquals() {
        int a = 2;
        int b = 2;
        Assert.notEquals(a, b, "test");
    }
}
