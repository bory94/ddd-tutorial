package com.bory.dddtutorial.repository

import com.bory.dddtutorial.coreapi.Client
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : CrudRepository<Client, Long>