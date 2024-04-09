import org.gradle.api.Plugin
import org.gradle.api.Project

class SimpleCompositePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        println("Our composite plugin has changed, configurating : ${target.name}")
    }
}

//Если что-либо тут поменять, то произойдет сброс кеша и все будет собираться заново
//Это минус плагинов в buildSrc