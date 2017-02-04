package promoz.com.br.promoz.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import promoz.com.br.promoz.dao.db.AppDatabase;
import promoz.com.br.promoz.dao.db.DatabaseHelper;
import promoz.com.br.promoz.dao.db.PromozContract;
import promoz.com.br.promoz.model.Coupon;
import promoz.com.br.promoz.model.Wallet;

/**
 * Created by vallux on 27/01/17.
 */

public class CouponDAO extends PromozContract.Coupon {

    private AppDatabase dbHelper;
    private SQLiteDatabase database;

    public CouponDAO(Context context) {
        dbHelper = new AppDatabase(context);
        database = dbHelper.getDatabase();
    }

    private Coupon populate(Cursor cursor){ // Popula o objeto "Coupon" com os dados do cursor
        Coupon model = new Coupon(
                cursor.getInt(cursor.getColumnIndex(_ID)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_WALLET_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_CPN_TITLE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_CPN_SUBTITLE)),
                //cursor.getBlob(cursor.getColumnIndex(COLUMN_CPN_IMG)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_CPN_IMG)), // modificado apenas para protótipo
                cursor.getString(cursor.getColumnIndex(COLUMN_CPN_INFO)),
                cursor.getString(cursor.getColumnIndex(COLUMN_CPN_DT_USE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_CPN_DT_EXP)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_CPN_PRICE)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_CPN_IND_VALID)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_CPN_STR_ID))
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
            return database.update(TABLE_NAME, values, "_id = ?", new String[]{ coupon.get_id().toString() });
        }
        return database.insert(TABLE_NAME, null, values);
    }

    public Coupon couponById(Integer id){
        Cursor cursor = database.query(TABLE_NAME, allFields, _ID+"=?", new String[]{id.toString()}, null, null, null);

        Coupon cpn = new Coupon();
        if(cursor.moveToFirst())
            cpn = populate(cursor);

        cursor.close();

        return cpn;
    }

    public List<Coupon> list(String order){
        Cursor cursor = database.query(TABLE_NAME, allFields, null, null, null, null, order);

//        cursor.moveToFirst();
        List<Coupon> lst = new ArrayList<Coupon>();
        while (cursor.moveToNext())
            lst.add(populate(cursor));
        cursor.close();
        return lst;
    }

    public void closeDatabase(){
        if(database.isOpen())
            database.close();
    }

    public boolean remove(int id){
        return database.delete(TABLE_NAME, "_id = ?", new String[]{ Integer.toString(id) }) > 0;
    }
}
