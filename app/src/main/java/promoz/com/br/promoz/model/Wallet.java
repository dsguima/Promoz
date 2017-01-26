package promoz.com.br.promoz.model;

import java.io.Serializable;

/**
 * Created by vallux on 25/01/17.
 */

public class Wallet implements Serializable {

    private Integer _id;
    private Integer userId;
    private Integer amountCoin;

    public Wallet(Integer _id, Integer userId, Integer amountCoin) {
        this._id = _id;
        this.userId = userId;
        this.amountCoin = amountCoin;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAmountCoin() {
        return amountCoin;
    }

    public void setAmountCoin(Integer amountCoin) {
        this.amountCoin = amountCoin;
    }

    @Override
    public String toString() {
        return Integer.toString(this.userId);
    }
}
