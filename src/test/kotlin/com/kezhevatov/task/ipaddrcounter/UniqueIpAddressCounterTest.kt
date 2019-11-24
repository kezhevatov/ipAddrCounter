package com.kezhevatov.task.ipaddrcounter

import org.junit.jupiter.api.Test
import java.io.File

class UniqueIpAddressCounterTest {

    @Test
    fun testWithCorrectIps() {
        val counter = UniqueIpAddressCounter()
        val file = File(UniqueIpAddressCounterTest::class.java.getResource("/set1-7-correct.txt").toURI())
        file.forEachLine {
            counter.add(it)
        }

        assert(counter.getCount() == 7L)
    }

    @Test
    fun testWithIncorrectFormatIps() {
        val counter = UniqueIpAddressCounter()
        val file = File(UniqueIpAddressCounterTest::class.java.getResource("/set2-9-correct.txt").toURI())
        file.forEachLine {
            try {
                counter.add(it)
            } catch (e: Exception) {
            }
        }

        assert(counter.getCount() == 9L)
    }
}
