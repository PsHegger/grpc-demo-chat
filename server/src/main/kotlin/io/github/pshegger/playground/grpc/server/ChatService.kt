package io.github.pshegger.playground.grpc.server

import io.github.pshegger.playground.grpc.server.dsl.streamObserver
import io.github.pshegger.playground.grpc.proto.ChatMessage
import io.github.pshegger.playground.grpc.proto.GrpcChatGrpc
import io.grpc.stub.StreamObserver
import java.util.*

class ChatService : GrpcChatGrpc.GrpcChatImplBase() {

    private val clients = Collections.synchronizedMap(mutableMapOf<String, StreamObserver<ChatMessage>>())

    override fun chat(responseObserver: StreamObserver<ChatMessage>): StreamObserver<ChatMessage> {
        val id = UUID.randomUUID().toString()
        clients[id] = responseObserver

        println("Client connected with id $id")

        return streamObserver {

            onNext { message ->
                println("Received message from $id, sending to ${clients.size} clients")
                clients.values.forEach { it.onNext(message) }
            }

            onError { clients.remove(id) }
            onCompleted {
                println("Client $id disconnected")
                clients.remove(id)
            }
        }
    }
}
