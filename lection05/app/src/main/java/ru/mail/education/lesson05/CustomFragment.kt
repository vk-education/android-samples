package ru.mail.education.lesson05

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.mail.education.lesson05.databinding.FragmentSimpleBinding
import kotlin.random.Random

class CustomFragment: Fragment() {
    interface IListener {
        fun openFragment()
    }


    protected var _binding: FragmentSimpleBinding? = null
    protected val binding get() = _binding!!

    protected var cahcedColor: Int? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSimpleBinding.inflate(inflater)

        Log.d(TAG, "savedInstanceState = $savedInstanceState")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.d(TAG, "cahcedColor = $cahcedColor")

        val oldColor = savedInstanceState?.getInt(EXTRAS_COLOR)

        val seed = Random.nextFloat()
        cahcedColor = when {
            oldColor != null -> oldColor
            cahcedColor != null -> cahcedColor
            seed < 0.3 -> Color.BLUE
            seed < 0.6 -> Color.BLACK
            else -> Color.RED
        }

        view.setBackgroundColor(cahcedColor!!)


        binding.time.text = System.currentTimeMillis().toString()
        binding.replaceFragmentWithStack.setOnClickListener {
            listener<IListener>().openFragment()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(EXTRAS_COLOR, (requireView().background as ColorDrawable).color)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    inline fun <reified LISTENER> listener(): LISTENER {
        (parentFragment as? LISTENER)?.let {
            return it
        }

        return activity as LISTENER
    }


    companion object {
        const val TAG = "CustomFragment"
        const val EXTRAS_COLOR = "EXTRAS_COLOR"


        fun newInstance(): CustomFragment {
            return CustomFragment()
        }
    }
}