package promoz.com.br.promoz.dao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import promoz.com.br.promoz.dao.HistoricTypeCoinDAO;
import promoz.com.br.promoz.model.HistoricTypeCoin;

/**
 * Created by vallux on 23/01/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "promoz.db";

    public DatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.v("SQL", "\n\nCRIANDO DB\n\n");

        for(int i = 0; i < PromozContract.tablesCreationList.length; i++)
            db.execSQL(PromozContract.tablesCreationList[i]);

        Log.v("SQL", "\n\nPOPULANDO TABELAS\n\n");

        for(int i = 0; i < PromozContract.valuesToPopulate.length; i++)
            db.execSQL(PromozContract.valuesToPopulate[i]);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
