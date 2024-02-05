package com.osamaaftab.bankmisr.app

import android.app.Application
import com.osamaaftab.bankmisr.di.module.ApiServicesModule
import com.osamaaftab.bankmisr.di.module.AppModule
import com.osamaaftab.bankmisr.di.module.NetWorkModule
import com.osamaaftab.bankmisr.di.module.RemoteDataSourceModule
import com.osamaaftab.bankmisr.di.module.RepositoryModule
import com.osamaaftab.bankmisr.di.module.UseCaseModule
import com.osamaaftab.bankmisr.di.module.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    ApiServicesModule,
                    AppModule,
                    NetWorkModule,
                    RepositoryModule,
                    UseCaseModule,
                    ViewModelModule,
                    RemoteDataSourceModule,
                )
            )
        }
    }
}