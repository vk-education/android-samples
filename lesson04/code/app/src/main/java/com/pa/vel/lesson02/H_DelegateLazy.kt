package com.pa.vel.lesson02

class JudgingManager {
    // Тяжелая операция: загружаем и парсим огромную таблицу
    // судейских назначений только тогда, когда она реально понадобится
    val assignmentsMasterFile by lazy {
        SpreadsheetParser.load("assignments_v2.xlsx")
    }

    val titleView: TextView by lazy(LazyThreadSafetyMode.NONE) { // если один поток!
        // root.findViewById(...)
        TextView()
    }
}

class SpreadsheetParser {
    companion object {
        fun load(s: String) {
            // ...
        }
    }

}

class TextView