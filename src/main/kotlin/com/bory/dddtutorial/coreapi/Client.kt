package com.bory.dddtutorial.coreapi

import com.bory.dddtutorial.dto.ClientDto
import com.bory.dddtutorial.dto.ProjectDto
import org.springframework.data.annotation.Id
import org.springframework.data.domain.AbstractAggregateRoot
import org.springframework.data.relational.core.mapping.Table

@Table("client")
data class Client(
    @Id private var id: Long?,
    private var name: String,
    private var projects: Set<Project> = mutableSetOf()
) : AbstractAggregateRoot<Client>() {
    companion object {
        private const val MAXIMUM_PROJECTS_SIZE = 4
    }

    constructor(dto: ClientDto) : this(
        dto.id,
        dto.name,
        dto.projects.map(Project::fromDto).toSet()
    ) {
        checkProjectsAddable()
        registerEvent(ClientCreated(this))
    }

    fun toDto() = ClientDto(
        id, name, projects.map {
            ProjectDto(it.id, it.name)
        }.toSet()
    )

    fun updateClient(dto: ClientDto) {
        this.name = dto.name

        registerEvent(ClientUpdated(this))
    }

    private fun checkProjectsAddable(newProjects: Set<Project> = setOf()) =
        if (projects.size + newProjects.size > MAXIMUM_PROJECTS_SIZE)
            throw throw IllegalArgumentException("Projects size should not be more than 4")
        else Unit

    fun addProject(project: Project) {
        checkProjectsAddable(setOf(project))

        projects += project

        registerEvent(ProjectAdded(this))
    }

    fun updateProject(pid: Long, project: Project) {
        val existsProject = projects.firstOrNull { it.id == pid }
            ?: throw IllegalArgumentException("No Such Project id[$pid]")

        existsProject.name = project.name

        registerEvent(ProjectUpdated(this))
    }
}

@Table("project")
data class Project(
    @Id var id: Long?,
    var name: String
) {
    companion object {
        fun fromDto(dto: ProjectDto) = with(dto) {
            Project(id, name)
        }
    }
}