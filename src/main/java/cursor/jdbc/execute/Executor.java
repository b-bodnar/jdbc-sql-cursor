package cursor.jdbc.execute;

import cursor.jdbc.model.Account;
import cursor.jdbc.util.DbUtil;

public class Executor {

    public static void execute(){

        Account account = new Account("Bohdan", "Bodnar", "Lviv", "Male", "username100");

        DbUtil.insert(account);

        Account accountById = (Account) DbUtil.findById(105, Account.class);
        System.out.println(accountById);

        accountById.setFirstName("Tom");
        DbUtil.update(accountById.getId(), accountById);
        System.out.println(DbUtil.findById(accountById.getId(), Account.class));

        DbUtil.delete(500, Account.class);
    }
}
