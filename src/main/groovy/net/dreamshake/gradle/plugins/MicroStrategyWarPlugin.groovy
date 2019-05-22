package net.dreamshake.gradle.plugins

import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.bundling.War

class MicroStrategyWarPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.pluginManager.apply("war")
        project.configurations.create("mstrPlugins")
        project.configurations.create("sourceWar")
        def mstrWar = project.extensions.create("mstrWar", MicroStrategyWarExtension)
        project.tasks.withType(War.class) { War warTask -> 
            warTask.from {
                project.configurations.sourceWar.collect {
                    project.zipTree(it)
                }
            }

            warTask.into('plugins/', {
                it.from({
                    project.configurations.mstrPlugins.collect { 
                    project.zipTree(it)
                }})
            })

            warTask.archiveFileName.set(project.mstrWar.outputFileName)
            warTask.duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
        Task aliasTask = project.tasks.create("mstrWar")
        aliasTask.dependsOn(War.TASK_NAME)
    }
}
