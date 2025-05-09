package com.example.tele_weather.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tele_weather.R;
import com.example.tele_weather.models.ForecastDay;
import java.util.List;

public class PronosticoAdapter extends RecyclerView.Adapter<PronosticoAdapter.PronosticoViewHolder> {

    private List<ForecastDay> forecastDays;

    public PronosticoAdapter(List<ForecastDay> forecastDays) {
        this.forecastDays = forecastDays;
    }

    @NonNull
    @Override
    public PronosticoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pronostico, parent, false);
        return new PronosticoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PronosticoViewHolder holder, int position) {
        ForecastDay forecastDay = forecastDays.get(position);
        holder.bind(forecastDay);
    }

    @Override
    public int getItemCount() {
        return forecastDays.size();
    }
    // como indique anteriormente, replique de nuevo el pronostico como en los adapters pasados
    // con los datos que iran en cada dia que tú seleccionas como el dia
    // la temperatura maxima y minima , y la condicion climática
    public class PronosticoViewHolder extends RecyclerView.ViewHolder {
        private TextView dateTextView;
        private TextView maxTempTextView;
        private TextView minTempTextView;
        private TextView conditionTextView;

        public PronosticoViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.pronostico_date_text_view);
            maxTempTextView = itemView.findViewById(R.id.pronostico_max_temp_text_view);
            minTempTextView = itemView.findViewById(R.id.pronostico_min_temp_text_view);
            conditionTextView = itemView.findViewById(R.id.pronostico_condition_text_view);
        }
        public void bind(ForecastDay forecastDay) { // en la funcion de bind se recibirá y mostrará los datos
            dateTextView.setText(forecastDay.getDate());
            maxTempTextView.setText("Max temperature: " + forecastDay.getDay().getMaxtemp_c() + "°C");
            minTempTextView.setText("Min temperature: " + forecastDay.getDay().getMintemp_c() + "°C");
            conditionTextView.setText("Condition: " + forecastDay.getDay().getCondition().getText());
        }
    }
}