package io.github.pshegger.playground.grpc.client.dsl

import io.github.pshegger.playground.grpc.proto.ChatMessage
import io.github.pshegger.playground.grpc.proto.User
import io.grpc.stub.StreamObserver

fun chatMessage(delegate: ChatMessageDsl.() -> Unit): ChatMessage = ChatMessageDsl().apply(delegate).build()
fun ChatMessage.copy(delegate: ChatMessageDsl.() -> Unit): ChatMessage =
    ChatMessageDsl(toBuilder()).apply(delegate).build()

fun StreamObserver<ChatMessage>.send(delegate: ChatMessageDsl.() -> Unit) =
    onNext(chatMessage(delegate))

class ChatMessageDsl(private val builder: ChatMessage.Builder = ChatMessage.newBuilder()) {

    var sender: User
        get() = builder.sender
        set(value) {
            builder.sender = value
        }

    var text: String
        get() = builder.text
        set(value) {
            builder.text = value
        }

    var timestamp: Long
        get() = builder.timestamp
        set(value) {
            builder.timestamp = value
        }

    fun sender(delegate: UserDsl.() -> Unit) {
        sender = user(delegate)
    }

    fun currentTimestamp() {
        timestamp = System.currentTimeMillis()
    }

    fun build(): ChatMessage = builder.build()
}
