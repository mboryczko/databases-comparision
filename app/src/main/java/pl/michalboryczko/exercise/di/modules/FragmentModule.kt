package pl.michalboryczko.exercise.di.modules


import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import pl.michalboryczko.exercise.di.ViewModelKey
import pl.michalboryczko.exercise.ui.learnwords.WordsLearningFragment
import pl.michalboryczko.exercise.ui.learnwords.WordsLearningViewModel
import pl.michalboryczko.exercise.ui.markwords.MarkWordsFragment
import pl.michalboryczko.exercise.ui.markwords.MarkWordsViewModel
import pl.michalboryczko.exercise.ui.search.SearchFragment
import pl.michalboryczko.exercise.ui.search.SearchViewModel
import pl.michalboryczko.exercise.ui.search.pager.WordsLearnedFragment
import pl.michalboryczko.exercise.ui.search.pager.WordsToLearnFragment
import pl.michalboryczko.exercise.ui.settings.SettingsFragment
import pl.michalboryczko.exercise.ui.settings.SettingsViewModel
import pl.michalboryczko.exercise.ui.test.TestFragment

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


    @ContributesAndroidInjector()
    internal abstract fun bindTestFragment(): TestFragment

    @Binds
    @IntoMap
    @ViewModelKey(WordsLearningViewModel::class)
    abstract fun bindWordsLearningViewModel(viewModel: WordsLearningViewModel): ViewModel

    @ContributesAndroidInjector()
    internal abstract fun bindSearchFragment(): SearchFragment

    @ContributesAndroidInjector()
    internal abstract fun bindWordsLearnedFragment(): WordsLearnedFragment

    @ContributesAndroidInjector()
    internal abstract fun bindWordsToLearnFragment(): WordsToLearnFragment

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @ContributesAndroidInjector()
    internal abstract fun bindMarkWordsFragment(): MarkWordsFragment

    @Binds
    @IntoMap
    @ViewModelKey(MarkWordsViewModel::class)
    abstract fun bindMarkWordsViewModel(viewModel: MarkWordsViewModel): ViewModel
}