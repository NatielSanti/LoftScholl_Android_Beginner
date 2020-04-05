package ru.natiel.week1homework;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.natiel.week1homework.api.Api;
import ru.natiel.week1homework.api.WebService;
import ru.natiel.week1homework.models.AuthResponse;
import ru.natiel.week1homework.models.ChargeModel;
import ru.natiel.week1homework.models.ItemRemote;

import java.util.ArrayList;
import java.util.List;

public class BudgetFragment extends Fragment {

    private static final int REQUEST_CODE = 100;
    private static final String TYPE = "fragmentType";
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
        final int realPrice = price;
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//			mAdapter.addItem(new ChargeModel(data.getStringExtra("name"), price));
            final String name = data.getStringExtra("name");
            final String token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(MainActivity.TOKEN, "");
            Call<Status> call = api.addItem(new AddItemRequest(name, getArguments().getString(TYPE), price), token);
            call.enqueue(new Callback<Status>() {
                @Override
                public void onResponse(
                        final Call<Status> call, final Response<Status> response
                ) {
                    if (response.body().getStatus().equals("success")) {
                        mAdapter.addItem(new ChargeModel(name, realPrice));
                    }
                }
                @Override
                public void onFailure(final Call<Status> call, final Throwable t) {
                    t.printStackTrace();
                }
            });
        }
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
                        for (ItemRemote item: itemRemotes) {
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
