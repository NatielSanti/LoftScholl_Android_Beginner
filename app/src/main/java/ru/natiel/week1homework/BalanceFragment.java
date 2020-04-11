package ru.natiel.week1homework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ru.natiel.week1homework.diagram.DiagramView;

public class BalanceFragment extends Fragment {
    private int color;
    private String type;
    private final static String COLOR_ID = "colorId";
    private final static String TYPE = "fragmentType";
    private View view;

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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DiagramView diagramView = view.findViewById(R.id.dvBalance);
        diagramView.update(5400, 15000);
    }
}
