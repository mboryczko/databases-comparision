package pl.michalboryczko.exercise.di.modules


import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import pl.michalboryczko.exercise.di.ViewModelKey
import pl.michalboryczko.exercise.ui.activesession.chat.ChatFragment
import pl.michalboryczko.exercise.ui.activesession.chat.ChatViewModel
import pl.michalboryczko.exercise.ui.activesession.chat.SessionFragment

@Module
internal abstract class FragmentModule {

    @ContributesAndroidInjector()
    internal abstract fun bindChatFragment(): ChatFragment


    @ContributesAndroidInjector()
    internal abstract fun bindSessionFragment(): SessionFragment


    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    abstract fun bindActiveSessionViewModel(viewModel: ChatViewModel): ViewModel

}