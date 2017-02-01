package promoz.com.br.promoz.dao;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import promoz.com.br.promoz.model.HistoricTypeCoin;

/**
 * Created by dsguima on 31/01/17.
 */

public class DatabaseDAO {

    private HistoricTypeCoinDAO typeCoinDAO;

    public DatabaseDAO(Context ctx) {
        typeCoinDAO = new HistoricTypeCoinDAO(ctx);
        populateHistoricTypeCoin();
        populateVirtualStore();
    }

    private void populateHistoricTypeCoin(){
        String TAG = "HistoricTypeCoin";

        Log.v(TAG,"populando registros HistoricTypeCoin...");
        HistoricTypeCoin typeCoin = new HistoricTypeCoin();
        typeCoin.setDescription("Moeda Verde");
        typeCoinDAO.save(typeCoin);
       // HistoricTypeCoin histCoin = new HistoricTypeCoin();
       // histCoin.setDescription("Moeda Verde");
       // HistoricTypeCoinDAO coinDAO = new HistoricTypeCoinDAO();
    }

    private void populateVirtualStore(){

        //TODO insere itens na tabela VirtualStore

    }
}
