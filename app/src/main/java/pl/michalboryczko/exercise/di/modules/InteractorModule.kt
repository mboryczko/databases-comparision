package pl.michalboryczko.exercise.di.modules

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import pl.michalboryczko.exercise.source.CryptocurrencyMapper
import pl.michalboryczko.exercise.source.PriceStatusChecker
import pl.michalboryczko.exercise.source.api.InternetConnectivityChecker
import pl.michalboryczko.exercise.source.api.firebase.FirebaseApiService
import pl.michalboryczko.exercise.source.api.firebase.FirestoreApiService
import pl.michalboryczko.exercise.source.repository.*
import com.google.firebase.firestore.FirebaseFirestoreSettings




@Module
class InteractorModule {


    @Provides
    fun providePriceStatusChecker(): PriceStatusChecker {
        return PriceStatusChecker()
    }

    @Provides
    fun provideRepository(userRepository: UserRepository,
                          firestoreApiService: FirestoreApiService,
                          checker: InternetConnectivityChecker): Repository {
        return RepositoryImpl(userRepository, firestoreApiService, checker)
    }


    @Provides
    fun provideUserRepository(firebaseApiService: FirebaseApiService, checker: InternetConnectivityChecker): UserRepository {
        return UserRepositoryImpl(firebaseApiService, checker)
    }

}