package promoz.com.br.promoz.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import promoz.com.br.promoz.dao.db.AppDatabase;
import promoz.com.br.promoz.dao.db.PromozContract;
import promoz.com.br.promoz.model.User;

/**
 * Created by vallux on 25/01/17.
 *
 * Classe para manipulação de dados da tabela USER
 */

public class UserDAO extends PromozContract.User {

    private AppDatabase dbHelper;
    private SQLiteDatabase database;

    public UserDAO(Context context) {
        dbHelper = new AppDatabase(context);
        database = dbHelper.getDatabase();
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
            return database.update(TABLE_NAME, values, "_id = ?", new String[]{ user.get_id().toString() });
        }
        return database.insert(TABLE_NAME, null, values);
    }

    public Integer getIdByEmail(String email){
        Cursor cursor = database.query(TABLE_NAME,new String[]{_ID},COLUMN_USER_EMAIL+"=?",new String[]{email},null,null,null);

        return cursor.getInt(cursor.getColumnIndex(_ID));
    }

    public User userById(Integer userId){
        Cursor cursor = database.query(TABLE_NAME, allFields, _ID + " = ?", new String[]{userId.toString()}, null, null, null);
        if(cursor.moveToFirst())
            return populate(cursor);

        return null;
    }

    public List<User> list(){
        Cursor cursor = database.query(TABLE_NAME, allFields, null, null, null, null, null);

        List<User> lst = new ArrayList<User>();
        if(cursor.moveToFirst())
            do{
                lst.add(populate(cursor));
            }while (cursor.moveToNext());

        cursor.close();
        return lst;
    }

    /**
     * Método para buscar usuário pelo login
     * @param user login do usuário
     * @return User
     */
    public User findUserByLogin(String user){
        User result = null;

        //String selection = COLUMN_USER_EMAIL + " = ?";
        String selection = COLUMN_USER_NAME + " = ?";
        String[] selectionArgs = { String.valueOf(user.trim())};

        Cursor cursor = database.query(TABLE_NAME, allFields, selection, selectionArgs, null, null, null);

        if(cursor.getCount() == 1) {
            cursor.moveToFirst();
            result = populate(cursor);
        }

        return result;
    }


    public void closeDatabase(){
        dbHelper.closeConnection();
        if(database.isOpen())
            database.close();
    }

    public boolean remove(int id){
        return database.delete(TABLE_NAME, "_id = ?", new String[]{ Integer.toString(id) }) > 0;
    }
}
