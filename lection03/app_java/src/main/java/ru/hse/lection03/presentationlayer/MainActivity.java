package ru.hse.lection03.presentationlayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.Serializable;

import ru.hse.lection03.R;
import ru.hse.lection03.businesslayer.DroidRepository;
import ru.hse.lection03.objects.Droid;

public class MainActivity extends AppCompatActivity implements DroidListFragment.IListener {
    protected static final String EXTRAS_DROID = "DROID";

    protected static final String TAG_DETAILS = "DETAILS";
    protected static final String TAG_DETAILS_DIALOG = "TAG_DETAILS_DIALOG";

    protected static final int DEFAULT_DROID_INDEX = 0;


    // Вариант кода, для android:launchMode="singleInstance"
//    public static Intent droidDetailsIntent(Context context, Droid droid) {
//        return new Intent(context, MainActivity.class)
//                .putExtra(EXTRAS_DROID, droid);
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        final boolean isDual = getResources().getBoolean(R.bool.is_dual);

        // Проверяем что эта Activity не имеет сохраненного стейта и вставляем свой фрагмент
        // Если стейт есть, тогда фрагмент будет восстановлен без нашего участия
        if (savedInstanceState == null) {
            if (isDual) {
                final Droid droid = DroidRepository.getInstance().item(DEFAULT_DROID_INDEX);
                showDetails(droid);
            }
        } else {
            // Произошло восстановление Activity savedInstanceState
            // Убедимся, что у нас в текущей конфигурации нет лишних объектов
            checkDetails(isDual);
        }
    }


    // Activity имеет специальный флаг в Manifest android:launchMode="singleInstance", поэтому startActivity пришел сюда
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // Вариант кода, для android:launchMode="singleInstance"
//        final Serializable droid = intent.getSerializableExtra(EXTRAS_DROID);
//        if (droid instanceof Droid) {
//            showDetails((Droid) droid);
//        }
    }

    // Вариант кода, для общения с activity без Intent
    @Override
    public void onDroidClicked(Droid droid) {
        showDetails(droid);
    }


    protected void showDetails(Droid droid) {
        if (droid == null) {
            // просто проверка. Непонятно как сюда попали. По хорошему надо залогировать
            // Но, в рамках примера, игнорируем исполнение
            return;
        }


        final DroidDetailsFragment detailsFragment = DroidDetailsFragment.newInstance(droid);

        final boolean isDual = getResources().getBoolean(R.bool.is_dual);
        if (isDual) {
            // Отображаем детали Дроида во второй панели
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.details, detailsFragment, TAG_DETAILS)        // заменить фрагмент
                    .commitAllowingStateLoss();
        } else {
            // Отображаем детали Дроида, как диалог
            detailsFragment.show(getSupportFragmentManager(), TAG_DETAILS_DIALOG);
        }
    }

    protected void checkDetails(boolean isDual) {
        if (isDual) {
            // Достанем диаложный фрагмент из FragmentManager
            final Fragment tmp = getSupportFragmentManager().findFragmentByTag(TAG_DETAILS_DIALOG);

            if (tmp instanceof DroidDetailsFragment) {
                // Диалог есть в стэке
                final DroidDetailsFragment dialog = (DroidDetailsFragment) tmp;

                // Закрываем диалог, т.к. у нас двухпанельный режим
                dialog.dismissAllowingStateLoss();

                // показываем панель с тем Дроидом, который был в диалоге
                final Droid droid = dialog.droid();
                showDetails(droid);
            } else {
                // Диалога не оказалось в стэке

                // Ищем, есть ли в стэке панельный фрагмент
                final Fragment tmpDetails = getSupportFragmentManager().findFragmentByTag(TAG_DETAILS);
                if (tmpDetails == null) {
                    // В стэке его нет. Значит устанавливаем детали по дефолту
                    final Droid droid = DroidRepository.getInstance().item(DEFAULT_DROID_INDEX);
                    showDetails(droid);
                }
            }
        } else {
            // Достанем панельный фрагмент из FragmentManager
            final Fragment tmp = getSupportFragmentManager().findFragmentByTag(TAG_DETAILS);
            if (tmp instanceof DroidDetailsFragment) {
                final DroidDetailsFragment details = (DroidDetailsFragment) tmp;

                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(details)                // удалим фрагмент
                        .commitAllowingStateLoss();


                final Droid droid = details.droid();
                showDetails(droid);
            }
        }
    }
}