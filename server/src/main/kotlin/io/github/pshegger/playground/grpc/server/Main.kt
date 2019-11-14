package io.github.pshegger.playground.grpc.server

object Main {
    private const val PORT = 8980

    @JvmStatic
    fun main(args: Array<String>) {
        val server = ChatServer(PORT)
        server.start()
        server.blockUntilShutdown()
    }
}
