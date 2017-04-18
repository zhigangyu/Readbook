package com.uplinfo.readbook.view.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uplinfo.readbook.R;
public class BookFragment extends Fragment {
	
	private RecyclerView recyclerView;
	private List<String> mDatas;
	private RecyclerAdapter recyclerAdapter;
	
	@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState); 
		
		View view = inflater.inflate(R.layout.fragment_book, container,false); 
		

		initData();
		recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
		recyclerView.setAdapter(recyclerAdapter = new RecyclerAdapter(mDatas,inflater.getContext()));
		
        return view;
    }
	
	protected void initData() {
		mDatas = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			mDatas.add("zzz " + i);
		}
	}
	
	public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
	    private Context context;
	    private List<String> list;

	    public RecyclerAdapter(List<String> list, Context context) {
	        this.list = list;
	        this.context = context;
	    }

	    @Override
	    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
	        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_book,
	                parent, false);
	        ViewHolder holder = new ViewHolder(view);
	        return holder;
	    }

	    @Override
	    public void onBindViewHolder(ViewHolder holder, int position) {
	        String s = list.get(position);
	        holder.item_textView.setText(s);
	    }

	    @Override
	    public int getItemCount() {
	        return list.size();
	    }

	    public class ViewHolder extends RecyclerView.ViewHolder {
	        private TextView item_textView;

	        public ViewHolder(View itemView) {
	            super(itemView);
	            item_textView = (TextView) itemView.findViewById(R.id.txt_name);
	        }
	    }
	}
}
