package io.github.pshegger.playground.grpc.client

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.pshegger.playground.grpc.proto.ChatMessage
import kotlinx.android.synthetic.main.chat_message.view.*
import java.util.*

class ChatMessageAdapter(private val items: List<ChatMessage>) : RecyclerView.Adapter<ChatMessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_message, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        fun bind(message: ChatMessage) {
            val time = Calendar.getInstance().apply { timeInMillis = message.timestamp }

            itemView.name.text = message.sender.name
            itemView.message.text = message.text
            itemView.timestamp.text = "(${time.get(Calendar.HOUR_OF_DAY)}:${String.format("%02d", time.get(
                Calendar.MINUTE))})"
        }
    }
}

