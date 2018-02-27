package com.derekpoon.starbuzzdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;
import android.widget.CursorAdapter;
import android.widget.Toast;

public class TopLevelActivity extends AppCompatActivity {

    //adding these variables so we have access to them in the onDestroy() method
    private SQLiteDatabase db;
    private Cursor favouritesCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        //create an onItemClickListener
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> listView,
                                    View view,
                                    int position,
                                    long id) {
                //Drinks is the first item in the list view at position 0
                if (position == 0) {

                    //the intent is coming from the TopLevelActivity
                    //it needs to launch DrinkCategoryActivity
                    Intent intent = new Intent(TopLevelActivity.this, DrinkCategoryActivity.class);
                    startActivity(intent);
                }
            }
        };

        //add the listener to the list view
        ListView listView = (ListView)findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);

        //populate the list_favourites ListView from a cursor
        ListView listFavourites = (ListView)findViewById(R.id.list_favorites);
        try {
            SQLiteOpenHelper starBuzzDatabaseHelper = new StarBuzzDatabaseHelper(this);
            db = starBuzzDatabaseHelper.getReadableDatabase();
            CursorAdapter favoriteAdapter =
                    new SimpleCursorAdapter(TopLevelActivity.this,
                            android.R.layout.simple_list_item_1, favouritesCursor,
                            new String[]{"NAME"},
                            new int[]{android.R.id.text1}, 0);
            listFavourites.setAdapter(favoriteAdapter); } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        //navigate to DrinkActivity  if a drink is clicked
        listFavourites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View v, int position, long id) {
                Intent intent = new Intent(TopLevelActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int)id);
                startActivity(intent);
            }
        });
    }

    //Close the cursor and database in the onDestroy() method
    @Override
    public void onDestroy() {
        super.onDestroy();
        favouritesCursor.close();
        db.close();
    }

    //this gets called when the user returns to the TopLevelActivity
    public void onRestart() {
        super.onRestart();
        try{
            StarBuzzDatabaseHelper starbuzzDatabaseHelper = new StarBuzzDatabaseHelper(this);
            db = starbuzzDatabaseHelper.getReadableDatabase();

            //create the cursor
            Cursor newCursor = db.query("DRINK",
                    new String[] { "_id", "NAME"}, "FAVORITE = 1",
                    null, null, null, null);
            ListView listFavorites = (ListView)findViewById(R.id.list_favorites);
            //get the ListView's adapter
            CursorAdapter adapter = (CursorAdapter) listFavorites.getAdapter();
            //change the cursor used by the cursor adapter to the new one
            adapter.changeCursor(newCursor);
            favouritesCursor = newCursor;
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT); toast.show();
        }
    }
}
