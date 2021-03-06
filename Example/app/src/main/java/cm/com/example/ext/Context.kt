package cm.com.example.ext

import android.content.Context
import android.content.Context.MODE_PRIVATE
import cm.com.example.vo.User
import com.google.gson.Gson

fun Context.saveUserPrefObj(user: User?) {
    val pref = getSharedPreferences("EXAM", MODE_PRIVATE)
    val editor = pref.edit()
    val gSon = Gson()
    editor.putString("user", gSon.toJson(user))
    editor.apply()
}

fun Context.getUserPrefObj(): User? {
    val pref = getSharedPreferences("EXAM", MODE_PRIVATE)
    val gSon = Gson()
    return gSon.fromJson<User>(
        pref.getString("user", null),
        User::class.java
    )
}