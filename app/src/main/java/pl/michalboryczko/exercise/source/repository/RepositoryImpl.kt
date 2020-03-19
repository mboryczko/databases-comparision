package pl.michalboryczko.exercise.source.repository


import pl.michalboryczko.exercise.source.api.InternetConnectivityChecker
import pl.michalboryczko.exercise.source.api.rest.Api

class RepositoryImpl(
        private val userRepository: UserRepository,
        private val api: Api,
        private val checker: InternetConnectivityChecker
) :Repository, NetworkRepository(checker) {



}