package com.example.tele_weather.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tele_weather.R;
import com.example.tele_weather.models.Sport;
import com.example.tele_weather.models.Football;
import java.util.List;

public class DeportesAdapter extends RecyclerView.Adapter<DeportesAdapter.DeportesViewHolder> {

    private List<Sport> sports;

    public DeportesAdapter(List<Sport> sports) {
        this.sports = sports;
    }

    @NonNull
    @Override
    public DeportesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deportes, parent, false);
        return new DeportesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeportesViewHolder holder, int position) {
        Sport sport = sports.get(position);
        holder.bind(sport);
    }

    @Override
    public int getItemCount() {
        return sports.size();
    }

    public class DeportesViewHolder extends RecyclerView.ViewHolder {

        private TextView stadiumTextView;
        private TextView countryTextView;
        private TextView tournamentTextView;
        private TextView startTextView;
        private TextView matchTextView;

        public DeportesViewHolder(@NonNull View itemView) {
            super(itemView);
            stadiumTextView = itemView.findViewById(R.id.deportes_stadium_text_view);
            countryTextView = itemView.findViewById(R.id.deportes_country_text_view);
            tournamentTextView = itemView.findViewById(R.id.deportes_tournament_text_view);
            startTextView = itemView.findViewById(R.id.deportes_start_text_view);
            matchTextView = itemView.findViewById(R.id.deportes_match_text_view);
        }

        public void bind(Sport sport) {
            if (sport != null && sport.getFootball() != null) {
                Football football = sport.getFootball().get(0);  //  Asume que es el primer elemento
                stadiumTextView.setText("Stadium: " + football.getStadium());
                countryTextView.setText("Country: " + football.getCountry());
                tournamentTextView.setText("Tournament: " + football.getTournament());
                startTextView.setText("Start: " + football.getStart());
                matchTextView.setText("Match: " + football.getMatch());
            }
        }
    }
}