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

    public class LocationViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView regionTextView;
        private TextView countryTextView;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.location_name_text_view);
            regionTextView = itemView.findViewById(R.id.location_region_text_view);
            countryTextView = itemView.findViewById(R.id.location_country_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Location selectedLocation = locations.get(getAdapterPosition());
                    //  Navegar a PronosticoFragment pasando el idLocation
                    Bundle bundle = new Bundle();
                    bundle.putString("idLocation", String.valueOf(selectedLocation.getId()));  //  Asegúrate de tener un getId()
                    navController.navigate(R.id.action_locationFragment_to_pronosticoFragment, bundle);
                }
            });
        }

        public void bind(Location location) {
            nameTextView.setText(location.getName());
            regionTextView.setText(location.getRegion());
            countryTextView.setText(location.getCountry());
        }
    }
}