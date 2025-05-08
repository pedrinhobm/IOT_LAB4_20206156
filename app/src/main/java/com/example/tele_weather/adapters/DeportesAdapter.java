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
    // aqui si use ia porque como eran bastantes datos que se debian obtener en un view
    // tuve que optar por una clase publica para recopilarlo
    public class DeportesViewHolder extends RecyclerView.ViewHolder {
        private TextView stadiumTextView;
        private TextView countryTextView;
        private TextView tournamentTextView;
        private TextView startTextView;
        private TextView matchTextView;

        public DeportesViewHolder(@NonNull View itemView) {
            super(itemView); // aqui se encuentran cada uno de los datos de los deportes con los datos del layout
            stadiumTextView = itemView.findViewById(R.id.deportes_stadium_text_view);
            countryTextView = itemView.findViewById(R.id.deportes_country_text_view);
            tournamentTextView = itemView.findViewById(R.id.deportes_tournament_text_view);
            startTextView = itemView.findViewById(R.id.deportes_start_text_view);
            matchTextView = itemView.findViewById(R.id.deportes_match_text_view);
        }
        public void bind(Sport sport) { // aqui es donde se va mostrar cada uno de los partidos de la localidad que escriba
            if (sport != null && sport.getFootball() != null) {
                Football football = sport.getFootball().get(0);
                stadiumTextView.setText("Estadio: " + football.getStadium());
                countryTextView.setText("País: " + football.getCountry());
                tournamentTextView.setText("Torneo: " + football.getTournament());
                startTextView.setText("Incio: " + football.getStart());
                matchTextView.setText("Partido: " + football.getMatch());
            }
        }
    }
}