package com.epam.esm.dao.connection;

import javax.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {
    private final static String URL = DbPropertyManager.getProperty("url");
    private final static String USER = DbPropertyManager.getProperty("user");
    private final static String PASSWORD = DbPropertyManager.getProperty("password");
    private final static int POOL_SIZE = Integer.parseInt(DbPropertyManager.getProperty("poolSize"));
    private LinkedBlockingQueue<ProxyConnection> connectionQueue;
    private List<ProxyConnection> usedConnections = new ArrayList<>();

    private ConnectionPool(int poolSize) {
        connectionQueue = new LinkedBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            connectionQueue.offer(createConnection());
        }
    }
    /**
     * @return new ProxyConnection
     */
    private ProxyConnection createConnection() {
        ProxyConnection proxyConnection = null;
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            proxyConnection = new ProxyConnection(connection);
        } catch (SQLException e) {
//            LOGGER.error(e.getMessage(), e);
        }
        return proxyConnection;
    }
    /**
     * @return lazy instance of Connection pool
     */
    public static ConnectionPool getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ConnectionPool INSTANCE = new ConnectionPool(POOL_SIZE);
    }

    /**
     * @return ProxyConnection from pool
     */
    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
            usedConnections.add(connection);
        } catch (InterruptedException e) {
//            throw new DaoException(e.getMessage(), e);
        }
        return connection;
    }

    /**
     * @param connection return connection to pool
     */
    /*package private*/ void releaseConnection(ProxyConnection connection) {
        try {
            boolean contains = usedConnections.contains(connection);
            if (contains) {
                connectionQueue.put(connection);
                usedConnections.remove(connection);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (InterruptedException e) {
//            LOGGER.error(e.getMessage(), e);
            connectionQueue.offer(createConnection());
        }
    }

    /**
     * close all connections in pool
     */
    @PreDestroy
    public void closeAll() {
        connectionQueue.forEach(ProxyConnection::doClose);
    }
}
