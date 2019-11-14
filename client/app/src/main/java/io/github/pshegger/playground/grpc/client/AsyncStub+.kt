package io.github.pshegger.playground.grpc.client

import io.github.pshegger.playground.grpc.client.dsl.StreamObserverDsl
import io.github.pshegger.playground.grpc.client.dsl.streamObserver
import io.github.pshegger.playground.grpc.proto.ChatMessage
import io.github.pshegger.playground.grpc.proto.GrpcChatGrpc
import io.grpc.stub.StreamObserver

fun GrpcChatGrpc.GrpcChatStub.chat(delegate: StreamObserverDsl<ChatMessage>.() -> Unit): StreamObserver<ChatMessage> =
    chat(streamObserver(delegate))
