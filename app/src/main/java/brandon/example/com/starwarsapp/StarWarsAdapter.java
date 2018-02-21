package brandon.example.com.starwarsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by brandon on 16/02/18.
 */

public class StarWarsAdapter extends ArrayAdapter<StarWars> {
    private Context context;
    public StarWarsAdapter(@NonNull Context context, int resource, @NonNull List<StarWars> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.starwars_layout,parent,false);
        }
        StarWars starWars = getItem(position);
        TextView textView = (TextView) convertView.findViewById(R.id.nombre);
        textView.setText(starWars.name);
        TextView textView1 = (TextView) convertView.findViewById(R.id.fecha_nacimiento);
        textView1.setText(starWars.birth_year);


        return convertView;
    }
}
