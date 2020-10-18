package ru.hse.lection04.presentationlayer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import ru.hse.lection04.R;
import ru.hse.lection04.objects.LogEntry;

public class LogAdapter extends ListAdapter<LogEntry, LogViewHolder> {
    public LogAdapter() {
        super(new Differ());
    }


    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_log, parent, false);

        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        final LogEntry item = getItem(position);
        holder.bind(item);
    }


    protected static class Differ extends DiffUtil.ItemCallback<LogEntry> {
        @Override
        public boolean areItemsTheSame(@NonNull LogEntry oldItem, @NonNull LogEntry newItem) {
            return oldItem.time.equals(newItem.time);
        }

        @Override
        public boolean areContentsTheSame(@NonNull LogEntry oldItem, @NonNull LogEntry newItem) {
            return oldItem.message.equals(newItem.message);
        }
    }
}
