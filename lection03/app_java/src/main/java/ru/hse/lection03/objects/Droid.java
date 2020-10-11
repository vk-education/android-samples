package ru.hse.lection03.objects;

import java.io.Serializable;

public class Droid implements Serializable {
    private static final long serialVersionUID = 8042222337958048164L;

    public static final int STATE_REMOVED = 0;
    public static final int STATE_NEW = 1;


    public String name;
    public int state;
}
