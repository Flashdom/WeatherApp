package com.example.weatherapp.weatherapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.CustomViewHolder> implements onAdapterClick{

    private List<String> myList;
    private Context mContext;

    Adapter(List<String> Data, Context context) {
        myList = Data;
        mContext = context;

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);

        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(Adapter.CustomViewHolder customViewHolder, int i) {
        customViewHolder.bind(myList.get(i));
        customViewHolder.attachCallback(this);

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public void addView(String text) {
        myList.add(text);
        notifyItemInserted(myList.size() - 1);
        //notifyDataSetChanged();
    }

    @Override
    public void removeView(int position) {
        myList.remove(position);
        notifyDataSetChanged();
    }

    public void setAll(List<String> list) {
        myList = list;

        notifyDataSetChanged();
    }

    @Override
    public void editView(int position) {
        myList.set(position, "Edit");
        notifyItemChanged(position);
    }

    public void clear() {
        myList.clear();
        notifyDataSetChanged();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener, View.OnCreateContextMenuListener {

        private onAdapterClick callback;

        void attachCallback(onAdapterClick callback){
            this.callback = callback;
        }

        TextView mTextView;
        TextView mContextView;

        CustomViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.Number);
            mContextView = v.findViewById(R.id.Option);
            mContextView.setOnClickListener(this);
            v.setOnCreateContextMenuListener(this);

        }

        void bind(String text) {
            mTextView.setText(text);

        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.Option: {
                    /*PopupMenu popup = new PopupMenu(v.getContext(), v);
                    popup.getMenuInflater().inflate(R.menu.context_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(this);
                    popup.show();

                    break;*/
                }
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_edit:
                    if (callback != null) callback.editView(getAdapterPosition());
                    return true;
                case R.id.menu_delete:
                    if (callback != null) callback.removeView(getAdapterPosition());
                    return true;

                default:
                    return false;
            }
        }



        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuInflater inflater = ((MainActivity) mContext).getMenuInflater();

            inflater.inflate(R.menu.context_menu, contextMenu);
        }
    }

    
    }
interface onAdapterClick{
    void removeView(int pos);
    void editView(int pos);
}
