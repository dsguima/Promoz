package promoz.com.br.promoz.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import promoz.com.br.promoz.dao.db.AppDatabase;
import promoz.com.br.promoz.dao.db.PromozContract;
import promoz.com.br.promoz.model.HistoricCoin;

/**
 * Created by vallux on 26/01/17.
 */

public class HistoricCoinDAO extends PromozContract.HistoricCoin {

    private AppDatabase dbHelper;
    private SQLiteDatabase database;

    public HistoricCoinDAO(Context context) {
        dbHelper = new AppDatabase(context);
        database = dbHelper.getDatabase();
    }

    private HistoricCoin populate(Cursor cursor){ // Popula o objeto com os dados do cursor
        HistoricCoin model = new HistoricCoin(
                cursor.getInt(cursor.getColumnIndex(_ID)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_WALLET_ID)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_HST_TP_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_HST_DT_OPER)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT_COIN)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DESC_OPER))
        );

        return model;
    }

    public long save(HistoricCoin historic){ // salva os campos do objeto na tabela - atualiza ou cria um novo caso não exista

        ContentValues values = new ContentValues();
        values.put(COLUMN_WALLET_ID, historic.getWalletId());
        values.put(COLUMN_HST_TP_ID, historic.getHistoricTypeId());
        values.put(COLUMN_HST_DT_OPER, historic.getHistoricDateOperation());
        values.put(COLUMN_AMOUNT_COIN, historic.getAmountCoin());
        values.put(COLUMN_DESC_OPER, historic.getOperationDescription());

        if(historic.get_id() != null){
            return database.update(TABLE_NAME, values, "_id = ?", new String[]{ historic.get_id().toString() });
        }
        return database.insert(TABLE_NAME, null, values);
    }

    public List<HistoricCoin> listByDate(Integer walletId, Integer daysBefore) {

        String selection =  COLUMN_WALLET_ID + "=? and " + COLUMN_HST_DT_OPER + " > date('now','"+daysBefore.toString()+" day')";
        String[] selectionArgs = { String.valueOf(walletId)};
        String orderBy = COLUMN_HST_DT_OPER + " DESC";

        Cursor cursor = database.query(TABLE_NAME, allFields, selection, selectionArgs, null, null, orderBy);

        List<HistoricCoin> lst = new ArrayList<HistoricCoin>();

        if(cursor.moveToFirst())
            do {
                lst.add(populate(cursor));
            }while (cursor.moveToNext());

        cursor.close();
        return lst;
    }

    public List<HistoricCoin> listById(Integer walletId){

        String selection = COLUMN_WALLET_ID + " = ?";
        String[] selectionArgs = { String.valueOf(walletId)};
        String orderBy = COLUMN_HST_DT_OPER + " DESC";

        Cursor cursor = database.query(TABLE_NAME, allFields, selection, selectionArgs, null, null, orderBy);

        List<HistoricCoin> lst = new ArrayList<HistoricCoin>();

        if(cursor.moveToFirst())
            do {
                lst.add(populate(cursor));
            }while (cursor.moveToNext());

        cursor.close();
        return lst;
    }

    public List<HistoricCoin> list(){
        Cursor cursor = database.query(TABLE_NAME, allFields, null, null, null, null, null);

        List<HistoricCoin> lst = new ArrayList<HistoricCoin>();
        if(cursor.moveToFirst())
            do {
                lst.add(populate(cursor));
            }while (cursor.moveToNext());

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
