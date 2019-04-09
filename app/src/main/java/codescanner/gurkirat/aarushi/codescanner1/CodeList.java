package codescanner.gurkirat.aarushi.codescanner1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CodeList extends Fragment implements updateList{

    View view;
    Toolbar bar;
    RecyclerView list;
    CodesAdapter adapter;
    TextView empty;
    viewChange mCallback;
    dataBaseAdapter helper;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (viewChange) context;
        } catch (ClassCastException e) {
            Log.e("Exception", e.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list = view.findViewById(R.id.list);
        bar = view.findViewById(R.id.bar);
        empty = view.findViewById(R.id.empty);

        helper = new dataBaseAdapter(getActivity());

        updateList();

        bar.setNavigationOnClickListener(view -> mCallback.viewCamera());
    }

    void updateList(){
        List<CodeData> clist = helper.getCodes();
        if(clist == null || clist.size()==0){

            empty.setVisibility(View.VISIBLE);
        }
        else {
            empty.setVisibility(View.GONE);
            adapter = new CodesAdapter(getActivity(), clist, mCallback, helper, this);
            list.setAdapter(adapter);
            list.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    @Override
    public void rowRemoved() {
        if(adapter.getItemCount()>0)
            empty.setVisibility(View.GONE);
        else empty.setVisibility(View.VISIBLE);
    }
}
