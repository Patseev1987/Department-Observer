package ru.bogdan.login_feature.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import di.ViewModelKey
import ru.bogdan.login_feature.ui.LoginViewModel

@Module
interface LoginViewModelModule {
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    @Binds
    fun bindLoginViewModel(impl: LoginViewModel): ViewModel
}