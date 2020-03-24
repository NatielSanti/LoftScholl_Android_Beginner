package ru.natiel.week1homework.screens.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.natiel.week1homework.R;

import java.util.ArrayList;
import java.util.List;

public class ChargesAdapter extends RecyclerView.Adapter<ChargesAdapter.ChargesViewHolder> {

    private List<ChargeModel> dataList = new ArrayList<>();

    public void addNewData( List<ChargeModel> newData){
        dataList.clear();
        dataList.addAll(newData);
        notifyDataSetChanged();
    }

    public void addDataToTop(ChargeModel data) {
        dataList.add(data);
        notify
    }

    @NonNull
    @Override
    public ChargesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ChargesViewHolder(layoutInflater.inflate(R.layout.cell_charge, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChargesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ChargesViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName = itemView.findViewById(R.id.txtChargeText);
        private TextView txtValue = itemView.findViewById(R.id.txtChargeValue);
        public ChargesViewHolder(@NonNull View itemView){
            super(itemView);
        }

        void bind(ChargeModel chargeModel){
            txtName
        }
    }
}
