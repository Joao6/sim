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
        dataSource.setDataSourceName("ddm7qrf01u54vl");
        dataSource.setServerName("ec2-174-129-227-116.compute-1.amazonaws.com");
        dataSource.setPortNumber(5432);
        dataSource.setDatabaseName("ddm7qrf01u54vl");
        dataSource.setUser("nxthvyvusuvadw");
        dataSource.setPassword("37b8527854169b4f6760a0b91d7c8c726ac7433cc7406175dd9038769eae1e2c");
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
