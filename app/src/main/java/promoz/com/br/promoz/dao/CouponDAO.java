package promoz.com.br.promoz.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import promoz.com.br.promoz.dao.db.MySQLiteDatabase;
import promoz.com.br.promoz.dao.db.PromozContract;
import promoz.com.br.promoz.model.Coupon;

/**
 * Created by vallux on 27/01/17.
 */

public class CouponDAO extends PromozContract.Coupon {

    private MySQLiteDatabase myDatabaseHelper;
    private SQLiteDatabase database;

    public CouponDAO(Context context) {
        this.myDatabaseHelper = new MySQLiteDatabase(context);
    }

    private SQLiteDatabase getDatabase(){
        if (database == null){
            database = myDatabaseHelper.getWritableDatabase();
        }
        return database;
    }

    private Coupon populate(Cursor cursor){ // Popula o objeto "Coupon" com os dados do cursor
        Coupon model = new Coupon(
                cursor.getInt(cursor.getColumnIndex(_ID)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_WALLET_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_CPN_TITLE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_CPN_SUBTITLE)),
                cursor.getBlob(cursor.getColumnIndex(COLUMN_CPN_IMG)),
                cursor.getString(cursor.getColumnIndex(COLUMN_CPN_INFO)),
                cursor.getString(cursor.getColumnIndex(COLUMN_CPN_DT_USE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_CPN_DT_EXP)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_CPN_PRICE)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_CPN_IND_VALID))
        );
        return model;
    }

    public long save(Coupon coupon){ // salva os campos do objeto na tabela - atualiza ou cria um novo caso não exista

        ContentValues values = new ContentValues();
        values.put(COLUMN_WALLET_ID, coupon.getWalletId());
        values.put(COLUMN_CPN_TITLE, coupon.getTitle());
        values.put(COLUMN_CPN_SUBTITLE, coupon.getSubTitle());
        values.put(COLUMN_CPN_IMG, coupon.getImg());
        values.put(COLUMN_CPN_INFO, coupon.getInfo());
        values.put(COLUMN_CPN_DT_USE, coupon.getDateUse());
        values.put(COLUMN_CPN_DT_EXP, coupon.getDateExp());
        values.put(COLUMN_CPN_PRICE, coupon.getPrice());
        values.put(COLUMN_CPN_IND_VALID, coupon.getValid());

        if(coupon.get_id() != null){
            return getDatabase().update(TABLE_NAME, values, "_id = ?", new String[]{ coupon.get_id().toString() });
        }
        return getDatabase().insert(TABLE_NAME, null, values);
    }

    public List<Coupon> list(){
        Cursor cursor = getDatabase().query(TABLE_NAME, allFields, null, null, null, null, null);

//        cursor.moveToFirst();
        List<Coupon> lst = new ArrayList<Coupon>();
        while (cursor.moveToNext())
            lst.add(populate(cursor));
        cursor.close();
        return lst;
    }

    public boolean remove(int id){
        return getDatabase().delete(TABLE_NAME, "_id = ?", new String[]{ Integer.toString(id) }) > 0;
    }
}
