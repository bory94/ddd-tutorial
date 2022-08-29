package com.bory.dddtutorial.service

import com.bory.dddtutorial.domain.Client
import com.bory.dddtutorial.domain.Project
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
    fun findAll(): Iterable<Client> = clientRepository.findAll()

    fun create(clientDto: ClientDto): ClientDto {
        val client = Client.from(clientDto)
        return clientRepository.save(client).toDto()
    }

    fun findById(id: Long): ClientDto =
        clientRepository.findById(id)
            .orElseThrow { throw IllegalArgumentException("client id[$id] not found") }
            .toDto()

    fun update(id: Long, clientDto: ClientDto): ClientDto {
        val foundClient = clientRepository.findById(id)
            .orElseThrow { throw IllegalArgumentException("client id[$id] not found") }

        val updatingClient = foundClient.copy(name = clientDto.name)
        return clientRepository.save(updatingClient).toDto()
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