package com.bory.dddtutorial.coreapi

data class ClientCreated(val client: Client)
data class ClientUpdated(val client: Client)
data class ProjectAdded(val client: Client)
data class ProjectUpdated(val client: Client)