package wit.org.guidancesystem.activities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_user.view.*
import wit.org.guidancesystem.R

interface UserListener {
    fun onUserClick(userEmail: String)
}

class UserAdapter constructor(private var users:List<String>,private val listener:UserListener): RecyclerView.Adapter<UserAdapter.MainHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_user,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val user = users[holder.adapterPosition]
        holder.bind(user, listener)
    }

    override fun getItemCount(): Int = users.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: String, listener: UserListener) {
            itemView.userEmail.text = user
            itemView.setOnClickListener { listener.onUserClick(user) }
        }
    }
}