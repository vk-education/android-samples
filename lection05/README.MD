# Description
Для работы проекта требуется:
- Зарегистрироваться на сайте **RapidApi** - https://rapidapi.com/
- Перейти на страничку **API Giphy** - https://rapidapi.com/giphy/api/giphy
- Требуется запомнить ключик из **X-RapidAPI-Key**, можно выбрать один из вариантов (значение этого поля можно сохранить в перемнных среды как **RAPID_API_KEY**)
- Требуется запомнить ключик из **api_key**, можно скопировать то, что в описании под полем (значение этого поля можно сохранить в перемнных среды как **RAPID_API_GIPHY_KEY**)


Если не хочется прописывать в переменных среды эти значения, то надо будет их вставить в gradle файле, вместо строчек (**System.getenv(...)**)
https://github.com/mailru-android-edu/hse-android-samples/blob/main/lection05/common/giphy/build.gradle