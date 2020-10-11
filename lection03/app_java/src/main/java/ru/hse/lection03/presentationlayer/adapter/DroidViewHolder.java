package ru.hse.lection03.presentationlayer.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import ru.hse.lection03.R;
import ru.hse.lection03.objects.Droid;

public class DroidViewHolder extends RecyclerView.ViewHolder {
    public interface IListener {
        void onDroidClicked(int position);
    }


    protected final IListener mListener;
    protected final TextView mName;
    protected final ImageView mImage;


    public DroidViewHolder(View itemView, IListener listener) {
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