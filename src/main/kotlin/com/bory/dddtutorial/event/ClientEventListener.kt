package com.bory.dddtutorial.event

import brave.SpanCustomizer
import com.bory.dddtutorial.coreapi.ClientCreated
import com.bory.dddtutorial.coreapi.ClientUpdated
import com.bory.dddtutorial.coreapi.ProjectAdded
import com.bory.dddtutorial.coreapi.ProjectUpdated
import io.opentracing.Tracer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.sleuth.annotation.NewSpan
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ClientEventListener(
    private val tracer: Tracer,
    private val spanCustomizer: SpanCustomizer
) {
    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(ClientEventListener::class.java)
    }

    @NewSpan
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun on(event: ProjectAdded) {
        LOGGER.debug("After Project Added ::: $event on ${Thread.currentThread().name}")
    }

    @NewSpan
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun on(event: ProjectUpdated) {
        LOGGER.debug("After Project Updated ::: $event on ${Thread.currentThread().name}")
    }

    @NewSpan
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun on(event: ClientCreated) {
        LOGGER.debug("After Client Created ::: $event on ${Thread.currentThread().name}")
    }

    @NewSpan
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun on(event: ClientUpdated) {
        tracer.activeSpan().setTag("Event-Class", event.javaClass.name)
        spanCustomizer.tag("Event-UUID", event.uuid.toString())
        spanCustomizer.tag("Event-Correlation-UUID", event.correlationId?.toString() ?: "")

        LOGGER.debug("After Client Updated ::: $event on ${Thread.currentThread().name}")
    }

}