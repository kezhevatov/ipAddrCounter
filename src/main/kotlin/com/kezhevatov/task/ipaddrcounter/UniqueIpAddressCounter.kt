package com.kezhevatov.task.ipaddrcounter

class UniqueIpAddressCounter {

    private val subArrayLength = (256 * 256 * 256) / 32
    private val mainArray = arrayOfNulls<IntArray>(256)
    private var count: Long = 0

    fun add(ipAsString: String) {
        val ipParts = ipAsString.split(".")

        if (ipParts.size != 4)
            throw IllegalArgumentException("Incorrect ip format: $ipParts")

        val ip1 = ipParts[0].toInt()
        val ip2 = ipParts[1].toInt()
        val ip3 = ipParts[2].toInt()
        val ip4 = ipParts[3].toInt()

        if (incorrectIpFormat(ip1, ip2, ip3, ip4))
            throw IllegalArgumentException("Incorrect ip format: $ipParts")

        if (mainArray[ip1] == null) {
            mainArray[ip1] = IntArray(subArrayLength)
        }

        val vectorPositionInSubArray = (ip2 shl 16) + (ip3 shl 8) + ip4          // ip2 * 256 * 256 + ip3 * 256 + ip4
        val bitVectorPosition = vectorPositionInSubArray ushr 5                  // vectorPositionInSubArray / 32
        val bitPosition = vectorPositionInSubArray - (bitVectorPosition shl 5)   // vectorPositionInSubArray % 32

        val vectorSubArray = mainArray[ip1]!!
        val bitVector = vectorSubArray[bitVectorPosition]

        if (isBitEmpty(bitVector, bitPosition)) {
            vectorSubArray[bitVectorPosition] = setBitInVector(bitVector, bitPosition)
            count++
        }
    }

    fun getCount() = count

    private fun incorrectIpFormat(ip1: Int, ip2: Int, ip3: Int, ip4: Int) =
        ip1 < 0 || ip2 < 0 || ip3 < 0 || ip4 < 0 || ip1 > 255 || ip2 > 255 || ip3 > 255 || ip4 > 255

    private fun setBitInVector(bitVector: Int, bitPosition: Int) =
        (bitVector or (1 shl bitPosition))

    private fun isBitEmpty(bitVector: Int, bitPosition: Int) =
        (bitVector and (1 shl bitPosition)) == 0
}
