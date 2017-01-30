package promoz.com.br.promoz.model;

import java.io.Serializable;

/**
 * Created by vallux on 26/01/17.
 */

public class HistoricCoin implements Serializable {

    private Integer _id;
    private Integer walletId;
    private Integer historicTypeId;
    private String historicDateOperation;
    private Integer amountCoin;


    public HistoricCoin(Integer _id, Integer walletId, Integer historicTypeId, String historicDateOperation, Integer amountCoin) {
        this._id = _id;
        this.walletId = walletId;
        this.historicTypeId = historicTypeId;
        this.historicDateOperation = historicDateOperation;
        this.amountCoin = amountCoin;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public Integer getWalletId() {
        return walletId;
    }

    public void setWalletId(Integer walletId) {
        this.walletId = walletId;
    }

    public Integer getHistoricTypeId() {
        return historicTypeId;
    }

    public void setHistoricTypeId(Integer historicTypeId) {
        this.historicTypeId = historicTypeId;
    }

    public String getHistoricDateOperation() {
        return historicDateOperation;
    }

    public void setHistoricDateOperation(String historicDateOperation) {
        this.historicDateOperation = historicDateOperation;
    }

    public Integer getAmountCoin() {
        return amountCoin;
    }

    public void setAmountCoin(Integer amountCoin) {
        this.amountCoin = amountCoin;
    }

    @Override
    public String toString() {
        return this.walletId + " " + this.historicTypeId + " " + this.historicDateOperation + " " + this.getAmountCoin();
    }
}
