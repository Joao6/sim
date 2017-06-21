package com.sim.model;

import java.sql.Connection;
import org.postgresql.ds.PGPoolingDataSource;

/**
 *
 * @author Joao Pedro
 */
public class ConnectionManager {

    private PGPoolingDataSource dataSource;

    public Connection getConnection() throws Exception {
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        return conn;
    }

//Inicio Singleton
    private ConnectionManager() {
        dataSource = new PGPoolingDataSource();
        dataSource.setDataSourceName("ddqvsiite4n1na");
        dataSource.setServerName("ec2-23-21-220-152.compute-1.amazonaws.com");
        dataSource.setPortNumber(5432);
        dataSource.setDatabaseName("ddqvsiite4n1na");
        dataSource.setUser("leycxmeboxjhtk");
        dataSource.setPassword("ca2bfa57369e3bfbf5bcdb739db97342c2cac54cc3de6b5da770d7b1d5de1613");
        dataSource.setMaxConnections(30);
        dataSource.setInitialConnections(10);
    }

    private static ConnectionManager instance;

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }
    //Fim Singleton
}
