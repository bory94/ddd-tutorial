package com.bory.dddtutorial.service

import com.bory.dddtutorial.coreapi.Client
import com.bory.dddtutorial.coreapi.Project
import com.bory.dddtutorial.dto.ClientDto
import com.bory.dddtutorial.dto.ProjectDto
import com.bory.dddtutorial.repository.ClientRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ClientService(
    private val clientRepository: ClientRepository
) {
    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(ClientService::class.java)
    }

    fun findAll(): List<ClientDto> =
        clientRepository.findAll().map { it.toDto() }

    fun create(clientDto: ClientDto): ClientDto {
        return clientRepository.save(Client(clientDto)).toDto()
    }

    fun findById(id: Long): ClientDto =
        clientRepository.findById(id)
            .orElseThrow { throw IllegalArgumentException("client id[$id] not found") }
            .toDto()

    fun update(id: Long, clientDto: ClientDto): ClientDto {
        LOGGER.debug("Client Updating on [${Thread.currentThread().name}]")
        val foundClient = clientRepository.findById(id)
            .orElseThrow { throw IllegalArgumentException("client id[$id] not found") }


        return clientRepository.save(
            foundClient.updateClient(clientDto)
        ).toDto()
    }

    fun addProject(id: Long, projectDto: ProjectDto): ClientDto {
        val foundClient = clientRepository.findById(id)
            .orElseThrow { throw IllegalArgumentException("client id[$id] not found") }

        return clientRepository.save(
            foundClient.addProject(Project(projectDto.id, projectDto.name))
        ).toDto()
    }

    fun updateProject(cid: Long, pid: Long, projectDto: ProjectDto): ClientDto {
        val foundClient = clientRepository.findById(cid)
            .orElseThrow { throw IllegalArgumentException("client id[$cid] not found") }

        return clientRepository.save(
            foundClient.updateProject(pid, Project(projectDto.id, projectDto.name))
        ).toDto()
    }
}