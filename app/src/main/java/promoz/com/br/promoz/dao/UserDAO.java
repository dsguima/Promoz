package promoz.com.br.promoz.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import promoz.com.br.promoz.R;
import promoz.com.br.promoz.dao.db.AppDatabase;
import promoz.com.br.promoz.dao.db.PromozContract;
import promoz.com.br.promoz.model.User;
import promoz.com.br.promoz.util.Message;
import promoz.com.br.promoz.util.Util;

/**
 * Created by vallux on 25/01/17.
 *
 * Classe para manipulação de dados da tabela USER
 */

public class UserDAO extends PromozContract.User {

    private AppDatabase dbHelper;
    private SQLiteDatabase database;
    private Context context;
    private Cursor cursor;

    public UserDAO(Context context) {
        this.context = context;
        dbHelper = new AppDatabase(context);
        database = dbHelper.getDatabase();
    }

    private User populate(){ // Popula o objeto "User" com os dados do cursor
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

    /**
     * Save or update User
     * @param user
     * @return long Util.Constants.ERROR_BD if error
     */
    public long save(User user){ // salva os campos do objeto na tabela - atualiza ou cria um novo caso não exista

        long result = Util.Constants.ERROR_BD;

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getNome());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_CPF, user.getCpf());
        values.put(COLUMN_USER_IMG, user.getImg());

        try {
            if(user.get_id() != null)
                result = database.update(TABLE_NAME, values, "_id = ?", new String[]{ user.get_id().toString()});
            else
                result = database.insert(TABLE_NAME, null, values);
        } catch (Exception ex){
            Message.msgErrorDB(context, context.getString(R.string.tag_error_db), context.getString(R.string.error_save_or_update), ex);
        }

        return result;
    }

    //TODO never used
    /*public Integer getIdByEmail(String email){
        Cursor cursor = database.query(TABLE_NAME,new String[]{_ID},COLUMN_USER_EMAIL+"=?",new String[]{email},null,null,null);

        return cursor.getInt(cursor.getColumnIndex(_ID));
    }*/

    public User userById(Integer userId){
        User result = null;

        try {
            cursor = database.query(TABLE_NAME, allFields, _ID + " = ?", new String[]{userId.toString()}, null, null, null);
            if(cursor.moveToFirst())
                result = populate();
        } catch (Exception ex){
            Message.msgErrorDB(context, context.getString(R.string.tag_error_db), context.getString(R.string.error_funny_db), ex);
        } finally {
            cursor.close();
        }

        return result;
    }

    //TODO never used
    /*public List<User> list(){
        List<User> lst = null;
        try {
            cursor = database.query(TABLE_NAME, allFields, null, null, null, null, null);
            lst = new ArrayList<User>();
            if(cursor.moveToFirst())
                do{
                    lst.add(populate());
                } while (cursor.moveToNext());
        } catch (Exception ex){
            Message.msgErrorDB(context, context.getString(R.string.tag_error_db), context.getString(R.string.error_funny_db), ex);
        } finally {
            close();
        }

        return lst;
    }*/

    /**
     * Método para buscar usuário pelo login
     * @param user login do usuário
     * @return User
     */
    public User findUserByLogin(String user){
        User result = null;

        String selection = COLUMN_USER_NAME + " = ?";
        String[] selectionArgs = { String.valueOf(user.trim())};

        try {
            cursor = database.query(TABLE_NAME, allFields, selection, selectionArgs, null, null, null);

            if(cursor.getCount() == 1) {
                cursor.moveToFirst();
                result = populate();
            }
        } catch (Exception ex){
            Message.msgErrorDB(context, context.getString(R.string.tag_error_db), context.getString(R.string.error_funny_db), ex);
        } finally {
            cursor.close();
        }

        return result;
    }

    public void closeDataBase(){
        if(database.isOpen()) database.close();
    }

    //TODO never used
   /* public void closeDatabase(){
        dbHelper.closeConnection();
        if(database.isOpen())
            database.close();
    }*/

    //TODO never used
    /*public boolean remove(int id){
        return database.delete(TABLE_NAME, "_id = ?", new String[]{ Integer.toString(id) }) > 0;
    }*/
}
