package org.cubewhy.lunarcn.utils

import org.cubewhy.lunarcn.Client
import java.util.*

class GitUtils {
    companion object {
        @JvmField
        val gitInfo = Properties().also {
            val inputStream = Client::class.java.classLoader.getResourceAsStream("git.properties")
            if (inputStream != null) {
                it.load(inputStream)
            } else {
                it["git.branch"] = "master"
            }
        }

        @JvmField
        val gitBranch = (gitInfo["git.branch"] ?: "unknown")
    }
}