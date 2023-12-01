package com.fikrielg.hadispocket.data.source.local.kotpref

import com.chibatching.kotpref.KotprefModel

object SharedPref:KotprefModel() {
    var fontArabSize by floatPref(default = 16f)
    var fontIdSize by floatPref(default = 16f)
}