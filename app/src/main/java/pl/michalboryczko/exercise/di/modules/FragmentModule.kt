package pl.michalboryczko.exercise.di.modules


import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import pl.michalboryczko.exercise.di.ViewModelKey
import pl.michalboryczko.exercise.ui.learnwords.WordsLearningFragment
import pl.michalboryczko.exercise.ui.learnwords.WordsLearningViewModel
import pl.michalboryczko.exercise.ui.settings.SettingsFragment
import pl.michalboryczko.exercise.ui.settings.SettingsViewModel

@Module
internal abstract class FragmentModule {

    @ContributesAndroidInjector()
    internal abstract fun bindSettingsFragment(): SettingsFragment

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel


    @ContributesAndroidInjector()
    internal abstract fun bindWordsLearningFragment(): WordsLearningFragment


    @Binds
    @IntoMap
    @ViewModelKey(WordsLearningViewModel::class)
    abstract fun bindWordsLearningViewModel(viewModel: WordsLearningViewModel): ViewModel
}