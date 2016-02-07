package dao.hibernate;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.spi.Stoppable;

import java.sql.Connection;
import java.sql.SQLException;

public class PostgresMultiTenantConnectionProvider implements MultiTenantConnectionProvider, Stoppable {

    private HikariDataSource dataSource;

    public PostgresMultiTenantConnectionProvider() {
        createDataSource();
    }

    private void createDataSource() {
        Configuration config = new Configuration();
        config.configure();
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(config.getProperty("hibernate.hikari.dataSource.url"));
        dataSource.setMinimumIdle(Integer.valueOf(config.getProperty("hibernate.hikari.minimumPoolSize")));
        dataSource.setMaximumPoolSize(Integer.valueOf(config.getProperty("hibernate.hikari.maximumPoolSize")));
        dataSource.setIdleTimeout(Long.valueOf(config.getProperty("hibernate.hikari.idleTimeout")));
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) {
        final Connection connection;
        try {
            connection = getAnyConnection();
            connection.createStatement().execute("SET search_path TO " + tenantIdentifier);
        } catch (SQLException ex) {
            throw new HibernateException("Could not alter JDBC connection to specified schema [" +
                    tenantIdentifier + "]", ex);
        }
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        try {
            connection.createStatement().execute("SET search_path TO public");
        } catch (SQLException ex) {
            // on error, throw an exception to make sure the connection is not returned to the pool.
            // your requirements may differ
            throw new HibernateException("Could not alter JDBC connection to specified schema [" +
                            tenantIdentifier + "]", ex);
        }
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }


    @Override
    public void stop() {}

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }
}
