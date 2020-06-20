package pl.michalboryczko.exercise.di.modules

import androidx.room.Room
import com.couchbase.lite.Database
import com.couchbase.lite.DatabaseConfiguration
import dagger.Module
import dagger.Provides
import pl.michalboryczko.exercise.app.MainApplication
import pl.michalboryczko.exercise.model.database.greendao.DaoMaster
import pl.michalboryczko.exercise.model.database.greendao.TranslateGreenDao
import pl.michalboryczko.exercise.model.database.ormlite.OrmLiteDBHelper
import pl.michalboryczko.exercise.source.api.InternetConnectivityChecker
import pl.michalboryczko.exercise.source.repository.*
import pl.michalboryczko.exercise.model.database.room.RoomDatabase
import pl.michalboryczko.exercise.source.UserPreferences
import pl.michalboryczko.exercise.source.api.rest.Api
import pl.michalboryczko.exercise.source.databases.impl.*
import javax.inject.Singleton


@Module
class InteractorModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(app: MainApplication): RoomDatabase = Room.databaseBuilder(app,
            RoomDatabase::class.java, "room_database").build()

    @Provides
    @Singleton
    fun provideCouchbaseDatabase(app: MainApplication): Database{
        val config = DatabaseConfiguration()
        return Database("couchbase_database", config)
    }

    @Provides
    @Singleton
    fun provideOrmDBHelperDatabase(app: MainApplication): OrmLiteDBHelper{
        return OrmLiteDBHelper(app)
    }

    @Provides
    @Singleton
    fun provideObjectBoxDatabaseImpl(): ObjectBoxDatabaseImpl = ObjectBoxDatabaseImpl()

    @Provides
    @Singleton
    fun provideOrmDatabaseImpl(helper: OrmLiteDBHelper): OrmLiteDatabaseImpl = OrmLiteDatabaseImpl(helper)

    @Provides
    @Singleton
    fun provideGreenDao(app: MainApplication): TranslateGreenDao {
        val helper = DaoMaster.DevOpenHelper(app, "translate-db")
        val db = helper.writableDatabase
        val session = DaoMaster(db).newSession()
        return session.translateGreenDao
    }


    @Provides
    @Singleton
    fun provideGreenDaoDatabaseImpl(dao: TranslateGreenDao): GreenDaoDatabaseImpl {
        return GreenDaoDatabaseImpl(dao)
    }


    @Provides
    @Singleton
    fun provideRoomDatabaseImpl(database: RoomDatabase): RoomDatabaseImpl = RoomDatabaseImpl(database)


    @Provides
    @Singleton
    fun provideRealmDatabaseImpl(): RealmDatabaseImpl = RealmDatabaseImpl()

    @Provides
    @Singleton
    fun provideCouchbaseDatabaseImpl(couchbaseDatabase: Database): CouchbaseDatabaseImpl = CouchbaseDatabaseImpl(couchbaseDatabase)


    @Provides
    fun provideRepository(userRepository: UserRepository,
                          api: Api,
                          checker: InternetConnectivityChecker): Repository {
        return RepositoryImpl(userRepository, api, checker)
    }


    @Singleton
    @Provides
    fun provideUserRepository(
            mainApplication: MainApplication,
            api: Api,
            room: RoomDatabaseImpl,
            realm: RealmDatabaseImpl,
            couchbase: CouchbaseDatabaseImpl,
            ormLiteDatabaseImpl: OrmLiteDatabaseImpl,
            objectBoxDatabaseImpl: ObjectBoxDatabaseImpl,
            greenDaoDatabaseImpl: GreenDaoDatabaseImpl,
            checker: InternetConnectivityChecker,
            preferences: UserPreferences
    ): UserRepository {
        return UserRepositoryImpl(api, room, realm, couchbase, ormLiteDatabaseImpl, objectBoxDatabaseImpl, greenDaoDatabaseImpl, checker, preferences)
    }

}