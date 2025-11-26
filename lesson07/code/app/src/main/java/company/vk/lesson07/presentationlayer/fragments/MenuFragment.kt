package company.vk.lesson07.presentationlayer.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import company.vk.lesson07.R
import company.vk.lesson07.businesslayer.PlateProviders
import company.vk.lesson07.presentationlayer.models.MenuViewModel


class MenuFragment: AbstractFragment<MenuFragment.MenuViewHolder>(R.layout.content_menu) {
    protected val viewModel by viewModels<MenuViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.providers().handle(::applyResult)
    }

    override fun createHolder(view: View) = MenuViewHolder(view)

    protected fun applyResult(result: Array<PlateProviders>) {
        result.forEach { addButton(it) }

        when (result.isEmpty()) {
            true -> applyStub(false, "empty")
            false -> applyStub(false, null)
        }
    }

    protected fun addButton(provider: PlateProviders) {
        val parent = holder?.layout
        if (parent == null) {
            return
        }

        val button = layoutInflater.inflate(R.layout.item_button, parent, false) as Button
        button.text = provider.name
        button.setOnClickListener { showPlateListFragment(provider) }

        parent.addView(button)
    }

    protected fun showPlateListFragment(provider: PlateProviders) {
        if (!checkFilePermission(provider)) {
            return
        }

        val fragment = PlateListFragment.newInstance(provider)

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.host, fragment, PlateListFragment.TAG)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    protected fun checkFilePermission(provider: PlateProviders): Boolean {
        if (provider != PlateProviders.EXTERNAL_DATA_DIR) {
            return true
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            return true
        }

        if (Environment.isExternalStorageManager()) {
            return true
        }

        val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
        startActivity(intent)

        return false
    }

    class MenuViewHolder(view: View): ViewHolder(view) {
        val layout by lazy { view.findViewById<ViewGroup>(R.id.layout) }
    }

    companion object {
        const val TAG = "MenuFragment"
    }
}
