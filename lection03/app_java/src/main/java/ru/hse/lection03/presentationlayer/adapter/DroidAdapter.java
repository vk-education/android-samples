package ru.hse.lection03.presentationlayer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.hse.lection03.R;
import ru.hse.lection03.objects.Droid;

public class DroidAdapter extends RecyclerView.Adapter<DroidViewHolder> {
    protected final DroidViewHolder.IListener mListener;
    protected final List<Droid> mData;


    public DroidAdapter(List<Droid> data, DroidViewHolder.IListener listener) {
        mListener = listener;
        mData = data;
    }


    // Инициализируем ViewHolder
    @NonNull
    @Override
    public DroidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Получаем инфлейтер и создаем нужный layout
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View layout = inflater.inflate(R.layout.item_droid, parent, false);

        // Создаем ViewHolder
        return new DroidViewHolder(layout, mListener);
    }

    // Вставляем данные во ViewHolder
    @Override
    public void onBindViewHolder(@NonNull DroidViewHolder holder, int position) {
        final Droid item = mData.get(position);

        holder.bind(item);
    }

    // Размер данных
    @Override
    public int getItemCount() {
        return mData.size();
    }
}