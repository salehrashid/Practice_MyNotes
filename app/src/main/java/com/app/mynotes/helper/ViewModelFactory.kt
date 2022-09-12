package com.app.mynotes.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.mynotes.ui.insert.NoteAddUpdateActivity
import com.app.mynotes.ui.insert.NoteAddUpdateViewModel
import com.app.mynotes.ui.main.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTACE: ViewModelFactory? = null

        @JvmStatic
        fun getInstace(application: Application): ViewModelFactory {
            if (INSTACE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTACE = ViewModelFactory(application)
                }
            }
            return INSTACE as ViewModelFactory
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(NoteAddUpdateViewModel::class.java)){
            return NoteAddUpdateViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}