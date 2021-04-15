package ru.hse.lection05.presentationlayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.hse.lection05.R
import ru.hse.lection05.presentationlayer.presenters.PlacePresenter
import ru.hse.lection05.presentationlayer.views.IPlaceView

class SplashFragment: AbstractFragment(), IPlaceView {
    protected val presenter = PlacePresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.contetn_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attachView(this)
    }

    override fun onStart() {
        super.onStart()

        presenter.startTimer()
    }


    override fun onDestroyView() {
        super.onDestroyView()

        presenter.detachView(this)
    }

    override fun nextStep() {
        navigator().mainScreen()
    }
}





