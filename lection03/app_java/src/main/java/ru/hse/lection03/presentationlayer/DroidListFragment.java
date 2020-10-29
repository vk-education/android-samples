package ru.hse.lection03.presentationlayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.hse.lection03.R;
import ru.hse.lection03.businesslayer.DroidRepository;
import ru.hse.lection03.presentationlayer.adapter.DroidAdapter;
import ru.hse.lection03.presentationlayer.adapter.DroidViewHolder;

public class DroidListFragment extends Fragment {
    // Вариант кода, для общения с activity без Intent
    public interface IListener {
        public void onDroidClicked(Short number);
    }


    // Вариант кода, для общения с activity без Intent
    protected IListener mListener;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);


        if (requireActivity() instanceof IListener) {
            mListener = (IListener) requireActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // устанавливаем верстку
        return inflater.inflate(
                R.layout.content_list   // ресурс для "надувания" в дерево View
                , container             // родитель, куда потом будет вставлена верстка
                , false     // стоит false, что бы инфлейтер вернул верстку.
                // Если поставить true, то инфлейтер вставит верстку в parent, и вернет тоже parent
                // Мы сами в шоке от того, почему была сделана такая логика работы метод:(
        );
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // инициализируем View для отображения списка
        final RecyclerView recycler = view.findViewById(R.id.recycler);
        int numberOfColumns = getResources().getInteger(R.integer.number_of_columns);

        recycler.setAdapter(new DroidAdapter(DroidRepository.getInstance().list(), new DroidClickHandler()));
        recycler.setLayoutManager(new GridLayoutManager(requireContext(), numberOfColumns));

        Button buttonNewView = (Button) getView().findViewById(R.id.button);
        buttonNewView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DroidRepository.getInstance().addNewNumber();
                recycler.setAdapter(new DroidAdapter(DroidRepository.getInstance().list(), new DroidClickHandler()));
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
    }


    // Одна из возможных реализаций отслеживания клика по элементу
    // обработчик клика по элементу
    class DroidClickHandler implements DroidViewHolder.IListener {
        @Override
        public void onDroidClicked(int position) {
            final Short number = DroidRepository.getInstance().item(position);

            // Вариант кода, для общения с activity без Intent
            if (mListener != null) {
                mListener.onDroidClicked(number);
            }



        }
    }
}
