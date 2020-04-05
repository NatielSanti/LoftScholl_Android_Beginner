package ru.natiel.week1homework;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.natiel.week1homework.api.Api;
import ru.natiel.week1homework.api.WebService;
import ru.natiel.week1homework.models.AuthResponse;
import ru.natiel.week1homework.models.ChargeModel;
import ru.natiel.week1homework.models.ItemRemote;

import java.util.ArrayList;
import java.util.List;

public class BudgetFragment extends Fragment {

    private static final int REQUEST_CODE = 100;
    private View view;
    private ChargeAdapter mAdapter;
    private WebService webService;
    private Api api;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webService = WebService.getInstance();
        api = webService.getApi();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState
    ) {
        view = inflater.inflate(R.layout.fragment_budget, null);

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

        loadItems();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadItems();
    }

    public void loadItems() {
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(AuthResponse.AUTH_TOKEN_KEY, "");

        api.request("expense", authToken)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ItemRemote>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // Stub
                    }

                    @Override
                    public void onSuccess(List<ItemRemote> itemRemotes) {
                        List<ChargeModel> chargeModels = new ArrayList<>(itemRemotes.size());
                        for (ItemRemote item : itemRemotes) {
                            ChargeModel chargeModel = new ChargeModel(item);
                            chargeModels.add(chargeModel);
                        }

                        mAdapter.setNewData(chargeModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(view.getContext().getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
