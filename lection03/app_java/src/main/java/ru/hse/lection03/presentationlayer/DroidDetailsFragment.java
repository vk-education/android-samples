package ru.hse.lection03.presentationlayer;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.Serializable;

import ru.hse.lection03.R;

public class DroidDetailsFragment extends Fragment {
    protected static final String EXTRAS = "NUMBER";


    // helper-метод для создания инстанса фрагмента
    // Это один из подходов в упрощении
    public static DroidDetailsFragment newInstance(Short number) {
        // Создаем данные, которые потом положим в фрагмент
        final Bundle extras = new Bundle();
        extras.putSerializable(EXTRAS, number);

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

        final Short number = number();
        if (number != null) {
            // Устанавливаем имя
            TextView Name = view.findViewById(R.id.name);
            Name.setText(Short.toString(number));

            switch (number % 2) {
                case 0:
                    Name.setTextColor(Color.parseColor("#FF0000"));
                    break;

                case 1:
                    Name.setTextColor(Color.parseColor("#0000FF"));
                    break;

                default:
                    Name.setTextColor(Color.parseColor("#000000"));
                    break;
            }
        }

    }


    // Метод для доставания объекта Droid из аргументов фрагмента
    public Short number(){
        if (getArguments() == null) {
            return null;
        }

        final Serializable number = getArguments().getSerializable(EXTRAS);
        if (number instanceof Short) {
            return (Short) getArguments().getSerializable(EXTRAS);
        }

        return null;
    }



}
