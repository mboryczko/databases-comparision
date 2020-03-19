package pl.michalboryczko.exercise.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import pl.michalboryczko.exercise.di.ViewModelKey
import pl.michalboryczko.exercise.ui.main.MainActivity
import pl.michalboryczko.exercise.ui.main.MainViewModel

@Module
internal abstract class ActivityModule {


    @ContributesAndroidInjector
    internal abstract fun bindLoginActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindLoginViewModel(viewModel: MainViewModel): ViewModel


}