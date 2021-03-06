package de.otto.find.gradle.projectversion

import org.gradle.api.Plugin
import org.gradle.api.Project

class ProjectVersionPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.extensions
                .create('projectVersion', ProjectVersionPluginExtension, project)

        project.tasks.create('tagVersion', ProjectVersionTag) {
            group = 'Publishing'
            description = 'Tags the current commit with the current project version'

            gitCommit = project.projectVersion.gitCommit
        }

        project.tasks.create('buildInfo', ProjectBuildInfo) {
            group = 'Help'
            description = 'Displays project version information'

            buildInfo = project.projectVersion.buildInfo
        }
    }
}
