package ru.hse.lection03.presentationlayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.Serializable;

import ru.hse.lection03.R;
import ru.hse.lection03.objects.Droid;

public class DroidDetailsFragment extends DialogFragment {
    protected static final String EXTRAS_DROID = "DROID";


    // helper-метод для создания инстанса фрагмента
    // Это один из подходов в упрощении
    public static DroidDetailsFragment newInstance(Droid droid) {
        // Создаем данные, которые потом положим в фрагмент
        final Bundle extras = new Bundle();
        extras.putSerializable(EXTRAS_DROID, droid);

        final DroidDetailsFragment fragment = new DroidDetailsFragment();
        fragment.setArguments(extras);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Droid droid = droid();
        if (droid != null) {
            // Устанавливаем имя
            ((TextView) view.findViewById(R.id.name)).setText(droid.name);

            final TextView state = view.findViewById(R.id.state);

            switch (droid.state) {
                case Droid.STATE_REMOVED:
                    state.setText(R.string.caption_droid_state_removed);
                    state.setBackgroundResource(R.color.color_red);
                    break;

                case Droid.STATE_NEW:
                    state.setText(R.string.caption_droid_state_new);
                    state.setBackgroundResource(R.color.color_green);
                    break;

                default:
                    state.setText(R.string.caption_droid_state_unknown);
                    state.setBackgroundResource(R.color.color_black);
                    break;
            }
        }
    }


    // Метод для доставания объекта Droid из аргументов фрагмента
    public Droid droid(){
        if (getArguments() == null) {
            return null;
        }

        final Serializable droid = getArguments().getSerializable(EXTRAS_DROID);
        if (droid instanceof Droid) {
            return (Droid) getArguments().getSerializable(EXTRAS_DROID);
        }

        return null;
    }
}
