# Лекция 4 – Jetpack Compose

[Презентация к лекции](https://cloud.mail.ru/public/1wck/LxA3BoQGJ)

**Jetpack Compose** – современный тулкит для построения пользовательского интерфейса, приходящий на замену Android View. 

## Дополнительные материалы

- [**Официальная документация**](https://developer.android.com/compose)

Полезна на начальных этапах, а также при ознакомлении с изменениями в новых версиях.

- [**Плагин vkompose**](https://github.com/VKCOM/vkompose)

Плагин необходимо [скачать с github](https://github.com/VKCOM/vkompose/releases/tag/0.6).

Выберите нужный вариант для вашей версии AndroidStudio и установите его в пункте меню "Плагины" в настройках студии.

- [**Осознанная оптимизация Compose**](https://habr.com/ru/companies/ozontech/articles/742854/)

Полезная статья, довольно подробно разбирается стабильность типов, пропускаемость, перезапускаемость и многое другое.

Вероятно не все будет понятно, если вы только начали изучать Compose, поэтому можно периодически к ней возвращаться.

- [**Про анимации на Compose**](https://habr.com/ru/companies/jugru/articles/683656/)

Очень подробная статья про анимации в Compose, тут есть практически все, что нужно знать.

- [**Navigation compose**](https://medium.com/@KaushalVasava/navigation-in-jetpack-compose-full-guide-beginner-to-advanced-950c1133740) (Medium, доступ только с VPN)

Большой гайд по инструменту для навигации от google – navigation-compose. Рассматривается передача параметров, вложенная навигация и диплинки.

# Основные моменты лекции

## Composable-функция

**Composable-функция** – функция, помеченная аннотацией `@Composable`, являющаяся некоторым элементом пользовательского интерфейса.

Composable-функции можно вызывать только из других Composable функций.

Название Composable-функции, которая ничего не возвращает, должно начинаться с большой буквы. 

## Layouts

[Layouts.kt](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/layout/Layouts.kt)

### Box

**`Box`** – layout, контейнер, который может располагать элементы в произвольном порядке в соответствии с переданным `Alignment`.

Alignment можно передать сразу в Box (параметр `contentAlignment`), а можно применить для конкретного элемента с помощью `Modifier.align()`.

### Column

**`Column`** – layout, контейнер, который размещает элементы вертикально строго друг под другом. Это аналог `LinearLayout` из Android View с переданным orientation = vertical. 

Column, по аналогии с Box, разрешает нам настраивать горизонтальный alignment у каждого элемента, то есть то, как будет располагаться элемент по горизонтали: справа, по центру или слева. Для одного элемента это можно сделать с помощью `Modifier.align()`, либо можно передать параметр `horizontalAlignment` в `Column`, таким образом настроив положение сразу всех элементов в контейнере.

В `Column` также можно передать `verticalArrangement` – положение всего контента по вертикали (сверху, по центру или снизу). Если мы рисуем в `Column` два небольших элемента, `Column` занимает весь экран, и передан `verticalArrangement = Arrangement.Center`, то эти два элемента будут располагаться друг под другом по центру экрана.

### Row

**`Row`** – layout, контейнер, который размещает элементы горизонтально строго друг за другом. Это аналог `LinearLayout` из Android View с переданным orientation = horizontal.

В целом это то же самое, что и Column, но дочерние элементы располагаются друг за другом горизонтально, а не вертикально. Поэтому мы можем настроить, как будет располагаться каждый элемент по вертикали: сверху, по центру или снизу с помощью `Modifier.align`, или же для всех элементов сразу, передав `verticalAlignment`.

## Списки

[Lists.kt](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/layout/Lists.kt)

Для отображения небольших списков можно перебрать список данных с помощью `forEach`, и для каждого айтема отобразить на UI некоторый элемент.

Для действительно больших списков этот подход не подойдет, т.к. Compose будет рисовать сразу все элементы, в следствие чего мы получим низкую производительность.

Для такого случая в Compose предусмотрены Lazy лейауты, которые отрисовывают только то, что видно на экране на данный момент.

К таким относятся:

- `LazyRow`
- `LazyColumn` 
- `LazyHorizontalGrid`
- `LazyVerticalGrid`

Обратите внимание, что в эти Composable-функции мы сразу не передаем composable-функцию для наших дочерних элементов. Так, например, LazyColumn/Row предоставляют нам LazyListScope, в котором мы можем вызвать функции `item`, `items`, в которые уже, в свою очередь, передадим Composable-функцию.

`item/items` дополнительно принимают параметры `key` и `contentType`.

В `key` мы можем передать лямбду – фабрику стабильных и уникальных ключей, представляющих элемент. Использование одного и того же ключа для нескольких элементов в списке не допускается. Тип ключа должен быть сохраняем через `Bundle`. Если передано значение `null`, ключ будет позицией в списке.

При указании ключа позиция прокрутки будет поддерживаться на основе ключа, что означает, что если вы добавляете/удаляете элементы перед текущим видимым элементом, элемент с указанным ключом будет сохранен как первый видимый.

Также можем передать лямбду `contentType` – фабрику content type-ов, если у нас их может быть несколько (к примеру, некоторые элементы списка – заголовки, а некоторые – картинки). 

Композиции элементов одного и того же типа можно было бы повторно использовать более эффективно. На самом деле результат от этого очень сомнительный, оптимизация сработает при изменении датасета и определенных трудно достижимых условиях. 

Тем не менее, `contentType` все равно следует передавать, если в Lazy layout может быть больше одного типа контента, поскольку ситуация может изменится в будущем, например, появится новая оптимизация, связанная с этим.

## Другие лейауты

[LayoutsPlus.kt](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/layout/LayoutsPlus.kt)

- `FlowRow` – `Row`, располагающий контент на следующей строке, если в текущем закончилось место.

- `FlowColumn` – аналогично `FlowRow`, располагает контент в следующем столбце, если в текущей закончилось место.

- `ConstraintLayout` – полный аналог `ConstraintLayout` из Android View.

## Modifier

[Modifiers.kt](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/modifier/Modifiers.kt)

Практически каждая Composable-функция принимает параметр `modifier: Modifier`. Он может как-либо модифицировать наш элемент. 

Modifier отвечает за:

- Изменение размера, макета, поведения и внешнего вида составного объекта.
- Добавление информации, например метки доступности.
- Обработка касаний
- Высокоуровневые взаимодействия, например кликабельность,скролл, перетаскивание или масштабируемость.

Ваши функции тоже должны принимать параметр `Modifier` и передавать его родительскому компоненту в вашей функции.

### Sizes

[ModifiersSizes.kt](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/modifier/ModifierSizes.kt)

С помощью Modifier можно установить размеры элемента,

- `fillMaxSize()`, `fillMaxWidth()`, `fillMaxHeight()`
- `wrapContentSize()`, `wrapContentWidth()`, `wrapContentHeight()`
- `size()`, `height()`, `width()`
- `sizeIn()`, `heightIn()`, `widthIn()`

Обратите внимание, что применяется только первый переданный модифаер размера.

### Внешний вид

[ModifierAlpha.kt](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/modifier/ModifierAlpha.kt)

`Modifier.alpha()` позволяет настроить прозрачность компонента

[ModifierBackground.kt](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/modifier/ModifierBackground.kt)

`Modifier.alpha()` позволяет нарисовать задний фон элемента.

[ModifierClip.kt](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/modifier/ModifierClip.kt)

`Modifier.clip()` позволяет настроить форму элемента.

[ModifierOrder.kt](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/modifier/ModifierOrder.kt)

Порядок применения Modifier-ов влияет на полученный результат. Например, если сначала применить `Modifier.background(Color.Red)`, а затем `Modifier.alpha(0.5f)`, то фон элемента все равно останется красным, без прозрачности.

### Доступность

[ModifierSemantics.kt](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/modifier/ModifierSemantics.kt)

`Modifier.semantics` позволяет добавить больше информации об элементе. В первую очередь это информация нужна для режима talk back – озвучивания содержимого экрана.

Из основного, мы можем устанавливать `contentDescription` (то, что будет прочитано, когда пользователь дойдет до элемента) и `role` (роль элемента также будет зачитана после content description).

## Рекомпозиция

Мы разобрали как отрисовать интерфейс. А как его изменить, если изменились какие-либо данные? Для этого в Compose есть `State<T>` и `MutableState<T>`. `(Mutable)State` – просто холдер для некоторых данных. 

Его особенность заключается в следующем: **он умеет предупреждать Compose** о том, что **данные, которые он хранил, изменились**, за счет чего в каждой Composable-функции, в которой мы читаем значение этого стейта, **вызов Composable функции произойдет заново**. 

Вызов Composable функции называется композицией, а повторный процесс вызова, соответственно, **рекомпозицией**.

Это можно сравнить с паттерном Observable-Observer – логика работы похожа, разве что в роли подписки на изменения выступает чтение `state.value`.

[Пример с каунтером](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/recomposition/Counter1.kt)

Для того, чтобы `MutableState` не пересоздался заново при рекомпозиции, он кешируется с помощью вызова функции `remember { ... }`.

### Пропускаемость

[Рассмотрим второй пример](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/recomposition/Counter2.kt)

`AppText` в данном случае – просто обертка над `Text`, которая будет логировать каждый вызов.

Если запустить этот пример и нажать на кнопку, то увидим, что рекомпозиция функции `AppText` **вызвалась только для текста с числом**.

На самом деле, когда мы собираем проект, в дело вступил **Compose compiler plugin** и сильно изменил наш код. В каждую функцию, помеченную аннотацией `@Composable` добавляется параметр `Composer`, а в начало функции был добавлен код, проверяющий, изменились ли параметры с прошлой композиции и позволяющий пропустить вызов функции.

Код для пропуска композиции генерируется не у всех Composable-функций.

**Пропускаемость (Skippable)** – свойство composable-функции, позволяющее пропускать композицию, если переданные параметры не изменились с прошлой композиции.

Composable-функция является **пропускаемой**, если выполняются следующие условия:

- Функция ничего не возвращает
- Все параметры функции стабильны.

**Стабильность** – свойство типа данных, обозначающее что тип является неизменяемым (Immutable).

Стабильность типа означает, что рантайм Compose может **безопасно читать и сравнивать** входные данные такого типа, чтобы, при необходимости, пропустить рекомпозицию. 

Суть введения понятия стабильности и пропускаемости логична: сравнивать нестабильные, то есть мутабельные, объекты нельзя, поскольку в composer-е будет храниться та же самая ссылка на объект и результат сравнения может быть ложным.

**Стабильными типами считаются**:

- Все примитивные типы и String;
- Функциональные типы (лямбды);
- Enum;
- Входит в список исключений;
- Классы, у которых все поля стабильного типа и объявлены как val, в том числе и sealed-классы. 

Стабильность полей класса проверяется рекурсивно, пока не найдётся тип, о стабильности которого уже однозначно известно.

Compose считает **нестабильными**:

- Классы, у которых хотя бы одно поле нестабильного типа или объявлено как var;
- Все классы из внешних модулей и библиотек, в которых нет компилятора Compose (`List`, `Set`, `Map` и прочие коллекции, `LocalDate`, `LocalTime`, `Flow`, ...).

В Compose также определены внешние типы, которые будут считаться стабильными: `Pair`, `Result`, `Comparator`, `ClosedRange`, коллекции из библиотеки `kotlinx.collections.immutable`, `dagger.Lazy` и другие.

Еще одно важное условие: для корректной работы Compos-а у класса должны быть корректно определены методы equals и hashCode. Это не влияет на свойство стабильности, но сравнение будет возвращать некорректный результат.

Иногда Compose compiler не может определить стабильность параметра, например, в случае с интерфейсами. В таком случае Compose может проверять стабильность типа данных в рантайме.

У дженериков проверка идёт сначала по структуре самого дженерика, а потом по указанному типу. 

Если структура дженерика нестабильна (есть поля нестабильного типа или поля с var), то он сразу считается нестабильным. 

Если мы сразу указываем тип дженерика, то Compose уже на этапе компиляции определит его как стабильный или нестабильный.


### Примеры стабильных и нестабильных типов

- [Types.kt](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/stability/Types.kt)

- [Types2.kt](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/stability/Types2.kt)

- [Types3.kt](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/stability/Types3.kt)

### Immutable, Stable

Мы можем принудительно пометить тип данных как стабильный с помощью аннотаций `@Immutable` и `@Stable`.

- `@Immutable` – тип данных неизменяемый.
- `@Stable` – тип данных изменяемый, но compose узнает об изменениях (содержит State/MutableState).

На самом деле типы данных с аннотациями Immutable и Stable обрабатываются компилятором одинаково. Тем не менее, лучше правильно проставлять аннотацию, поскольку в будущем возможно появление оптимизаций, завязанных на эти аннотации.

### Default value

[DefaultParameter.kt](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/stability/DefaultParameter.kt)

Функция будет считаться пропускаемой, даже если среди ее параметров есть нестабильные типы данных, но все они имеют значение по умолчанию. Лучше никогда не используйте это. Исключение – ViewModel.

### Перезапускаемость

**Перезапускаемость (restartable)** – свойство Composable-функции, позволяющее начинать рекомпозицию с этой функции. Это не тоже самое, что и пропускаемость (функция может быть перезапускаемой, но не пропускаемой).

Если функция **не перезапускаемая**, то она **не может быть пропускаемой**.

То есть, если функция перезапускаемая, то она может быть областью перезапуска, областью, в которой чтение изменившегося Compose стейта может привести к рекомпозиции.

**Функция является перезапускаемой**, если она ничего не возвращает, не является `inline` и явно не указано, что она не является перезапускаемой с помощью аннотации `@NonRestartableComposable` или `@ReadOnlyComposable`.

Если функция не является перезапускаемой, то рекомпозиция будет вызываться у первой перезапускаемой родительской функции.

К неперезапускаемым относятся функции `Box`, `Column` и `Row`. Если стейт читается внутри, например, `Column`, то рекомпозиция произойдет у родительской функции, как в примере с каунтером.

## Remember

Иногда рекомпозиция может происходить часто, например, при вводе текста в поле ввода или при анимации. Так как на каждую рекомпозицию функция вызывается заново, мы можем перерасчитывать какие-то тяжелые вычисления или пересоздавать объекты заново. Однако не всегда это необходимо.
С помощью remember мы можем сохранить объект в кеш и он переживет рекомпозицию.

Сохранение лямбд происходит автоматически, если внутри лямбды нет нестабильных параметров.

[Пример с remember](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/recomposition/Remember.kt)

[Пример с remember с key](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/recomposition/RememberWithKey.kt)

## Composition Local

**CompositionLocal** – еще одна ключевая концепция в Jetpack Compose, которая позволяет неявно делиться данными по всему дереву композиции. 

Это означает, что мы можем передавать данные в Composable-функции без явной передачи их параметром каждую встречающуюся на пути функцию.

Есть два типа Composition Local: **динамический** и **статический**. 

Разница между ними в том, что второй не будет вызывать рекомпозицию в месте чтения. (Да, рекомпозицию может вызывать чтение CompositionLocal, точно также, как и чтение State).

[Простой пример с CompositionLocal](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/compositionlocal/CompositionLocal.kt)

С помощью `CompositionLocalProvider` можно переопределить значение, возвращаемое через CompositionLocal.

За счет этой механики работает поддержка темной темы в, в том числе `MaterialDesign`.

В примере ниже `LocalColorScheme` содержит `data class` со всеми нужными в приложении цветами.

В composable-функции `AppTheme` мы получаем текущую тему устройства и, в зависимости от нее, передаем дальше по дереву композиции уже нужный нам набор цветов (`AppDarkColorScheme` в данном примере – набор цветов для темной темы, `AppLightColorScheme` – набор цветов для светлой темы).

```kotlin
@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val theme = if (isSystemInDarkTheme()) {
        AppDarkColorScheme
    } else {
        AppLightColorScheme
    }

    CompositionLocalProvider(
        LocalColorScheme provides theme
    ) {
        content()
    }
}

@Composable
fun App() {
    AppTheme {
        SomeScreen()
    }
}
```

[Полный пример](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/compositionlocal/ds)

В AppTheme есть два метода, помеченные аннотацией `@ReadOnlyComposable`. Эта аннотация обозначает, что функция ничего не рисует на экране, поэтому compose compiler не будет оборачивать ее содержимое в группу – небольшая оптимизация.

## Material

Мы рассмотрели, как сделать свою единую цветовую схему и типографию для всего приложения. Так вот, google сделали готовую дизайн-систему, которую вы можете использовать в своих приложениях: MaterialDesign

Она предоставляет: готовые дизайн-компоненты (такие как, например, кнопки, поля ввода, пикер даты и многое другое), цвета, типографию, риппл эффекты и многое другое.

[Figma – все компоненты Material Design](https://www.google.com/url?sa=t&source=web&rct=j&opi=89978449&url=https://www.figma.com/community/file/1035203688168086460/material-3-design-kit&ved=2ahUKEwjsi6-K0qSJAxXlKRAIHXk7GEYQFnoECBgQAQ&usg=AOvVaw0EKYdNOGtijZpMXU1cmKXG)

`object MaterialTheme` содержит цветовую схема, типографию и набор шейпов. Реализован практически также, как мы только что реализовывали свою ДС.

Компоненты MaterialDesign реализованы на **Slot Api**.

**Slot Api** – принцип построения api для Composable-функций, основанный на слотах. Функция описывает общие элементы экрана, а также предусматривает слоты под кастомные элементы. Слот представляет собой ComposableLambda.

Примеры: Scaffold, Button

[Пример экрана с MaterialDesign](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/material/MaterialTheme.kt)

## Side Effect

**Side effect** в UI – некоторые сценарии, при которых изменение состояния приложения должно произойти вне области действия Composable функции. 

Или, другими словами, цель side эффектов в Compose — обеспечить возможность выполнения операций, не связанных с пользовательским интерфейсом, которые изменяют состояние приложения вне Composable-функции контролируемым и предсказуемым образом.

Всего существует три вида сайд эффектов:

- SideEffect
- LaunchedEffect
- DisposableEffect

### SideEffect

[SideEffect.kt](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/side/SideEffect.kt)

Код, обернутый в SideEffect, будет выполняться после каждой композиции.

Может пригодится при изменении глобальных переменных/полей класса, если по какой-то причине ваша Composable-функция лежит в классе.

Скорее всего SideEffect никогда вам не понадобиться, если код организован правильно.

### LaunchedEffect


[LaunchedEffect.kt](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/side/LaunchedEffect.kt)

**LaunchedEffect** выполняет переданный эффект в отдельном coroutine scope.

Это полезно при вызове длительных операций, таких как анимации.

В `LaunchedEffect` обязательно нужно передавать `key`. Если во время рекомпозиции в тот же LaunchedEffect будет передан тот же key, переданный эффект не будет вызван.

### DisposableEffect

[DisposableEffect.kt](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/side/DisposableEffect.kt)

**DisposableEffect** выполняет переданный эффект с возможностью определить действия на событие уничтожения композиции.

Аналогично LaunchedEffect, в DisposableEffect необходимо передавать key.

## Compose внутри Android View и наоборот

Огромное количество приложений уже написаны на Android View и переписать их на Compose за разумное время просто не представляется возможным. Поэтому Compose можно интегрировать в Android View при помощи ComposeView.

Мы можем добавить новую ComposeView в наш XML-макет, и в коде прописать у нее setContent.

Для этого в xml следует добавить ComposeView:

[activity_main.xml](/lection%2004/lection_04/app/src/main/res/layout/activity_main.xml)

```xml
<androidx.compose.ui.platform.ComposeView
    android:id="@+id/compose_view"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>
```

И затем в коде вызвать `setContent`

[MainActivity.kt](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/MainActivity.kt)

Также мы можем добавить AndroidView в Compose.

[AndroidViewInCompose.kt](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/composeview/AndroidViewInCompose.kt)

## Навигация

### Старые приложения

Начнем с того, что мы можем использовать навигацию для android view, основанную на фрагментах. 

Это актуально **только для приложений, которые уже написаны на android view**.

В таких условиях каждый экран – фрагмент с единственной ComposeView, в которой уже рисуется контент.

### Простейшая реализация

В самом базовом виде навигация в Compose может выглядеть следующим образом:

```kotlin
@Composable
fun App() {
    var screen by remember { mutableStateOf("home") }

    when (screen) {
        "home" -> {
            HomeScreen(
                goToSettings = {
                    screen = "settings"
                },
            )
        }

        "settings" -> {
            SettingsScreen()
            BackHandler {
                screen = "home"
            }
        }
    }
}
```

Это простейшая реализация, не поддерживающая многое. Но все библиотеки в целом работают на подобном принципе.

[Полный пример](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/navigation/SimpleNavigation.kt)

### navigation-compose

Google предлагают использовать для навигации их библиотеку navigation-compose.

Для управления навигацией (переход на экран, переход назад и тому подобное) используется `NavController`, который можно создать с помощью вызова `rememberNavController()`.

Каждый экран должен иметь свой "роут", некоторый уникальный путь. Роуты можно хранить в виде строк, но можно завести `sealed class`-обертку, к примеру такую (в нашем примере будет 2 экрана: home и settings):

```kotlin
sealed class Route(val route: String) {
    data object Home : Route("home")
    data object Settings : Route("settings")
}
```

В самую верхнюю функцию необходимо поместить `NavHost` (ему необходимо передать navController и роут к стартовому экрану).

`NavHost` предоставляет нам Builder, в котором мы можем определить, какие экраны следует открывать по определенным роутам c помощью метода `composable`.

```kotlin
@Composable
fun NavigationComposeApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Home.route,
    ) {
        composable(Route.Home.route) {
            HomeScreen()
        }
        composable(Route.Settings.route) {
            SettingsScreen()
        }
    }
}
```

В NavHost можно настроить анимации при переходе с экрана на экран с помощью передачи параметров `enterTransition`, `exitTransition`, `popEnterTransition`, `popExitTransition`, `sizeTransform`. Подробнее о том, что туда можно передать, можно узнать в статье про анимации из дополнительных материалов.

Для того, чтобы перейти с одного экрана на другой, в Composable-функции экранов нужно передать navController. Передать его можно с помощью composition local:

```kotlin
val LocalNavController = staticCompositionLocalOf<NavController> {
    throw IllegalStateException("No nav controller found")
}

@Composable
fun NavigationComposeApp() {
    val navController = rememberNavController()

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(...)
    }
}
```

[Полный пример](/lection%2004/lection_04/app/src/main/java/com/example/compose_sample/navigation/navcompose/NavigationCompose.kt)

navigation-compose – большая тема для обсуждения. Тут есть и передача параметров между экранами, и вложенная навигация, и поддержка диплинков. Рекомендую ознакомится со статьей из дополнительных материалов.

## Strong skipping

**Strong skipping** (сильная пропускаемость) – экспериментальный режим, в котором **все Composable-функции**, которые ничего не возвращают, **обладают свойством пропускаемости** вне зависимости от типа принимаемых параметров, а также **все лямбды запоминаются**, вне зависимости от использования в них нестабильных типов.

Режим включается, если в проекте используем kotlin 2. По умолчанию он выключен.

Изначально Compose делит типы на стабильные и нестабильные из-за того, что нестабильные типы нельзя корректно сравнить между собой через equals. Как эта проблема решена в strong skipping mode? На самом деле эта проблема была решена только частично. Нестабильные типы будут сравниваться по ссылке, а не по значению.

Это означает, что типы данных все равно должны быть неизменяемыми.

## Проблемы Compose

Compose – очень мощный инструмент, но, конечно же, он имеет и свои недостатки.

- **Производительность**. Она действительно хуже, чем у экранов, написанных на Android View. У этого есть следующие причины:
    - Декларативность. Из этого вытекает тяжелый этап композиции, когда под капотом нужно просчитать, какие элементы изменились, а какие – нет. Конечно же, в Android View в этом плане все проще.
    - Compose, в отличие от Android View – подключаемая библиотека. Это означает, что она будет компилироваться из байт-кода в режиме JIT (just in time).
    - Compose позволяет писать непроизводительный код. Случайно написать нестабильный класс очень просто, но Compose никак об этом нас не предупредит, хоть и собирает эту статистику.

- **Баги**. Compose, относительно Android View, молодой инструмент, и поэтому не обходится без проблем. Баги с полями ввода в модальных окнах, проблемы с двумя стейтами. 

- **Нестабильное API** (Experimental). В Compose очень много функций, помеченных аннотацией ExperimentalApi, что означает, что входные параметры или логика работы функции может изменится. И такое действительно нередко происходит, как, например, в Compose 1.6 google отказались от Swipeable в пользу AnchoredDraggable. Также эта проблема сильно касается compose multiplatform. Стабильный релиз вышел не для всех платформ (например, его еще нет для Tv), поэтому там api меняется постоянно, и при каждом обновлении до новой версии что-то легко может сломаться.

Несмотря на свои проблемы, Compose – очень мощный инструмент, который помогает разработчикам быстрее писать код и упрощает поддержку этого кода. Компании активно переходят на Compose в своих существующих приложениях, а также пишут новые уже с использованием этой технологии.