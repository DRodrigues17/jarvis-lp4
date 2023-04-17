package br.org.fundatec.jarvis.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.org.fundatec.jarvis.R
import br.org.fundatec.jarvis.data.Character
import com.bumptech.glide.Glide

class CharacterListAdapter() :
    RecyclerView.Adapter<CharacterListAdapter.ViewHolder>() {

    private val characters: MutableList<Character> = mutableListOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image)
        val name: TextView = view.findViewById(R.id.name)
        val description: TextView = view.findViewById(R.id.description)
        val age: TextView = view.findViewById(R.id.age)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_character, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val character = characters[position]

        Glide.with(holder.image.context)
            .load(character.image)
            .into(holder.image)

        holder.name.text = character.name
        holder.description.text = character.description
        holder.age.text = character.age.toString()
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    fun adicionarItensLista(listaPersonagens: List<Character>){
        characters.addAll(listaPersonagens)
        notifyDataSetChanged()
    }
}
