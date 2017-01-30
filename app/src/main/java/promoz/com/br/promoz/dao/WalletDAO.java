package promoz.com.br.promoz.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import promoz.com.br.promoz.dao.db.MySQLiteDatabase;
import promoz.com.br.promoz.dao.db.PromozContract;
import promoz.com.br.promoz.model.Wallet;

/**
 * Created by vallux on 25/01/17.
 */

public class WalletDAO extends PromozContract.Wallet {

    private MySQLiteDatabase myDatabaseHelper;
    private SQLiteDatabase database;

    public WalletDAO(Context context) {
        this.myDatabaseHelper = new MySQLiteDatabase(context);
    }

    private SQLiteDatabase getDatabase(){
        if (database == null){
            database = myDatabaseHelper.getWritableDatabase();
        }
        return database;
    }

    private Wallet populate(Cursor cursor){ // Popula o objeto "Wallet" com os dados do cursor
        Wallet model = new Wallet(
                cursor.getInt(cursor.getColumnIndex(_ID)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT_COIN))
        );
        return model;
    }

    public long save(Wallet hist){ // salva os campos do objeto na tabela - atualiza ou cria um novo caso não exista

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, hist.getUserId());
        values.put(COLUMN_AMOUNT_COIN, hist.getAmountCoin());

        if(hist.get_id() != null){
            return getDatabase().update(TABLE_NAME, values, "_id = ?", new String[]{ hist.get_id().toString() });
        }
        return getDatabase().insert(TABLE_NAME, null, values);
    }

    public List<Wallet> list(){
        Cursor cursor = getDatabase().query(TABLE_NAME, allFields, null, null, null, null, null);

//        cursor.moveToFirst();
        List<Wallet> lst = new ArrayList<Wallet>();
        while (cursor.moveToNext())
            lst.add(populate(cursor));
        cursor.close();
        return lst;
    }

    public boolean remove(int id){
        return getDatabase().delete(TABLE_NAME, "_id = ?", new String[]{ Integer.toString(id) }) > 0;
    }
}
