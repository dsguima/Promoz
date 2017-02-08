package promoz.com.br.promoz.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import promoz.com.br.promoz.dao.db.AppDatabase;
import promoz.com.br.promoz.dao.db.PromozContract;
import promoz.com.br.promoz.model.Wallet;

/**
 * Created by vallux on 25/01/17.
 */

public class WalletDAO extends PromozContract.Wallet {

    private AppDatabase dbHelper;
    private SQLiteDatabase database;

    public WalletDAO(Context context) {
        dbHelper = new AppDatabase(context);
        database = dbHelper.getDatabase();
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
            return database.update(TABLE_NAME, values, "_id = ?", new String[]{ hist.get_id().toString() });
        }
        return database.insert(TABLE_NAME, null, values);
    }

    public Wallet walletById(Integer id){
        Cursor cursor = database.query(TABLE_NAME, allFields, _ID+"=?", new String[]{id.toString()}, null, null, null);

        Wallet wlt = new Wallet(null,null,null);
        if(cursor.moveToFirst())
            wlt = populate(cursor);
        cursor.close();

       // Log.e("WallwtDAO","===============" + wlt.getAmountCoin());

        return wlt;
    }

    public Integer getAmountByWalletId(Integer id){
        Cursor cursor = database.query(TABLE_NAME, new String[]{COLUMN_AMOUNT_COIN}, COLUMN_USER_ID+"=?", new String[]{id.toString()}, null, null, null);

        if(cursor.moveToFirst())
            return cursor.getInt(0);

        return 0;
    }

    public Integer walletIdByUserId(Integer id){
        Cursor cursor = database.query(TABLE_NAME, new String[]{_ID}, COLUMN_USER_ID+"=?", new String[]{id.toString()}, null, null, null);

        if(cursor.moveToFirst())
            return cursor.getInt(0);

        return 0;
    }

    public List<Wallet> list(){
        Cursor cursor = database.query(TABLE_NAME, allFields, null, null, null, null, null);

//        cursor.moveToFirst();
        List<Wallet> lst = new ArrayList<Wallet>();
        while (cursor.moveToNext())
            lst.add(populate(cursor));
        cursor.close();

        return lst;
    }

    public void closeDatabase(){
        database.close();
    }

    public boolean remove(int id){
        return database.delete(TABLE_NAME, "_id = ?", new String[]{ Integer.toString(id) }) > 0;
    }
}
