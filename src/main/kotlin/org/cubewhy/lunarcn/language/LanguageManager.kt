package org.cubewhy.lunarcn.language

import net.minecraft.util.ResourceLocation
import org.cubewhy.lunarcn.utils.FileUtils
import java.io.InputStream
import java.util.regex.Pattern

object LanguageManager {
    val key = "%"
    var currentLanguage = "en_us"

    private val pattern = Pattern.compile("$key[A-Za-z0-9\u002E]*$key")

    val translationsDir = ResourceLocation("lunarcn/translations")

    fun getTranslationFile(): InputStream {
        return FileUtils.getFile("${translationsDir}/{$currentLanguage}.properties")
    }

//    TODO 完成语言管理器
}