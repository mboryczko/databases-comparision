package pl.michalboryczko.exercise.viewmodel

import androidx.lifecycle.Observer
import org.mockito.Mockito.*
import pl.michalboryczko.exercise.BaseTest
import pl.michalboryczko.exercise.model.CryptocurrencyPair
import pl.michalboryczko.exercise.model.base.Resource
import pl.michalboryczko.exercise.source.repository.Repository

class CryptocurrencyPairViewModelTest: BaseTest(){


    private val observerState = mock<Observer<Resource<List<CryptocurrencyPair>>>>()
    private val repo = mock(Repository::class.java)
    //private val viewmodel by lazy { CryptocurrencyPairViewModel(repo, checker, Schedulers.trampoline(), Schedulers.trampoline()) }



    /*@Before
    override fun setUp(){
        viewmodel.cryptocurrencyPairs.observeForever(observerState)
        viewmodel.cryptocurrencyPairs.value = Resource.loading()
    }*/


   /* @Test
    fun noInternetTest(){
        whenever(repo.getCryptocurrencyPairs()).thenReturn(Single.error(NoInternetException()))
        viewmodel.getCryptocurrencyPairs()

        assertEquals(Resource.error<NoInternetException>(R.string.noInternetConnectionError), viewmodel.cryptocurrencyPairs.value)
    }

    @Test
    fun getCryptocurrencyPairsTest(){
        val mapper = CryptocurrencyMapper()
        val cryptocurrencyPair = mapper.mergeCryptocurrencyPairAndTicker(pairResponseMock, tickerResponseMock)

        whenever(repo.getCryptocurrencyPairs()).thenReturn(Single.just(listOf(cryptocurrencyPair)))
        viewmodel.getCryptocurrencyPairs()

        assertEquals(Resource.success(listOf(cryptocurrencyPair)), viewmodel.cryptocurrencyPairs.value)
    }
*/

}