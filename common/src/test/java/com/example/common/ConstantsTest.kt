package com.example.common

import org.junit.Assert.*
import org.junit.Test


class ConstantsTest {

    @Test
    fun testBaseUrl() {
        assertEquals(BuildConfig.BASE_URL, Constants.BASE_URL)
    }
}