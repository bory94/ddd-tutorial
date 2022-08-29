package com.bory.dddtutorial.controller

import com.bory.dddtutorial.dto.ClientDto
import com.bory.dddtutorial.dto.ProjectDto
import com.bory.dddtutorial.service.ClientService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/clients")
class ClientController(
    private val clientService: ClientService
) {
    @GetMapping
    fun findAllClients() = clientService.findAll()

    @PostMapping
    fun createNewClient(@Valid @RequestBody clientDto: ClientDto) =
        ResponseEntity.created(
            URI("/clients/${clientService.create(clientDto).id.toString()}")
        ).build<URI>()

    @GetMapping("/{id}")
    fun getClient(@PathVariable(name = "id") id: Long) =
        clientService.findById(id)

    @PutMapping("/{id}")
    fun updateClient(
        @PathVariable(name = "id") id: Long,
        @Valid @RequestBody clientDto: ClientDto
    ) =
        clientService.update(id, clientDto)

    @PostMapping("/{id}/projects")
    fun addProject(
        @PathVariable(name = "id") id: Long,
        @Valid @RequestBody projectDto: ProjectDto
    ) = ResponseEntity.created(
        URI("/clients/$id/projects/${clientService.addProject(id, projectDto).id}")
    ).build<URI>()

    @PutMapping("/{cid}/projects/{pid}")
    fun updateProject(
        @PathVariable(name = "cid") cid: Long,
        @PathVariable(name = "pid") pid: Long,
        @Valid @RequestBody projectDto: ProjectDto
    ) =
        clientService.updateProject(cid, pid, projectDto)

}