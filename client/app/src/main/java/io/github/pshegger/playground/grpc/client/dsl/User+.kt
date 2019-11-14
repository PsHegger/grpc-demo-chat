package io.github.pshegger.playground.grpc.client.dsl

import io.github.pshegger.playground.grpc.proto.User

fun user(delegate: UserDsl.() -> Unit): User = UserDsl().apply(delegate).build()
fun User.copy(delegate: UserDsl.() -> Unit): User = UserDsl(
    toBuilder()
).apply(delegate).build()

class UserDsl(private val builder: User.Builder = User.newBuilder()) {

    var name: String
        get() = builder.name
        set(value) {
            builder.name = value
        }

    fun build(): User = builder.build()
}
