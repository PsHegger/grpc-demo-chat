package io.github.pshegger.playground.grpc.client.dsl

import io.grpc.stub.StreamObserver

fun <T> streamObserver(delegate: StreamObserverDsl<T>.() -> Unit) = StreamObserverDsl<T>().apply(delegate)

class StreamObserverDsl<T> : StreamObserver<T> {
    private var _onNext: (T) -> Unit = {}
    private var _onError: (Throwable?) -> Unit = {}
    private var _onCompleted: () -> Unit = {}

    fun onNext(delegate: (T) -> Unit) {
        _onNext = delegate
    }

    fun onError(delegate: (Throwable?) -> Unit) {
        _onError = delegate
    }

    fun onCompleted(delegate: () -> Unit) {
        _onCompleted = delegate
    }

    override fun onNext(data: T) {
        _onNext(data)
    }

    override fun onError(t: Throwable?) {
        _onError(t)
    }

    override fun onCompleted() {
        _onCompleted()
    }

}
