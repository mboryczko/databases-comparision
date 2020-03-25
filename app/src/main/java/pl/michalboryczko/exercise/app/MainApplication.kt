package pl.michalboryczko.exercise.app

import androidx.multidex.MultiDex
import com.couchbase.lite.CouchbaseLite
import com.facebook.stetho.Stetho
import com.google.firebase.FirebaseApp
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.realm.Realm
import pl.michalboryczko.exercise.di.DaggerAppComponent
import timber.log.Timber


class MainApplication: DaggerApplication() {


    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        FirebaseApp.initializeApp(this)
        Timber.plant(CustomLoggingTree())
        Stetho.initializeWithDefaults(this)
        Realm.init(this)
        CouchbaseLite.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }


}