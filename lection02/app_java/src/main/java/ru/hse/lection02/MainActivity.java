package ru.hse.lection02;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    // Константы
    public static final int DATA_SIZE = 100;
    public static final float STATE_CRITICAL = 0.8f;
    public static final Random RANOMIZER = new Random();


    // Ссылка на RecyclerView
    protected RecyclerView mRecycler = null;
    protected DroidAdapter mAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Устанавливаем верстку
        setContentView(R.layout.activity_main);


        // Генерим данные
        final List<Droid> data = initializeData();


        // Класс для отслеживания клика по элементу
        final PersonViewHolder.IListener stateToggler = new PersonViewHolder.IListener() {
            @Override
            public void onDroidClicked(int position) {

                // Изменяем состояние дроида
                final Droid droid = data.get(position);
                droid.state = (droid.state + 1) % 2;

                // Уведомляем адаптер, что поменялся один из его элементов
                mAdapter.notifyItemChanged(position);
            }
        };

        // Создаем адаптер
        mAdapter = new DroidAdapter(data, stateToggler);


        // Инициализируем RecyclerView
        mRecycler = findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(mRecycler.getContext()));
        mRecycler.setAdapter(mAdapter);
    }

    protected List<Droid> initializeData() {
        final List<Droid> data = new ArrayList<>();

        // Наполняем лист в цикле
        for (int position = 0; position < DATA_SIZE; position ++) {
            // Генерим имя дроида
            final String name = "Droid " + position;

            // Получаем случайное число, и определяем состояние дроида
            final int state;
            if (RANOMIZER.nextFloat() >= STATE_CRITICAL) {
                state = Droid.STATE_REMOVED;
            } else {
                state = Droid.STATE_NEW;
            }

            // Создаем дроида и добавляем его в список
            final Droid droid = new Droid();
            droid.name = name;
            droid.state = state;

            data.add(droid);
        }

        return data;
    }


    static class Droid {
        public static final int STATE_REMOVED = 0;
        public static final int STATE_NEW = 1;

        public String name;
        public int state;
    }

    static class PersonViewHolder extends RecyclerView.ViewHolder {
        public interface IListener {
            void onDroidClicked(int position);
        }


        protected final IListener mListener;
        protected final TextView mName;
        protected final ImageView mImage;


        public PersonViewHolder(View itemView, IListener listener) {
            super(itemView);

            mListener = listener;

            // Находим View, которые будут отвечать за имя и картинку
            mName = itemView.findViewById(R.id.name);
            mImage = itemView.findViewById(R.id.image);


            // Отслеживаем клик по элементу
            final View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onDroidClicked(getAdapterPosition());
                }
            };

            itemView.setOnClickListener(clickListener);
        }



        void bind(Droid item) {
            // Ставим имя дроида
            mName.setText(item.name);

            // Ставим цвет, в зависимости от состояния дроида
            switch (item.state) {
                case Droid.STATE_REMOVED:
                    mImage.setImageResource(R.color.color_red);
                    break;

                case Droid.STATE_NEW:
                    mImage.setImageResource(R.color.color_green);
                    break;

                default:
                    mImage.setImageResource(R.color.color_black);
                    break;
            }
        }
    }

    static class DroidAdapter extends RecyclerView.Adapter<PersonViewHolder> {
        protected final PersonViewHolder.IListener mListener;
        protected final List<Droid> mData;


        public DroidAdapter(List<Droid> data, PersonViewHolder.IListener listener) {
            mListener = listener;
            mData = data;
        }


        // Инициализируем ViewHolder
        @NonNull
        @Override
        public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            // Получаем инфлейтер и создаем нужный layout
            final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            final View layout = inflater.inflate(R.layout.item_droid, parent, false);

            // Создаем ViewHolder
            return new PersonViewHolder(layout, mListener);
        }

        // Вставляем данные во ViewHolder
        @Override
        public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
            final Droid item = mData.get(position);

            holder.bind(item);
        }

        // Размер данных
        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}