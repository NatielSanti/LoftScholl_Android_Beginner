package ru.natiel.week1homework.screens.main.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import ru.natiel.week1homework.screens.main.adapter.ChargeAdapter;
import ru.natiel.week1homework.screens.main.adapter.ChargeModel;
import ru.natiel.week1homework.screens.second.SecondActivity;

public class BudgetFragment extends Fragment {
	
	private static final int REQUEST_CODE = 100;
	private ChargeAdapter mAdapter;
	
	@Nullable
	@Override
	public View onCreateView(
		@NonNull final LayoutInflater inflater,
		@Nullable final ViewGroup container,
		@Nullable final Bundle savedInstanceState
	) {
		View view = inflater.inflate(R.layout.fragment_budget, null);
		
		Button callAddButton = view.findViewById(R.id.call_add_item_activity);
		callAddButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				startActivityForResult(new Intent(getActivity(), SecondActivity.class),
					REQUEST_CODE);
			}
		});
		
		RecyclerView recyclerView = view.findViewById(R.id.budget_item_list);
		
		mAdapter = new ChargeAdapter();
		recyclerView.setAdapter(mAdapter);
		
		mAdapter.addItem(new ChargeModel("Молоко", 70));
		mAdapter.addItem(new ChargeModel("Зубная щетка", 70));
		mAdapter.addItem(new ChargeModel("Новый телевизор", 20000));
		
		return view;
	}
	
	@Override
	public void onActivityResult(
		final int requestCode, final int resultCode, @Nullable final Intent data
	) {
		super.onActivityResult(requestCode, resultCode, data);
		
		int price;
		try {
			price = Integer.parseInt(data.getStringExtra("price"));
		} catch (NumberFormatException e) {
			price = 0;
		}
		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			mAdapter.addItem(new ChargeModel(data.getStringExtra("name"), price));
		}
	}
	
	
}
