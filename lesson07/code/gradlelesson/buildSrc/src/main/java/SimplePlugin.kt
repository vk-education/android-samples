import org.gradle.api.Plugin
import org.gradle.api.Project

class SimplePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        println("Our simple plugin, configurating  : ${target.name}")
    }
}

//Если что-либо тут поменять, то произойдет сброс кеша и все будет собираться заново
//Это минус плагинов в buildSrc