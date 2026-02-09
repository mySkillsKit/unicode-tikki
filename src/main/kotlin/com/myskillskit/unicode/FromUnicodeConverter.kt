package com.myskillskit.unicode

private const val UNICODE_ESCAPE_LENGTH = 6 // \uXXXX
private const val HEX_RADIX = 16

class FromUnicodeConverter : AbstractUnicodeAction() {

    override fun convert(text: String): String = buildString(text.length) {
        var i = 0
        while (i < text.length) {
            if (text[i] == '\\' && i + UNICODE_ESCAPE_LENGTH <= text.length && text[i + 1] == 'u') {
                val hex = text.substring(i + 2, i + UNICODE_ESCAPE_LENGTH)
                try {
                    append(hex.toInt(HEX_RADIX).toChar())
                    i += UNICODE_ESCAPE_LENGTH
                    continue
                } catch (_: NumberFormatException) {
                    // Invalid hex digits — write as-is
                }
            }
            append(text[i])
            i++
        }
    }
}
