plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("composite.plugin")
}

//apply<SimplePlugin>()

apply<SimpleCompositePlugin>()

android {
    namespace = "ru.vk.gradle_lesson"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.vk.gradle_lesson"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

//Простая таска, register предпочтительней, чем create, так создает таску лениво
tasks.register("simpleTask") {
    group = "build"
    println("Hello world")
    doLast { println("do Last") }
    doFirst { println("do First") }
}

tasks.register<Delete>("deleteBuild") {
    group = "simple tasks example"
    delete(layout.buildDirectory)
}

//Можно писать код таски здесь, но не рекомендуция. В качестве примера
abstract class HelloTask : DefaultTask() {
    @get:Input
    abstract val msg: Property<String>

    @TaskAction
    fun action() {
        println(msg.get())
    }
}

//Регистрируем HelloTask

tasks.register<HelloTask>("helloTask") {
    group = "simple tasks example"
    msg.set("Hello World1!")
}

// # 2 Примеры зависимостей

tasks.register<HelloTask>("helloTask1") {
    //Таска два запустится перед 1
    //dependsOn("helloTask2")


    //shouldRunAfter и mustRunAfter не принуждают таску к запуску
    //shouldRunAfter("helloTask2")
    mustRunAfter("helloTask2")
    group = "depends tasks example"
    msg.set("Executed task 1")
}

//./gradlew helloTask1 helloTask2

//Регистрируем HelloTask
tasks.register<HelloTask>("helloTask2") {
    group = "depends tasks example"
    //shouldRunAfter("helloTask1")
    msg.set("Executed task 2")
}


tasks.register<Copy>("copyApk") {
    group = "depends tasks example"

    dependsOn("assemble")
    from(layout.buildDirectory.file("outputs/apk/debug"))
    include("app-debug.apk")
    rename("app-debug.apk", "new-release.apk")
    into("artifacts/")
}

// # 3 Incremental/Cacheable task

abstract class IncrementalChangesTask : DefaultTask() {

    @get:Incremental
    @get:PathSensitive(PathSensitivity.NAME_ONLY)
    @get:InputDirectory
    abstract val inputDir: DirectoryProperty

    @TaskAction
    fun action(inputChanges: InputChanges) {
        if (inputChanges.isIncremental) {
            println("Incremental start")
        } else {
            println("Cold start")
        }

        inputChanges.getFileChanges(inputDir).forEach {
            if (it.fileType == FileType.FILE) {
                println("We know that ${it.normalizedPath} ${it.changeType}")
            }
        }
    }
}


tasks.register<IncrementalChangesTask>("incrementalTask") {
    group = "Incremental/Cacheable task"
    outputs.upToDateWhen { true }
    inputDir.set(file("artifacts/"))
}

@CacheableTask
abstract class CacheableChangesTask : DefaultTask() {

    @get:Input
    abstract val msg: Property<String>

    @get:OutputFile
    abstract val output: RegularFileProperty

    @TaskAction
    fun action() {
        println(msg.get())
        output.get().asFile.writeText(msg.get())
    }

}

tasks.register<CacheableChangesTask>("cacheTask") {
    group = "Incremental/Cacheable task"
    msg.set("5")
    outputs.cacheIf { true }
    outputs.upToDateWhen { true }
    output.set(file("artifacts/file4"))
}