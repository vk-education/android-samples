package ru.hse.lection03.businesslayer;

import java.util.ArrayList;
import java.util.List;

public class DroidRepository {
    public static Short DATA_SIZE ;

    private static volatile DroidRepository mInstance;

    public static DroidRepository getInstance() {
        if (mInstance == null) {
            synchronized (DroidRepository.class) {
                if (mInstance == null) {
                    mInstance = new DroidRepository();
                }
            }
        }
        return mInstance;
    }


    protected final List<Short> mData;


    private DroidRepository() {
        mData = initializeData();
    }


    // получить список Дроидов
    public List<Short> list() {
        return mData;
    }

    // получить дроида по индексу
    public Short item(int index) {
        return mData.get(index);
    }

    public void addNewNumber() {
        mData.add(++DATA_SIZE);
    }

    // Функция инициализации списка дроидов
    protected List<Short> initializeData() {
        final List<Short> data = new ArrayList<>();

        // Наполняем лист в цикле
        for (short position = 0; position <= 100; position ++) {
            data.add(position);
        }
        DATA_SIZE=100;
        return data;
    }
}
