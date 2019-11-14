package io.github.pshegger.playground.grpc.server

import io.grpc.ServerBuilder

class ChatServer(serverBuilder: ServerBuilder<*>, private val port: Int) {
    constructor(port: Int) : this(ServerBuilder.forPort(port), port)

    private val server = serverBuilder
        .addService(ChatService())
        .build()

    fun start() {
        server.start()
        println("Server started on port $port")
        Runtime.getRuntime().addShutdownHook(Thread {
            stop()
        })
    }

    private fun stop() = server?.shutdown()

    fun blockUntilShutdown() = server?.awaitTermination()
}
