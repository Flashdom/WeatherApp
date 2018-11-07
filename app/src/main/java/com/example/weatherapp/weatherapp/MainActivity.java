package com.example.weatherapp.weatherapp;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
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
            case R.id.menu_button_add:
            {
                adapter.addView("New");
                break;
            }
            case R.id.menu_button_clear:
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
        navigationView=findViewById(R.id.nav_view);
        DrawerLayout drawerLayout =findViewById(R.id.drawer_layout);
        initHeader();
    }
    private void initHeader(){
        ImageView imageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        TextView nameView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView);
        imageView.setImageResource(R.drawable.ic_menu_gallery);
        nameView.setText("text");
    }
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_button_add:
                       adapter.addView("NewOne");
                        return true;
                    case R.id.menu_button_clear:
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
