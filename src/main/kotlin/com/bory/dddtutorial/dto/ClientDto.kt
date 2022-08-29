package com.bory.dddtutorial.dto

import org.hibernate.validator.constraints.Length
import org.springframework.boot.context.properties.ConstructorBinding
import javax.validation.Valid
import javax.validation.constraints.NotEmpty

data class ClientDto @ConstructorBinding constructor(
    val id: Long? = null,
    @field:NotEmpty @field:Length(min = 3, max = 100)
    val name: String,
    @field:Valid val projects: Set<ProjectDto> = mutableSetOf()
)

data class ProjectDto @ConstructorBinding constructor(
    val id: Long? = null,
    @field:NotEmpty @field:Length(min = 3, max = 100)
    val name: String
)