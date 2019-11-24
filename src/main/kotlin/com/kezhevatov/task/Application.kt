package com.kezhevatov.task

import com.kezhevatov.task.ipaddrcounter.UniqueIpAddressCounter
import java.io.File

fun main(args: Array<String>) {
    if (args.size != 1) {
        throw IllegalArgumentException("Usage: java ip-addr-counter-1.0.jar <file name>")
    }

    val counter = UniqueIpAddressCounter()

    val file = File(args[0])
    file.forEachLine {
        try {
            counter.add(it)
        } catch (e: IllegalArgumentException) {
            println("Incorrect ip format: $it")
        }
    }

    println("Unique ip addresses: ${counter.getCount()}")
}





