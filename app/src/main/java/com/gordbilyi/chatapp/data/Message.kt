package com.gordbilyi.chatapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Immutable model class for a Message.
 *
 * @param contactId id of the contact to which the message belongs to
 * @param payload payload of the message
 * @param isLocal whether or not this message was sent or received
 * @param id id of the message
 */
@Entity(tableName = "messages")
data class Message @JvmOverloads constructor(
    @ColumnInfo(name = "contact_id") var contactId: Int,
    @ColumnInfo(name = "payload") var payload: String,
    @ColumnInfo(name = "is_local") var isLocal: Boolean = false,
    @ColumnInfo(name = "created_at") var createdAt: Date = Date(),
    @PrimaryKey @ColumnInfo(name = "entry_id") var id: String = UUID.randomUUID().toString()
)
