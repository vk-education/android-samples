package ru.hse.lection03.presentationlayer.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import ru.hse.lection03.R;

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


    @SuppressLint("ResourceAsColor")
    void bind(Short number) {
        // Ставим имя дроида
        mName.setText(Short.toString(number));

        // Ставим цвет, в зависимости от состояния дроида
        switch (number % 2) {
            case 0:
                mName.setTextColor(Color.parseColor("#FF0000"));
                break;

            case 1:
                mName.setTextColor(Color.parseColor("#0000FF"));
                break;

            default:
                //mImage.setImageResource(R.color.color_black);
                mName.setTextColor(Color.parseColor("#000000"));
                break;
        }
    }
}
