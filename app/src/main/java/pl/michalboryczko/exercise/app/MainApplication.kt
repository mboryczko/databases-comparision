package pl.michalboryczko.exercise.app

import androidx.multidex.MultiDex
import com.couchbase.lite.CouchbaseLite
import com.facebook.stetho.Stetho
import com.google.firebase.FirebaseApp
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.realm.Realm
import pl.michalboryczko.exercise.di.DaggerAppComponent
import pl.michalboryczko.exercise.model.database.objectbox.ObjectBox
import timber.log.Timber
import io.realm.RealmConfiguration




class MainApplication: DaggerApplication() {


    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        FirebaseApp.initializeApp(this)
        Timber.plant(CustomLoggingTree())
        Stetho.initializeWithDefaults(this)
        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .name("myrealm.realm")
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(config)

        CouchbaseLite.init(this)
        ObjectBox.init(this);
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }


}