package com.nodelab.sala_operatoria;


import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nodelab.sala_operatoria.database.entity.Turno;
import com.nodelab.sala_operatoria.viewModel.TurnoViewModel;


import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.MyViewHolder> {
    private List<Turno> mDataset;
    private AppCompatActivity context;
    private TurnoViewModel turnoViewModel;
    private CalendarAdapter calendarAdapter = this;

    public void updateLastDate() {
        ((MainActivity)context).updateLastDate();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView data;
        private TextView dottoreGiorno;
        private TextView numeroDottoreGiorno;
        private TextView dottoreNotte;
        private TextView numeroDottoreNotte;
        private View constraintLayoutTotal;


        private MyViewHolder(View v) {
            super(v);
            data = v.findViewById(R.id.calendar_row_data);
            dottoreGiorno = v.findViewById(R.id.calendar_row_nome_giorno);
            numeroDottoreGiorno = v.findViewById(R.id.calendar_row_numero_dottore_giorno);
            dottoreNotte = v.findViewById(R.id.calendar_row_nome);
            numeroDottoreNotte = v.findViewById(R.id.calendar_row_numero_dottore);
            constraintLayoutTotal = v.findViewById(R.id.calendar_row_total);
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public CalendarAdapter(List<Turno> myDataset, AppCompatActivity context, TurnoViewModel turnoViewModel) {
        mDataset = myDataset;
        this.context = context;
        this.turnoViewModel = turnoViewModel;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public CalendarAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_row, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder,  int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


            holder.data.setText(mDataset.get(position).getData());
            holder.dottoreGiorno.setText(mDataset.get(position).getNomeDottoreGiorno());
            holder.numeroDottoreGiorno.setText(mDataset.get(position).getNumeroDottoreGiorno());
            holder.dottoreNotte.setText(mDataset.get(position).getNomeDottoreNotte());
            holder.numeroDottoreNotte.setText(mDataset.get(position).getNumeroDottoreNotte());
            holder.constraintLayoutTotal.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    FragmentManager fm = context.getSupportFragmentManager();

                    SaveTurnoDialog.display(fm, mDataset.get(holder.getAdapterPosition()), turnoViewModel, (MainActivity)context);

                    return false;
                }
            });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setTurni(List<Turno> turni) {
        this.mDataset = turni;
        notifyDataSetChanged();
    }

    public void addTurno(Turno turn) {
        this.mDataset.add(turn);
    }


}
