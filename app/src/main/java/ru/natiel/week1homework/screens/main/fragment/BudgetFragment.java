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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.natiel.week1homework.R;
import ru.natiel.week1homework.screens.main.MainActivity;
import ru.natiel.week1homework.screens.main.adapter.ChargeModel;
import ru.natiel.week1homework.screens.main.adapter.ChargesAdapter;
import ru.natiel.week1homework.screens.second.SecondActivity;

public class BudgetFragment extends Fragment {

    private static final int REQUEST_CODE = 100;
    public static final int ADD_ITEM_REQUEST = 1;
    private ChargesAdapter adapter = new ChargesAdapter();
    private MainActivity mainActivity;

    public BudgetFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, null);

        Button callAddButton = view.findViewById(R.id.call_add_item_activity);
        callAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivityForResult(new Intent(getActivity(), SecondActivity.class),
                        REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerMain);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity.getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false));

        final Intent secondIntent = SecondActivity.startIntent(mainActivity);
        view.findViewById(R.id.fabMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(secondIntent, ADD_ITEM_REQUEST);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ITEM_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            ChargeModel chargeModel = (ChargeModel) data.getSerializableExtra(ChargeModel.KEY_NAME);
            adapter.addDataToTop(chargeModel);
        }
    }
}
