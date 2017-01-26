package promoz.com.br.promoz.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import promoz.com.br.promoz.dao.db.MySQLiteDatabase;
import promoz.com.br.promoz.dao.db.PromozContract;
import promoz.com.br.promoz.model.User;

/**
 * Created by vallux on 25/01/17.
 *
 * Classe para manipulação de dados da tabela USER
 */

public class userDAO extends PromozContract.User {

    private MySQLiteDatabase myDatabaseHelper;
    private SQLiteDatabase database;

    public userDAO(Context context) {
        this.myDatabaseHelper = new MySQLiteDatabase(context);
    }

    private SQLiteDatabase getDatabase(){
        if (database == null){
            database = myDatabaseHelper.getWritableDatabase();
        }
        return database;
    }

    private User populate(Cursor cursor){ // Popula o objeto "User" com os dados do cursor
        User model = new User(
                cursor.getInt(cursor.getColumnIndex(_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)),
                cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)),
                cursor.getString(cursor.getColumnIndex(COLUMN_USER_CPF)),
                cursor.getBlob(cursor.getColumnIndex(COLUMN_USER_IMG))
        );
        return model;
    }

    public long save(User user){ // salva os campos do objeto na tabela - atualiza ou cria um novo caso não exista

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getNome());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_CPF, user.getCpf());
        values.put(COLUMN_USER_IMG, user.getImg());

        if(user.get_id() != null){
            return getDatabase().update(TABLE_NAME, values, "_id = ?", new String[]{ user.get_id().toString() });
        }
        return getDatabase().insert(TABLE_NAME, null, values);
    }

    public List<User> list(){
        Cursor cursor = getDatabase().query(TABLE_NAME, allFields, null, null, null, null, null);

//        cursor.moveToFirst();
        List<User> lst = new ArrayList<User>();
        while (cursor.moveToNext())
            lst.add(populate(cursor));
        cursor.close();
        return lst;
    }

    public boolean remove(int id){
        return getDatabase().delete(TABLE_NAME, "_id = ?", new String[]{ Integer.toString(id) }) > 0;
    }
}
