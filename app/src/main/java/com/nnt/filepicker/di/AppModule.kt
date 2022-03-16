package com.nnt.filepicker

import androidx.lifecycle.ViewModelProvider
import com.nnt.filepicker.di.ViewModelFactory
import com.nnt.filepicker.extension.bindViewModel
import com.nnt.filepicker.imagepicker.GalleryViewModel
import com.nnt.filepicker.imagepicker.datasource.ImageDataSource
import com.nnt.filepicker.imagepicker.datasource.ImageDataSourceImpl
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


val appModule = Kodein.Module("APP_MODULE", false){
    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein.direct) }
    bind<ImageDataSource>() with provider {
        ImageDataSourceImpl()
    }
    bindViewModel<GalleryViewModel>() with provider {
        GalleryViewModel(instance())
    }
}