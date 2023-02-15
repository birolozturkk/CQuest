package dev.crius.cquest.config;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SQL {

    public Driver driver = Driver.SQLITE;
    public String host = "localhost";
    public String database = "CQuest";
    public String username = "";
    public String password = "";
    public int port = 3306;
    public boolean useSSL = false;

    public SQL(Driver driver, String host, String database, String username, String password) {
        this.driver = driver;
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public enum Driver {
        MYSQL,
        POSTGRESQL,
        SQLITE;
    }
}
