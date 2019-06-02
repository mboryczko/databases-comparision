package pl.michalboryczko.exercise.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import pl.michalboryczko.exercise.di.ViewModelKey
import pl.michalboryczko.exercise.ui.activesession.ActiveSessionActivity
import pl.michalboryczko.exercise.ui.activesession.ActiveSessionViewModel
import pl.michalboryczko.exercise.ui.session.SessionActivity
import pl.michalboryczko.exercise.ui.session.SessionViewModel
import pl.michalboryczko.exercise.ui.login.LoginActivity
import pl.michalboryczko.exercise.ui.login.LoginViewModel
import pl.michalboryczko.exercise.ui.mysessions.MySessionsActivity
import pl.michalboryczko.exercise.ui.mysessions.MySessionsViewModel
import pl.michalboryczko.exercise.ui.register.RegisterActivity
import pl.michalboryczko.exercise.ui.register.RegisterViewModel

@Module
internal abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun bindRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector
    internal abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    internal abstract fun bindMenuActivity(): SessionActivity


    @ContributesAndroidInjector
    internal abstract fun bindActiveSessionActivity(): ActiveSessionActivity


    @ContributesAndroidInjector
    internal abstract fun bindMySessionsActivity(): MySessionsActivity


    @Binds
    @IntoMap
    @ViewModelKey(MySessionsViewModel::class)
    abstract fun bindMySessionsViewModel(viewModel: MySessionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ActiveSessionViewModel::class)
    abstract fun bindActiveSessionViewModel(viewModel: ActiveSessionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SessionViewModel::class)
    abstract fun bindMenuViewModel(viewModel: SessionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(viewModel: RegisterViewModel): ViewModel

}