package com.ccortez.desafiobrqapplicationkotlin

import androidx.fragment.app.Fragment
import androidx.test.platform.app.InstrumentationRegistry
import dagger.android.support.AndroidSupportInjection

class TestInjector(private val fragment: Fragment) {

    fun inject() {
        val instrumentation
                = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext
                as MVVMApplication

//        DaggerTestApplicationComponent
//            .builder()
//            .appModule(testApplicationModule)
//            .create(app)
//            .inject(app)

        AndroidSupportInjection.inject(fragment)
    }
}