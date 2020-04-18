package ru.natiel.week1homework;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.natiel.week1homework.api.Api;
import ru.natiel.week1homework.api.WebService;
import ru.natiel.week1homework.diagram.DiagramView;
import ru.natiel.week1homework.models.AuthResponse;
import ru.natiel.week1homework.models.ChargeModel;
import ru.natiel.week1homework.models.ItemRemote;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class BalanceFragment extends Fragment implements FragmentInterface{
    private int color;
    private String type;
    private final static String COLOR_ID = "colorId";
    private final static String TYPE = "fragmentType";
    private View view;
    private WebService webService;
    private Api api;
    private String authToken;
    private float expencesAll;
    private float incomesAll;
    private DiagramView diagramView;
    private TextView expencesText;
    private TextView incomeText;

    public static BalanceFragment newInstance(final int colorId, final String type) {
        BalanceFragment balanceFragment = new BalanceFragment();
        balanceFragment.color = colorId;
        balanceFragment.type = type;
        Bundle bundle = new Bundle();
        bundle.putInt(COLOR_ID, colorId);
        bundle.putString(TYPE, type);
        balanceFragment.setArguments(bundle);
        return balanceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_balance, null);
        webService = WebService.getInstance();
        api = webService.getApi();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expencesText = view.findViewById(R.id.txtBalanceExpense);
        incomeText = view.findViewById(R.id.txtBalanceIncome);
        diagramView = view.findViewById(R.id.dvBalance);
        if(isCachedTotalEmpty())
            loadItems();
        expencesText.setText(String.format("%s P", expencesAll));
        incomeText.setText(String.format("%s P", incomesAll));
        diagramView.update(expencesAll, incomesAll);
    }

    private boolean isCachedTotalEmpty() {
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        int expencesCached = sharedPreferences.getInt(MainActivity.EXPENSE, -1);
        int incomeCached = sharedPreferences.getInt(MainActivity.INCOME, -1);
        if (expencesCached != -1 && incomeCached != -1){
            expencesAll = expencesCached;
            incomesAll = incomeCached;
            return false;
        } else
            return true;
    }

    @Override
    public void loadItems(){
        getToken();
        getRequest(MainActivity.EXPENSE, true);
        getRequest(MainActivity.INCOME, false);
        Log.d(TAG, "loadItems: " + expencesAll + " " + incomesAll );
    }

    private void getRequest(String type, final boolean isExpences) {
        api.request(type, authToken)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ItemRemote>>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onSuccess(List<ItemRemote> itemRemotes) {
                        float result = 0;
                        for (ItemRemote item : itemRemotes) {
                            result += item.getPrice();
                        }
                        if(isExpences)
                            expencesAll = result;
                        else
                            incomesAll = result;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(view.getContext().getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void getToken() {
        if (TextUtils.isEmpty(authToken)) {
            SharedPreferences sharedPreferences = view.getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
            authToken = sharedPreferences.getString(AuthResponse.AUTH_TOKEN_KEY, "");
        }
    }
}
