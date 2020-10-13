package ru.hse.lection03.businesslayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.hse.lection03.objects.Droid;

public class DroidRepository {
    // Константы
    public static final int DATA_SIZE = 100;
    public static final float STATE_CRITICAL = 0.8f;

    // Для генерации случайных чисел
    public static final Random RANOMIZER = new Random();


    // Объекты для реализации хардкорного синглтона в java
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


    protected final List<Droid> mData;


    private DroidRepository() {
        mData = initializeData();
    }


    // получить список Дроидов
    public List<Droid> list() {
        return mData;
    }

    // получить дроида по индексу
    public Droid item(int index) {
        return mData.get(index);
    }


    // Функция инициализации списка дроидов
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
}
