package com.alphawallet.app.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DappBrowserUtilsTest
{
    @Test
    public void testDefaultDapp()
    {
        assertThat(DappBrowserUtils.defaultDapp(1L), equalTo("https://megapayer.io"));
        assertThat(DappBrowserUtils.defaultDapp(137L), equalTo("https://megapayer.io"));
        assertThat(DappBrowserUtils.defaultDapp(80001L), equalTo("https://megapayer.io"));
    }

    @Test
    public void testIsDefaultDapp()
    {
        assertTrue(DappBrowserUtils.isDefaultDapp("https://megapayer.io"));
        assertTrue(DappBrowserUtils.isDefaultDapp("https://megapayer.io"));
        assertFalse(DappBrowserUtils.isDefaultDapp("https://app.1inch.finance/"));
    }

    @Test
    public void testIsWithinHomePage()
    {
        assertTrue(DappBrowserUtils.isWithinHomePage("https://megapayer.io"));
        assertTrue(DappBrowserUtils.isWithinHomePage("https://megapayer.io"));
        assertTrue(DappBrowserUtils.isWithinHomePage("https://megapayer.ioxxx"));
        assertFalse(DappBrowserUtils.isWithinHomePage("https://alphawallet1.com/browser"));
    }
}