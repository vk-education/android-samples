plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        create("compositePlugin") {
            id = "composite.plugin"
            implementationClass = "SimpleCompositePlugin"
        }
    }
}