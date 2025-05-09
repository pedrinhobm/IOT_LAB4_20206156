package com.example.tele_weather.adapters;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tele_weather.R;
import com.example.tele_weather.models.Location;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    private List<Location> locations;
    private NavController navController;

    public LocationAdapter(List<Location> locations, NavController navController) {
        this.locations = locations;
        this.navController = navController;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location location = locations.get(position);
        holder.bind(location);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
    // aqui si use ia porque que se debian obtener en un textview
    // tuve que optar por una clase publica para recopilarlo , aspi lo haré con deportes y pronostico Adapter
    public class LocationViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView regionTextView;
        private TextView countryTextView;
        private TextView idTextView;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.location_name_text_view);
            idTextView = itemView.findViewById(R.id.location_id_text_view);
            regionTextView = itemView.findViewById(R.id.location_region_text_view);
            countryTextView = itemView.findViewById(R.id.location_country_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Location selectedLocation = locations.get(getAdapterPosition());
                    Bundle bundle = new Bundle();
                    bundle.putString("idLocation", String.valueOf(selectedLocation.getId())); // con un getId() permitira que al seleccionar una localidad la identifique
                    navController.navigate(R.id.action_locationFragment_to_pronosticoFragment, bundle); // y nos dirija a la siguiente vista del pronostico donde mediran la temperatura por los proximos dias
                }
            });
        }

        public void bind(Location location) { // en la funcion de bind, se permitira obtener los datos de la localidad
            nameTextView.setText(location.getName()); // su nombre, region y pais exacto
            idTextView.setText(String.valueOf(location.getId()));
            regionTextView.setText(location.getRegion());
            countryTextView.setText(location.getCountry());
        }
    }
}