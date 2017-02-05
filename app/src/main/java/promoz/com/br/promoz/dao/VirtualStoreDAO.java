package promoz.com.br.promoz.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import promoz.com.br.promoz.dao.db.AppDatabase;
import promoz.com.br.promoz.dao.db.PromozContract;
import promoz.com.br.promoz.model.VirtualStore;

/**
 * Created by vallux on 26/01/17.
 */

public class VirtualStoreDAO extends PromozContract.VirtualStore {

    private AppDatabase dbHelper;
    private SQLiteDatabase database;

    public VirtualStoreDAO(Context context) {
        dbHelper = new AppDatabase(context);
        database = dbHelper.getDatabase();
    }

    private VirtualStore populate(Cursor cursor){ // Popula o objeto "VirtualStore" com os dados do cursor
        VirtualStore model = new VirtualStore(
                cursor.getInt(cursor.getColumnIndex(_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_VRT_STR_TITLE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_VRT_STR_INFO)),
                cursor.getBlob(cursor.getColumnIndex(COLUMN_VRT_STR_IMG)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_VRT_STR_PRICE)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_VRT_STR_IND_VALID))
        );
        return model;
    }

    public long save(VirtualStore virtualStore){ // salva os campos do objeto na tabela - atualiza ou cria um novo caso não exista

        ContentValues values = new ContentValues();
        values.put(_ID, virtualStore.get_id());
        values.put(COLUMN_VRT_STR_TITLE, virtualStore.getTitle());
        values.put(COLUMN_VRT_STR_INFO, virtualStore.getInformation());
        values.put(COLUMN_VRT_STR_IMG, virtualStore.getImg());
        values.put(COLUMN_VRT_STR_PRICE, virtualStore.getPrice());
        values.put(COLUMN_VRT_STR_IND_VALID, virtualStore.getValid());

        if(virtualStore.get_id() != null){
            return database.update(TABLE_NAME, values, "_id = ?", new String[]{ virtualStore.get_id().toString() });
        }
        return database.insert(TABLE_NAME, null, values);
    }

    public List<VirtualStore> list(){
        Cursor cursor = database.query(TABLE_NAME, allFields, null, null, null, null, null);

//        cursor.moveToFirst();
        List<VirtualStore> lst = new ArrayList<VirtualStore>();
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
