package ru.hse.lection04.businesslayer;

import java.util.HashSet;
import java.util.Set;

public class AbstractCallbackProvider<LISTENER> {
    protected static final int EMPTY = 0;

    protected final Set<LISTENER> mListeners = new HashSet<>();


    public void register(LISTENER listener) {
        final int lastSize = mListeners.size();

        mListeners.add(listener);

        if (lastSize == EMPTY) {
            setActivation(true);
        }
    }

    public void unregister(LISTENER listener) {
        mListeners.remove(listener);

        if (mListeners.size() == EMPTY) {
            setActivation(false);
        }
    }


    protected void setActivation(boolean value) {

    }
}
