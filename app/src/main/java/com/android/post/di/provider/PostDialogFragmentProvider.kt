package com.android.post.di.provider

import com.android.post.presentation.post.PostDialogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PostDialogFragmentProvider {

    @ContributesAndroidInjector
    abstract fun providePostDialogFragment(): PostDialogFragment
}