package pl.michalboryczko.exercise.di.modules

import androidx.room.Room
import dagger.Module
import dagger.Provides
import pl.michalboryczko.exercise.app.MainApplication
import pl.michalboryczko.exercise.source.api.InternetConnectivityChecker
import pl.michalboryczko.exercise.source.repository.*
import pl.michalboryczko.exercise.model.database.room.RoomDatabase
import pl.michalboryczko.exercise.model.database.room.TranslateDAO
import pl.michalboryczko.exercise.source.api.rest.Api
import pl.michalboryczko.exercise.source.databases.RealmDatabaseImpl
import pl.michalboryczko.exercise.source.databases.RoomDatabaseImpl
import javax.inject.Singleton


@Module
class InteractorModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(app: MainApplication): RoomDatabase = Room.databaseBuilder(app,
            RoomDatabase::class.java, "room_database").build()

    @Provides
    @Singleton
    fun provideRoomDatabaseImpl(database: RoomDatabase): RoomDatabaseImpl = RoomDatabaseImpl(database)


    @Provides
    @Singleton
    fun provideRealmDatabaseImpl(): RealmDatabaseImpl = RealmDatabaseImpl()


    @Provides
    fun provideRepository(userRepository: UserRepository,
                          api: Api,
                          checker: InternetConnectivityChecker): Repository {
        return RepositoryImpl(userRepository, api, checker)
    }


    @Provides
    fun provideUserRepository(room: RoomDatabaseImpl, realm: RealmDatabaseImpl, checker: InternetConnectivityChecker): UserRepository {
        return UserRepositoryImpl(room, realm, checker)
    }

}