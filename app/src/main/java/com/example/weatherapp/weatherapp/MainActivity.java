package com.example.weatherapp.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    // Метод вызывается по нажатию на любой пункт меню. В качестве агрумента приходит item меню.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // обработка нажатий
        switch (item.getItemId()) {
            case R.id.item1: {
                adapter.addView("New");
                break;
            }
            case R.id.item3:
                adapter.clear();
                return true;
            default:
        }
                return super.onOptionsItemSelected(item);

    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_edit:
                adapter.editView(info.position);
                return true;
            case R.id.menu_delete:
                adapter.removeView(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void initGUI() {

        recyclerView = findViewById(R.id.list);
        adapter = new Adapter( getData(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        registerForContextMenu(recyclerView);
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                       adapter.addView("NewOne");
                        return true;
                    case R.id.item3:
                        adapter.clear();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.inflate(R.menu.main);
        popup.show();
    }

    private List<String> getData(){
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            result.add("Element " + i);
        }
        return result;
    }
}
