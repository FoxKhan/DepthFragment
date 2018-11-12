package com.developer.smartfox.fragmentdemo.di

import com.developer.smartfox.fragmentdemo.domain.DepthInteractor
import dagger.Component
import javax.inject.Singleton

@Component()
@Singleton
interface AppComponent {

    val depthInteractor: DepthInteractor
}
