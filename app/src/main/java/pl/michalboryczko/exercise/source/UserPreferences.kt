package pl.michalboryczko.exercise.source

import android.content.SharedPreferences
import com.google.gson.Gson
import javax.inject.Inject
import android.content.Context
import pl.michalboryczko.exercise.source.UserPreferences.Key.IMPL

class UserPreferences @Inject constructor(
        private val sharedPreferences: SharedPreferences,
        private val gson: Gson,
        private val context: Context) {

    private object Key {
        const val IMPL = "IMPL"
    }

    fun getCurrentImpl(): String{
        val impl = sharedPreferences.getString(IMPL, "REALM")
        return impl
    }


    fun saveCurrentImpl(impl: String){
        sharedPreferences.edit().putString(IMPL, impl).apply()
    }


    fun clearPreferences() = sharedPreferences.edit().clear().apply()


}
