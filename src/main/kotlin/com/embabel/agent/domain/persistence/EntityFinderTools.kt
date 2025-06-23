package com.embabel.agent.domain.persistence

import com.embabel.agent.api.annotation.waitFor
import com.embabel.agent.api.common.PromptRunner
import com.embabel.agent.api.common.createObjectIfPossible
import com.embabel.agent.core.hitl.ConfirmationRequest
import org.springframework.data.repository.CrudRepository

/**
 * Superclass for tool support to find entities in a repository
 * Subclasses should extend this to add @Tool annotations.
 * We require this rather than trying a generic solution because
 * the exact combination of finders is use case specific.
 */
abstract class EntityFinderTools<T : Any>(
    protected val repository: CrudRepository<T, String>,
    private val entityType: Class<T>,
) {

    fun findEntity(
        promptRunner: PromptRunner, content: String,
        confirmOne: (match: T) -> ConfirmationRequest<T>? = { null }
    ): T? {
        val entityId = promptRunner
            .withToolObject(this).createObjectIfPossible<EntityId>(
                """
            Find a ${entityType.simpleName} based on the user input: "$content"
            Use the tools. Consider possible variations of case.
            """.trimIndent()
            )
        if (entityId == null) {
            return null
        }
        val entity = repository.findById(entityId.id).orElse(null)
        if (entity == null) {
            return null
        }
        val cf = confirmOne(entity)
        return if (cf != null) return waitFor(cf) else entity
    }
}

/**
 * Returned by an LLM to indicate that it has found an entity.
 */
internal data class EntityId(
    val id: String,
    val reason: String,
)