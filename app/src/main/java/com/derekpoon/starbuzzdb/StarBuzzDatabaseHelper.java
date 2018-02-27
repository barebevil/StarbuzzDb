package com.derekpoon.starbuzzdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by derekpoon on 28/12/2017.
 */

//this class enables you to create and manage databases
//must override onCreate and onUpgrade
//these methods are mandatory

//must extend SQLiteOpenHelper
class StarBuzzDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Starbuzz"; //name of db
    private static final int DB_VERSION = 2; //version of the db

    StarBuzzDatabaseHelper(Context context) {
        //calling SQLiteOpenHelper superclass and passing it the db name and
        //version
        super(context, DB_NAME, null, DB_VERSION);
    }

    //onCreate() gets called when the database first gets created, so we're
    //using it to create the table and insert data
    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    //onUpgrade gets called when the database needs to be upgraded, e.g if you
    //need to make table changes to the db after it has been released
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            // this is the SQL command to create the table
            // _id column is the primary key
            // table name = DRINK
            db.execSQL("CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT, " //create the drink table
                    //below are the table columns
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER);");

            //insert each drink in a separate row in the DRINK table
            insertDrink(db, "Latte", "Espresso and steamed milk", R.drawable.latte);
            insertDrink(db, "Cappuccino", "Espresso, hot milk and steamed-milk foam", R.drawable.cappuccino);
            insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter);
        }
        if (oldVersion < 2) {
            //code to add the extra column
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
        }
    }

    //this method inserts several drinks
    private static void insertDrink(SQLiteDatabase db, String name, String description, int resourceId) {

        //ContentValues object is used to hold name / value pairs of data
        ContentValues drinkValues = new ContentValues();

        //example
//        drinkValues.put("NAME", "Latte");
//        drinkValues.put("DESCRIPTION", "Espresso and steamed milk");
//        drinkValues.put("IMAGE_RESOURCE_ID", R.drawable.latte);

        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceId);

        //insert the values into the DRINK table
        db.insert("DRINK", null, drinkValues);
    }

}
