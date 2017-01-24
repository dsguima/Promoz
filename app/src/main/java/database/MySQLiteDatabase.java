package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by vallux on 23/01/17.
 */

public class MySQLiteDatabase extends SQLiteOpenHelper {

    public MySQLiteDatabase(Context context) {
        super(context, PromozContract.DB.DATABASE_NAME, null, PromozContract.DB.DATABASE_VERSION);

        for(int i = 0; i < PromozContract.tablesList.length; i++){
            Log.v("SQL", PromozContract.tablesList[i]);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("\nSQL", "CRIANDO DB\n");
        for(int i = 0; i < PromozContract.tablesList.length; i++){
            db.execSQL(PromozContract.tablesList[i]);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
