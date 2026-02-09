package com.myskillskit.unicode

private const val ASCII_MAX = 0x7F
private const val HEX_PAD_LENGTH = 4

class ToUnicodeConverter : AbstractUnicodeAction() {

    override fun convert(text: String): String = buildString(text.length * 2) {
        for (ch in text) {
            if (ch.code <= ASCII_MAX) {
                append(ch)
            } else {
                append("\\u")
                append(ch.code.toString(16).uppercase().padStart(HEX_PAD_LENGTH, '0'))
            }
        }
    }
}
