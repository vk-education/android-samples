package ru.hse.lection04.presentationlayer.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.hse.lection04.R;
import ru.hse.lection04.objects.LogEntry;

public class LogViewHolder extends RecyclerView.ViewHolder {
    protected static final String MESSAGE_PATTERN = "[%s] - %s";


    protected final TextView mText;


    public LogViewHolder(@NonNull View itemView) {
        super(itemView);

        mText = itemView.findViewById(R.id.text);
    }


    public void bind(LogEntry item) {
        final String message = String.format(MESSAGE_PATTERN, item.time, item.message);
        mText.setText(message);
    }
}
