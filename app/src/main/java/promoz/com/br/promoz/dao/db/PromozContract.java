package promoz.com.br.promoz.dao.db;

import android.provider.BaseColumns;

/**
 * Created by vallux on 23/01/17.
 */

public final class PromozContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private PromozContract(){}

    private static final String CREATE_STM = "CREATE TABLE ";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String BLOB_TYPE = " BLOB";
    private static final String PK_TYPE = " INTEGER NOT NULL PRIMARY KEY ASC AUTOINCREMENT";
    private static final String FK_TYPE = " FOREIGN KEY(";
    private static final String COMMA_SEP = ", ";
    private static final String END_STM = ")";

    public static class User implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_USER_PASSWORD = "user_password";
        public static final String COLUMN_USER_EMAIL = "user_email";
        public static final String COLUMN_USER_CPF = "user_cpf";
        public static final String COLUMN_USER_IMG = "user_img";


        public static final String SQL_CREATE_USER = CREATE_STM + User.TABLE_NAME + " (" +
                User._ID + PK_TYPE + COMMA_SEP +
                User.COLUMN_USER_NAME + TEXT_TYPE + COMMA_SEP +
                User.COLUMN_USER_PASSWORD + TEXT_TYPE + COMMA_SEP +
                User.COLUMN_USER_EMAIL + TEXT_TYPE + COMMA_SEP +
                User.COLUMN_USER_CPF + TEXT_TYPE + COMMA_SEP +
                User.COLUMN_USER_IMG + BLOB_TYPE + END_STM;

        public static final String allFields[] = {
                User._ID,
                User.COLUMN_USER_NAME,
                User.COLUMN_USER_PASSWORD,
                User.COLUMN_USER_EMAIL,
                User.COLUMN_USER_CPF,
                User.COLUMN_USER_IMG
        };
    }

    public static class Wallet implements BaseColumns {
        public static final String TABLE_NAME = "wallet";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_FK_USER_ID = "user_id)  REFERENCES " + User.TABLE_NAME + "("+User._ID+")";
        public static final String COLUMN_AMOUNT_COIN = "amount_coin";

        public static final String SQL_CREATE_WALLET = CREATE_STM + Wallet.TABLE_NAME + " (" +
                Wallet._ID + PK_TYPE + COMMA_SEP +
                Wallet.COLUMN_AMOUNT_COIN + INTEGER_TYPE + " DEFAULT 0" + COMMA_SEP +
                Wallet.COLUMN_USER_ID + INTEGER_TYPE + COMMA_SEP +
                FK_TYPE + Wallet.COLUMN_FK_USER_ID +
                END_STM;

        public static final String allFields[] = {
                Wallet._ID,
                Wallet.COLUMN_USER_ID,
                Wallet.COLUMN_AMOUNT_COIN
        };
    }

    public static class Coupon implements BaseColumns {
        public static final String TABLE_NAME = "coupon";
        public static final String COLUMN_WALLET_ID = "wallet_id";
        public static final String COLUMN_FK_WALLET_ID = "wallet_id)  REFERENCES " + Wallet.TABLE_NAME + "("+Wallet._ID+")";
        public static final String COLUMN_CPN_TITLE = "cpn_title";
        public static final String COLUMN_CPN_SUBTITLE = "cpn_subtitle";
        public static final String COLUMN_CPN_IMG = "cpn_img";
        public static final String COLUMN_CPN_INFO = "cpn_info";
        public static final String COLUMN_CPN_DT_USE = "cpn_dt_use";
        public static final String COLUMN_CPN_DT_EXP = "cpn_dt_exp";
        public static final String COLUMN_CPN_IND_VALID = "cpn_ind_valid";
        public static final String COLUMN_CPN_PRICE = "cpn_price";

        public static final String SQL_CREATE_COUPON = CREATE_STM + Coupon.TABLE_NAME + " (" +
                Coupon._ID + PK_TYPE + COMMA_SEP +
                Coupon.COLUMN_WALLET_ID + INTEGER_TYPE + COMMA_SEP +
                Coupon.COLUMN_CPN_TITLE + TEXT_TYPE + COMMA_SEP +
                Coupon.COLUMN_CPN_SUBTITLE + TEXT_TYPE + COMMA_SEP +
                Coupon.COLUMN_CPN_IMG + BLOB_TYPE + COMMA_SEP +
                Coupon.COLUMN_CPN_INFO + TEXT_TYPE + COMMA_SEP +
                Coupon.COLUMN_CPN_DT_USE + TEXT_TYPE + COMMA_SEP +
                Coupon.COLUMN_CPN_DT_EXP + TEXT_TYPE + COMMA_SEP +
                Coupon.COLUMN_CPN_PRICE + INTEGER_TYPE + COMMA_SEP +
                Coupon.COLUMN_CPN_IND_VALID + INTEGER_TYPE + COMMA_SEP +
                FK_TYPE + Coupon.COLUMN_FK_WALLET_ID +
                END_STM;

        public static final String allFields[] = {
                Coupon._ID,
                Coupon.COLUMN_WALLET_ID,
                Coupon.COLUMN_CPN_TITLE,
                Coupon.COLUMN_CPN_SUBTITLE,
                Coupon.COLUMN_CPN_IMG,
                Coupon.COLUMN_CPN_INFO,
                Coupon.COLUMN_CPN_DT_USE,
                Coupon.COLUMN_CPN_DT_EXP,
                Coupon.COLUMN_CPN_PRICE,
                Coupon.COLUMN_CPN_IND_VALID
        };
    }

    public static class VirtualStore implements BaseColumns {
        public static final String TABLE_NAME = "virtual_store";
        public static final String COLUMN_VRT_STR_TITLE = "vrt_str_title";
        public static final String COLUMN_VRT_STR_INFO = "vrt_str_info";
        public static final String COLUMN_VRT_STR_IMG = "vrt_str_img";
        public static final String COLUMN_VRT_STR_PRICE = "vrt_str_price";
        public static final String COLUMN_VRT_STR_IND_VALID = "vrt_str_ind_valid";

        private static final String SQL_CREATE_VIRTUAL_STORE = CREATE_STM + VirtualStore.TABLE_NAME + " (" +
                VirtualStore._ID + PK_TYPE + COMMA_SEP +
                VirtualStore.COLUMN_VRT_STR_TITLE + TEXT_TYPE + COMMA_SEP +
                VirtualStore.COLUMN_VRT_STR_INFO + TEXT_TYPE + COMMA_SEP +
                VirtualStore.COLUMN_VRT_STR_IMG + BLOB_TYPE + COMMA_SEP +
                VirtualStore.COLUMN_VRT_STR_PRICE + INTEGER_TYPE + COMMA_SEP +
                VirtualStore.COLUMN_VRT_STR_IND_VALID + INTEGER_TYPE + END_STM;

        public static final String allFields[] = {
                VirtualStore._ID,
                VirtualStore.COLUMN_VRT_STR_TITLE,
                VirtualStore.COLUMN_VRT_STR_INFO,
                VirtualStore.COLUMN_VRT_STR_IMG,
                VirtualStore.COLUMN_VRT_STR_PRICE,
                VirtualStore.COLUMN_VRT_STR_IND_VALID
        };
    }

    public static class HistoricCoin implements BaseColumns {
        public static final String TABLE_NAME = "historic_coin";
        public static final String COLUMN_WALLET_ID = "wallet_id";
        public static final String COLUMN_FK_WALLET_ID = "wallet_id)  REFERENCES " + Wallet.TABLE_NAME + "("+Wallet._ID+")";
        public static final String COLUMN_HST_TP_ID = "hst_tp_id";
        public static final String COLUMN_FK_HST_TP_ID = "hst_tp_id)  REFERENCES " + HistoricTypeCoin.TABLE_NAME + "("+HistoricTypeCoin._ID+")";
        public static final String COLUMN_HST_DT_OPER = "hst_dt_oper";
        public static final String COLUMN_AMOUNT_COIN = "amount_coin";

        public static final String SQL_CREATE_HISTORIC_COIN = CREATE_STM + HistoricCoin.TABLE_NAME + " (" +
                HistoricCoin._ID + PK_TYPE + COMMA_SEP +
                HistoricCoin.COLUMN_HST_DT_OPER + TEXT_TYPE + COMMA_SEP +
                HistoricCoin.COLUMN_AMOUNT_COIN + INTEGER_TYPE + COMMA_SEP +
                HistoricCoin.COLUMN_WALLET_ID + INTEGER_TYPE + COMMA_SEP +
                HistoricCoin.COLUMN_HST_TP_ID + INTEGER_TYPE + COMMA_SEP +
                FK_TYPE + HistoricCoin.COLUMN_FK_WALLET_ID + COMMA_SEP +
                FK_TYPE + HistoricCoin.COLUMN_FK_HST_TP_ID +
                END_STM;

        public static final String allFields[] = {
                HistoricCoin._ID,
                HistoricCoin.COLUMN_WALLET_ID,
                HistoricCoin.COLUMN_HST_TP_ID,
                HistoricCoin.COLUMN_HST_DT_OPER,
                HistoricCoin.COLUMN_AMOUNT_COIN
        };
    }

    public static class HistoricTypeCoin implements BaseColumns{
        public static final String TABLE_NAME = "historic_type_coin";
        public static final String COLUMN_HST_TP_DESC = "hst_tp_desc";

        public static final String SQL_CREATE_HISTORIC_TYPE_COIN = CREATE_STM + HistoricTypeCoin.TABLE_NAME + " (" +
                HistoricTypeCoin._ID + PK_TYPE + COMMA_SEP +
                HistoricTypeCoin.COLUMN_HST_TP_DESC + TEXT_TYPE + END_STM;

        public static final String allFields[] = {
                HistoricTypeCoin._ID,
                HistoricTypeCoin.COLUMN_HST_TP_DESC
        };
    }

    public static class populateBasicTables {
        private static final String INSERT_STM = "INSERT INTO ";
        private static final String VALUE_TABLE_HISTORIC_TYPE_COIN = INSERT_STM + HistoricTypeCoin.TABLE_NAME + " (" + HistoricTypeCoin.COLUMN_HST_TP_DESC +
                ") VALUES('Moeda Verde')";

        private static final String VALUE_TABLE_USER = INSERT_STM + User.TABLE_NAME + " (" + User.COLUMN_USER_NAME +
                ", "+ User.COLUMN_USER_PASSWORD + " ," + User.COLUMN_USER_EMAIL+ ") VALUES('promoz','','promoz@promoz.com.br')";

        private static final String VALUE_TABLE_COUPON_CENTAURO = INSERT_STM + Coupon.TABLE_NAME + " (" + Coupon.COLUMN_CPN_TITLE +
                ", " + Coupon.COLUMN_CPN_SUBTITLE + ", " + Coupon.COLUMN_CPN_INFO + ", " + Coupon.COLUMN_CPN_DT_EXP +
                ", " + Coupon.COLUMN_CPN_PRICE + ", " + Coupon.COLUMN_CPN_IND_VALID +
                ") VALUES('Mês do Fitness','R$50,00 de desconto em compras acima de R$200,00','Neste mês de Fevereiro, a " +
                "Centauro traz para você promoções imperdíveis: Toda linha fitness com até 50% de desconto.','28/02/2017',10, 1)";

        private static final String VALUE_TABLE_COUPON_CIA = INSERT_STM + Coupon.TABLE_NAME + " (" + Coupon.COLUMN_CPN_TITLE +
                ", " + Coupon.COLUMN_CPN_SUBTITLE + ", " + Coupon.COLUMN_CPN_INFO + ", " + Coupon.COLUMN_CPN_DT_EXP +
                ", " + Coupon.COLUMN_CPN_PRICE + ", " + Coupon.COLUMN_CPN_IND_VALID +
                ") VALUES('Abuse e use no mês do carnaval','R$60,00 de desconto em compras acima de R$300,00','Nos mêses de " +
                "Fevereiro e Março, a C&A está abusando com preços imperdíveis: Toda a loja com até 50% de desconto.','28/02/2017',10, 1)";

        private static  final String TRIGER_USER_WALLET = "CREATE TRIGGER trigger_user_wallet " +
                " AFTER INSERT" + " ON " + User.TABLE_NAME + " BEGIN " + INSERT_STM + Wallet.TABLE_NAME +
                " (" + Wallet.COLUMN_USER_ID + ") VALUES (" + " last_insert_rowid() " + ");" + "END;";
    }

    public static final String valuesToPopulate[] = {populateBasicTables.VALUE_TABLE_HISTORIC_TYPE_COIN, populateBasicTables.VALUE_TABLE_USER, populateBasicTables.VALUE_TABLE_COUPON_CENTAURO, populateBasicTables.VALUE_TABLE_COUPON_CIA};
    public static final String tablesCreationList[] = {User.SQL_CREATE_USER,Wallet.SQL_CREATE_WALLET,HistoricTypeCoin.SQL_CREATE_HISTORIC_TYPE_COIN, HistoricCoin.SQL_CREATE_HISTORIC_COIN,VirtualStore.SQL_CREATE_VIRTUAL_STORE, Coupon.SQL_CREATE_COUPON, populateBasicTables.TRIGER_USER_WALLET};
}
