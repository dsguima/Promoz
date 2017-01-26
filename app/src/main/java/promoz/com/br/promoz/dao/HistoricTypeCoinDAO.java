package promoz.com.br.promoz.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import promoz.com.br.promoz.dao.db.MySQLiteDatabase;
import promoz.com.br.promoz.dao.db.PromozContract;
import promoz.com.br.promoz.model.HistoricTypeCoin;

/**
 * Created by vallux on 25/01/17.
 */

public class HistoricTypeCoinDAO extends PromozContract.HistoricTypeCoin {

    private MySQLiteDatabase myDatabaseHelper;
    private SQLiteDatabase database;

    public HistoricTypeCoinDAO(Context context) {
        this.myDatabaseHelper = new MySQLiteDatabase(context);
    }

    private SQLiteDatabase getDatabase(){
        if (database == null){
            database = myDatabaseHelper.getWritableDatabase();
        }
        return database;
    }

    private HistoricTypeCoin populateUser(Cursor cursor){ // Popula o objeto "HistoricTypeCoin" com os dados do cursor
        HistoricTypeCoin model = new HistoricTypeCoin(
                cursor.getInt(cursor.getColumnIndex(_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_HST_TP_DESC))
        );
        return model;
    }

    public long saveUser(HistoricTypeCoin hist){ // salva os campos do objeto na tabela - atualiza ou cria um novo caso não exista

        ContentValues values = new ContentValues();
        values.put(COLUMN_HST_TP_DESC, hist.getDescription());

        if(hist.get_id() != null){
            return getDatabase().update(TABLE_NAME, values, "_id = ?", new String[]{ hist.get_id().toString() });
        }
        return getDatabase().insert(TABLE_NAME, null, values);
    }

    public List<HistoricTypeCoin> listUser(){
        Cursor cursor = getDatabase().query(TABLE_NAME, allFields, null, null, null, null, null);

//        cursor.moveToFirst();
        List<HistoricTypeCoin> lst = new ArrayList<HistoricTypeCoin>();
        while (cursor.moveToNext())
            lst.add(populateUser(cursor));
        cursor.close();
        return lst;
    }

    public boolean removeUser(int id){
        return getDatabase().delete(TABLE_NAME, "_id = ?", new String[]{ Integer.toString(id) }) > 0;
    }
}
