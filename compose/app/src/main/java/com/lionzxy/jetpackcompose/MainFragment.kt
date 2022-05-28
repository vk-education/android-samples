package com.lionzxy.jetpackcompose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lionzxy.jetpackcompose.ui.ComposableCounterViewModel
import com.lionzxy.jetpackcompose.viewmodel.CounterViewModel

class MainFragment : Fragment() {
    private val counterViewModel: CounterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(inflater.context).apply {
            setContent {
                ComposableCounterViewModel(counterViewModel)
            }
        }
    }
}
