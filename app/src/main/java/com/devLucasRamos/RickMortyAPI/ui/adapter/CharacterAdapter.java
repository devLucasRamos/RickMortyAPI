package com.devLucasRamos.RickMortyAPI.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.devLucasRamos.RickMortyAPI.data.model.Character;
import com.devLucasRamos.RickMortyAPI.R;

import java.util.List;
import android.content.Context;

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
        Context context = holder.itemView.getContext();

        holder.tvName.setText(character.getName());
        String status = character.getStatus();
        holder.tvStatus.setText(status);

        int colorStatus;
        if ("Alive".equalsIgnoreCase(character.getStatus())) {
            colorStatus = R.color.status_alive;
        }

        else if ("Dead".equalsIgnoreCase(character.getStatus())) {
            colorStatus = R.color.status_dead;
        }

        else {
            colorStatus = R.color.status_unknown;
        }

        int colorHolder = androidx.core.content.ContextCompat.getColor(context, colorStatus);

        androidx.core.view.ViewCompat.setBackgroundTintList(
                holder.tvStatus,
                android.content.res.ColorStateList.valueOf(colorHolder)
        );

        String speciesText = "Espécie: " + character.getSpecies();
        if (!character.getType().isEmpty()) {
            speciesText += " - " + character.getType();
        }

        holder.tvSpecies.setText(speciesText);

        holder.tvInfoLine.setText("Gênero: " + character.getGender() + "  -  " + character.getEpisodeCount() + " episódios");

        String locationText = "Local: " + character.getLocationName() + "\nOrigem: " + character.getOriginName();

        holder.tvLocation.setText(locationText);

        Glide.with(holder.itemView.getContext()).load(character.getImage()).circleCrop().into(holder.ivCharacter);

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
        TextView tvName, tvStatus, tvSpecies, tvInfoLine, tvLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCharacter = itemView.findViewById(R.id.ivCharacter);
            tvName = itemView.findViewById(R.id.tvName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvSpecies = itemView.findViewById(R.id.tvSpecies);
            tvInfoLine = itemView.findViewById(R.id.tvInfoLine);
            tvLocation = itemView.findViewById(R.id.tvLocation);
        }
    }
}
