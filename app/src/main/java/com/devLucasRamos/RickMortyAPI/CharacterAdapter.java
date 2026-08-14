package com.devLucasRamos.RickMortyAPI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    private List<Character> characters;

    public CharacterAdapter(List<Character> characters) {
        this.characters = characters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_character, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Character character = characters.get(position);

        holder.tvName.setText(character.getName());
        holder.tvStatus.setText("Status: " + character.getStatus());
        holder.tvSpecies.setText("Especie: " + character.getSpecies());

        Glide.with(holder.itemView.getContext()).load(character.getImage()).into(holder.ivCharacter);
    }

    @Override
    public int getItemCount() {
        if (characters == null) {
            return 0;
        }
        return characters.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCharacter;
        TextView tvName, tvStatus, tvSpecies;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCharacter = itemView.findViewById(R.id.ivCharacter);
            tvName = itemView.findViewById(R.id.tvName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvSpecies = itemView.findViewById(R.id.tvSpecies);
        }
    }
}
