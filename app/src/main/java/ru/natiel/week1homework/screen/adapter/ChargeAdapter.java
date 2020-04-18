package ru.natiel.week1homework.screen.adapter;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.natiel.week1homework.R;
import ru.natiel.week1homework.model.ChargeModel;

import java.util.ArrayList;
import java.util.List;

public class ChargeAdapter extends RecyclerView.Adapter<ChargeAdapter.ChargeViewHolder> {

    private List<ChargeModel> itemsList = new ArrayList<ChargeModel>();
    private ChargeModelAdapterListener listener;

    private SparseBooleanArray selectedItem = new SparseBooleanArray();

    public void clearSelected(){
        selectedItem.clear();
        notifyDataSetChanged();
    }

    public void toogleItem(int position){
        selectedItem.put(position, !selectedItem.get(position));
        notifyDataSetChanged();
    }

    public void clearItems(int position){
        selectedItem.put(position, false);
        notifyDataSetChanged();
    }

    public int getSelectedSize(){
        int result = 0;
        for(int i = 0; i<itemsList.size(); i++){
            if(selectedItem.get(i))
                result ++;
        }
        return result;
    }

    public List<Integer> getSelectedItemIdList(){
        List<Integer> result = new ArrayList<>();
        int i = 0;
        for(ChargeModel item: itemsList){
            if(selectedItem.get(i))
                result.add(item.getId());
            i++;
        }
        return result;
    }

    public void setListener(ChargeModelAdapterListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChargeViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.cell_charge, null);

        return new ChargeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChargeViewHolder holder, final int position) {
        holder.bindItem(itemsList.get(position), selectedItem.get(position));
        holder.setListener(listener, itemsList.get(position), position);
    }

    public void addItem(ChargeModel item) {
        itemsList.add(item);
        notifyDataSetChanged();
    }

    public void setNewData(List<ChargeModel> chargeModels) {
        itemsList.clear();
        itemsList.addAll(chargeModels);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    static class ChargeViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private TextView mNameView;
        private TextView mPriceView;

        public ChargeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            mNameView = itemView.findViewById(R.id.name_view);
            mPriceView = itemView.findViewById(R.id.price_view);
        }

        public void bindItem(final ChargeModel item, boolean isSelected) {
            itemView.setSelected(isSelected);
            mNameView.setText(item.getName());
            mPriceView.setText(String.valueOf(item.getPrice()));
        }

        public void setListener(final ChargeModelAdapterListener listener, final ChargeModel item, final int position){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item, position);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(item, position);
                    return false;
                }
            });
        }
    }
}
