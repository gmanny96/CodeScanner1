package codescanner.gurkirat.aarushi.codescanner1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.util.Collections;
import java.util.List;

public class CodesAdapter extends RecyclerView.Adapter {

    private final LayoutInflater inflater;
    private List<CodeData> data = Collections.emptyList();
    private Context context;
    private viewChange callback;
    private dataBaseAdapter adapter;
    private updateList inter;

    CodesAdapter(Context context, List<CodeData> data, viewChange callback, dataBaseAdapter adapter, updateList inter)
    {
        this.context=context;
        this.data=data;
        inflater = LayoutInflater.from(context);
        this.callback = callback;
        this.adapter = adapter;
        this.inter = inter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new row(inflater.inflate(R.layout.code_row, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        row r = (row) holder;
        r.name.setText(data.get(position).name);
        r.time.setText(data.get(position).time);
    }

    @Override
    public int getItemCount() {
        if(data.size()==0 || data == null)
            return 0;
        return data.size();
    }

    void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        inter.rowRemoved();
    }

    private class row extends RecyclerView.ViewHolder {

        TextView name,time;
        RelativeLayout layout;
        SwipeLayout swipe;

        row(View itemView) {
            super(itemView);
            layout =  itemView.findViewById(R.id.layout);
            name =  itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.date);
            swipe = itemView.findViewById(R.id.swipe);

            swipe.addSwipeListener(new SwipeLayout.SwipeListener() {
                @Override
                public void onStartOpen(SwipeLayout layout) {

                }

                @Override
                public void onOpen(SwipeLayout layout) {
                    adapter.removeCode(data.get(getAdapterPosition()).name);
                    removeItem(getAdapterPosition());
                }

                @Override
                public void onStartClose(SwipeLayout layout) {

                }

                @Override
                public void onClose(SwipeLayout layout) {

                }

                @Override
                public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

                }

                @Override
                public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

                }
            });

            layout.setOnClickListener(v -> {
               if(callback!=null)
                    callback.openCode(data.get(getAdapterPosition()).name, dataBaseAdapter.getInstance(context).getCode(data.get(getAdapterPosition()).name));
            });
        }
    }

}
