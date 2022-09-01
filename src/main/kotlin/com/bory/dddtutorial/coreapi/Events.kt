package com.bory.dddtutorial.coreapi

import java.time.Instant
import java.util.*

abstract class AbstractEvent(
    open val uuid: UUID = UUID.randomUUID(),
    open val createdAt: Instant = Instant.now(),
    open val correlationId: UUID? = null,
    open val payload: Any? = null
) {
    override fun toString(): String {
        return """
            ClientCreated(
                uuid=$uuid, 
                createdAt=$createdAt, 
                correlationId=$correlationId, 
                payload=$payload
            )
            """.trimIndent()
    }
}

data class ClientCreated(
    override val payload: Client
) : AbstractEvent() {
    override fun toString(): String {
        return super.toString()
    }
}

data class ClientUpdated(
    override val payload: Client
) : AbstractEvent() {
    override fun toString(): String {
        return super.toString()
    }
}

data class ProjectAdded(
    override val payload: Client
) : AbstractEvent() {
    override fun toString(): String {
        return super.toString()
    }
}

data class ProjectUpdated(
    override val payload: Client
) : AbstractEvent() {
    override fun toString(): String {
        return super.toString()
    }
}