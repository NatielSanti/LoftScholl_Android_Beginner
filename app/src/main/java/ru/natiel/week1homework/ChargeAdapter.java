package ru.natiel.week1homework;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChargeAdapter extends RecyclerView.Adapter<ChargeAdapter.ChargeViewHolder> {
	
	private List<ChargeModel> mItemsList = new ArrayList<ChargeModel>();
	
	@NonNull
	@Override
	public ChargeViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
		View itemView = View.inflate(parent.getContext(), R.layout.cell_charge, null);
		
		return new ChargeViewHolder(itemView);
	}
	
	@Override
	public void onBindViewHolder(@NonNull final ChargeViewHolder holder, final int position) {
		holder.bindItem(mItemsList.get(position));
	}
	
	public void addItem(ChargeModel item) {
		mItemsList.add(item);
		notifyDataSetChanged();
	}
	
	@Override
	public int getItemCount() {
		return mItemsList.size();
	}
	
	static class ChargeViewHolder extends RecyclerView.ViewHolder {
		
		private TextView mNameView;
		private TextView mPriceView;
		
		public ChargeViewHolder(@NonNull final View itemView) {
			super(itemView);
			
			mNameView = itemView.findViewById(R.id.name_view);
			mPriceView = itemView.findViewById(R.id.price_view);
		}
		
		public void bindItem(final ChargeModel item) {
			mNameView.setText(item.getName());
			mPriceView.setText(
				mPriceView.getContext().getResources().getString(R.string.price_with_currency, String.valueOf(item.getPrice()))
			);
		}
	}
}
