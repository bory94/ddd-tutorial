package com.bory.dddtutorial.domain

import com.bory.dddtutorial.dto.ClientDto
import com.bory.dddtutorial.dto.ProjectDto
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("client")
data class Client(
    @Id private var id: Long?,
    private var name: String,
    private var projects: Set<Project> = mutableSetOf()
) {
    companion object {
        fun from(clientDto: ClientDto) = with(clientDto) {
            if (projects.size == 4) throw IllegalArgumentException("Projects size should not be more than 4")

            Client(id, name, projects.map {
                Project(it.id, it.name)
            }.toSet())
        }
    }

    fun toDto() = ClientDto(
        id, name, projects.map {
            ProjectDto(it.id, it.name)
        }.toSet()
    )

    fun addProject(project: Project) {
        if (projects.size == 4) throw IllegalArgumentException("Projects size should not be more than 4")

        projects += project
    }

    fun updateProject(pid: Long, project: Project) {
        val existsProject = projects.firstOrNull { it.id == pid }
            ?: throw IllegalArgumentException("No Such Project id[$pid]")

        existsProject.name = project.name
    }
}

@Table("project")
data class Project(
    @Id var id: Long?,
    var name: String
)