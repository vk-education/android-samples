package ru.hse.lection04.businesslayer;

import ru.hse.lection04.R;

/**
 * Каналы, которые есть в приложении
 */
public enum Channels {
    CONNECTIVITY("CONNECTIVITY", R.string.app_channel_connectivity);


    public final String id;
    public final int nameResId;


    Channels(String id, int nameResId) {
        this.id = id;
        this.nameResId = nameResId;
    }
}
