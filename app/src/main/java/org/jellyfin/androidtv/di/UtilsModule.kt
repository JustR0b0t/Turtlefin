package app.turtlefin.androidtv.di

import app.turtlefin.androidtv.util.ImageHelper
import org.koin.dsl.module

val utilsModule = module {
	single { ImageHelper(get()) }
}
