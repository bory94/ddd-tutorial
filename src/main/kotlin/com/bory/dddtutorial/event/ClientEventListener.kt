package com.bory.dddtutorial.event

import com.bory.dddtutorial.coreapi.ClientCreated
import com.bory.dddtutorial.coreapi.ClientUpdated
import com.bory.dddtutorial.coreapi.ProjectAdded
import com.bory.dddtutorial.coreapi.ProjectUpdated
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ClientEventListener {
    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(ClientEventListener::class.java)
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun on(event: ProjectAdded) {
        LOGGER.debug("After Project Added ::: $event")
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun on(event: ProjectUpdated) {
        LOGGER.debug("After Project Updated ::: $event")
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun on(event: ClientCreated) {
        LOGGER.debug("After Client Created ::: $event")
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun on(event: ClientUpdated) {
        LOGGER.debug("After Client Updated ::: $event")
    }
}