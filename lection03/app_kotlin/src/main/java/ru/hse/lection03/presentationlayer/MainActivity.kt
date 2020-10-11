package ru.hse.lection03.presentationlayer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.hse.lection03.R
import ru.hse.lection03.businesslayer.DroidRepository
import ru.hse.lection03.objects.Droid

class MainActivity : AppCompatActivity() {
    companion object {
        const val EXTRAS_DROID = "DROID"

        const val TAG_LIST = "LIST"
        const val TAG_DETAILS = "DETAILS"
        const val TAG_DETAILS_DIALOG = "DETAILS_DIALOG"

        const val DEFAULT_DROID_INDEX = 0


        fun droidDetailsIntent(context: Context, droid: Droid) = Intent(context, MainActivity::class.java)
                .putExtra(EXTRAS_DROID, droid)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        val isDual = resources.getBoolean(R.bool.is_dual)


        // Проверяем что эта Activity не имеет сохраненного стейта и вставляем свой фрагмент
        // Если стейт есть, тогда фрагмент будет восстановлен без нашего участия
        if (savedInstanceState == null) {

            // Устанавливаем фрагмент со списком
            supportFragmentManager
                    .beginTransaction()                             // начинаем транзакцию
                    .add(R.id.data, DroidListFragment(), TAG_LIST)  // добавляем франмент со списком
                    .commit()                                       // совершаем транзакцию


            if (isDual) {
                // У нас двухпанельный режим, устанавливаем детали по дефолту
                val droid = DroidRepository.instance.item(DEFAULT_DROID_INDEX)
                showDetails(droid)
            }
        } else {
            // Произошло восстановление Activity savedInstanceState
            // Убедимся, что у нас в текущей конфигурации нет лишних объектов
            checkDetails(isDual)
        }
    }


    // Activity имеет специальный флаг в Manifest launchMode="singleInstance", поэтому startActivity пришел сюда
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val droid = intent.getSerializableExtra(EXTRAS_DROID) as? Droid
        showDetails(droid)
    }


    protected fun showDetails(droid: Droid?) {
        if (droid == null) {
            // просто проверка. Непонятно как сюда попали. По хорошему надо залогировать
            // Но, в рамках примера, игнорируем исполнение
            return
        }


        val detailsFragment = DroidDetailsFragment.newInstance(droid)

        val isDual = resources.getBoolean(R.bool.is_dual)
        when(isDual) {
            true -> {
                // Отображаем детали Дроида во второй панели
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.details, detailsFragment, TAG_DETAILS)        // заменить фрагмент
                        .commitAllowingStateLoss()
            }

            false -> {
                // Отображаем детали Дроида, как диалог
                detailsFragment.show(supportFragmentManager, TAG_DETAILS_DIALOG)
            }
        }
    }

    protected fun checkDetails(isDual: Boolean) {
        when (isDual) {
            true -> {
                val dialog = supportFragmentManager.findFragmentByTag(TAG_DETAILS_DIALOG) as? DroidDetailsFragment
                when (dialog == null) {
                    // Диалога не оказалось в стэке
                    true -> {
                        val details = supportFragmentManager.findFragmentByTag(TAG_DETAILS) as? DroidDetailsFragment

                        if (details == null) {
                            // В стэке его нет. Значит устанавливаем детали по дефолту
                            val droid = DroidRepository.instance.item(DEFAULT_DROID_INDEX)
                            showDetails(droid)
                        }
                    }

                    // Диалог есть в стэке
                    false -> {
                        // Закрываем диалог, т.к. у нас двухпанельный режим
                        dialog.dismissAllowingStateLoss()

                        // показываем панель с тем Дроидом, который был в диалоге
                        val droid = dialog.droid()
                        showDetails(droid)
                    }
                }
            }

            false -> {
                val details = supportFragmentManager.findFragmentByTag(TAG_DETAILS) as? DroidDetailsFragment
                if (details != null) {
                    supportFragmentManager
                            .beginTransaction()
                            .remove(details)                // удалим фрагмент
                            .commitAllowingStateLoss()


                    val droid = details.droid()
                    showDetails(droid)
                }
            }
        }
    }
}
