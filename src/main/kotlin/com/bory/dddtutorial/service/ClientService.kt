package com.bory.dddtutorial.service

import com.bory.dddtutorial.coreapi.Client
import com.bory.dddtutorial.coreapi.Project
import com.bory.dddtutorial.dto.ClientDto
import com.bory.dddtutorial.dto.ProjectDto
import com.bory.dddtutorial.repository.ClientRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ClientService(
    private val clientRepository: ClientRepository
) {
    fun findAll(): List<ClientDto> =
        clientRepository.findAll().map { it.toDto() }

    fun create(clientDto: ClientDto): ClientDto {
        val client = Client(clientDto)
        return clientRepository.save(client).toDto()
    }

    fun findById(id: Long): ClientDto =
        clientRepository.findById(id)
            .orElseThrow { throw IllegalArgumentException("client id[$id] not found") }
            .toDto()

    fun update(id: Long, clientDto: ClientDto): ClientDto {
        val foundClient = clientRepository.findById(id)
            .orElseThrow { throw IllegalArgumentException("client id[$id] not found") }

        foundClient.updateClient(clientDto)
        return clientRepository.save(foundClient).toDto()
    }

    fun addProject(id: Long, projectDto: ProjectDto): ClientDto {
        val foundClient = clientRepository.findById(id)
            .orElseThrow { throw IllegalArgumentException("client id[$id] not found") }

        foundClient.addProject(Project(projectDto.id, projectDto.name))

        return clientRepository.save(foundClient).toDto()
    }

    fun updateProject(cid: Long, pid: Long, projectDto: ProjectDto): ClientDto {
        val foundClient = clientRepository.findById(cid)
            .orElseThrow { throw IllegalArgumentException("client id[$cid] not found") }

        foundClient.updateProject(pid, Project(projectDto.id, projectDto.name))

        return clientRepository.save(foundClient).toDto()
    }
}