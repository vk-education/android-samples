package ru.hse.lection05.presentationlayer.presenters

import android.os.Handler
import android.os.Looper
import ru.hse.lection05.businesslayer.repositories.RepositoryFactory
import ru.hse.lection05.businesslayer.repositories.IPlaceRepository
import ru.hse.lection05.presentationlayer.views.IPlaceView
import java.util.concurrent.TimeUnit

class PlacePresenter {
    protected val handler = Handler(Looper.getMainLooper())

    protected var view: IPlaceView? = null



    fun attachView(view: IPlaceView) {
        this.view = view
    }

    fun detachView(view: IPlaceView) {
        this.view = null
    }

    fun startTimer() {
        handler.postDelayed( {view?.nextStep()} , TimeUnit.SECONDS.toMillis(1))
    }
}