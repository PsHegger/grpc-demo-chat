package io.github.pshegger.playground.grpc.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.pshegger.playground.grpc.client.dsl.send
import io.github.pshegger.playground.grpc.proto.ChatMessage
import io.github.pshegger.playground.grpc.proto.GrpcChatGrpc
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var channel: ManagedChannel
    private lateinit var outChannel: StreamObserver<ChatMessage>

    private lateinit var userName: String

    private val messagesList = mutableListOf<ChatMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messageList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true)

        val rng = Random.Default
        val colors = resources.getStringArray(R.array.colors)
        val animals = resources.getStringArray(R.array.animals)
        userName = "${colors.random(rng)}${animals.random(rng)}${String.format("%02d", rng.nextInt(1, 100))}"
        userNameHint.text = userName

        send.setOnClickListener {
            sendMessage(messageInput.text.toString())
            messageInput.setText("")
        }
    }

    override fun onResume() {
        super.onResume()

        channel = ManagedChannelBuilder.forAddress(BuildConfig.HOST, BuildConfig.PORT).usePlaintext().build()
        val chatStub = GrpcChatGrpc.newStub(channel)
        outChannel = chatStub.chat {
            onNext { message ->
                handleNewMessage(message)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        outChannel.onCompleted()
        channel.shutdownNow()
    }

    private fun sendMessage(messageText: String) {
        outChannel.send {
            sender {
                name = userName
            }
            currentTimestamp()
            text = messageText
        }
    }

    private fun handleNewMessage(message: ChatMessage) {
        messagesList.add(0, message)
        runOnUiThread {
            messageList.adapter = ChatMessageAdapter(messagesList)
        }
    }
}
