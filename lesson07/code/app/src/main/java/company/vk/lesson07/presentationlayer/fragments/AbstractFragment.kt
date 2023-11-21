package company.vk.lesson07.presentationlayer.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import company.vk.lesson07.R
import company.vk.lesson07.businesslayer.PlateProviders
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

abstract class AbstractFragment<HOLDER: AbstractFragment.ViewHolder>(@LayoutRes contentLayoutId: Int): Fragment(contentLayoutId) {
    protected var holder: HOLDER? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        holder = createHolder(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        holder = null
    }

    protected open fun <T> Flow<T>.handle(action: suspend (value: T) -> Unit): Job {
        return onStart { applyStub(true, null) }
            .catch { applyStub(false, it.message?: "UNKNOWN") }
            .onEach {
                applyStub(false, null)
                action(it)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    protected open fun applyStub(pending: Boolean, message: String?) {
        holder?.pending?.isVisible = pending
        holder?.message?.apply {
            text = message
            isVisible = message != null
        }
    }

    protected fun provider(): PlateProviders {
        val providerName = arguments?.getString(EXTRAS_PROVIDER)?: "NONE"
        return PlateProviders.valueOf(providerName)
    }

    protected abstract fun createHolder(view: View): HOLDER

    open class ViewHolder(val view: View) {
        val message by lazy { view.findViewById<TextView>(R.id.message) }
        val pending by lazy { view.findViewById<View>(R.id.pending) }
    }

    companion object {
        @JvmStatic protected val EXTRAS_PROVIDER = "EXTRAS_PROVIDER"
    }
}
