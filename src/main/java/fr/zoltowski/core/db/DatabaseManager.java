package fr.zoltowski.core.db;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;

import static fr.zoltowski.Program.LOGGER;

public class DatabaseManager {
    private final Properties properties;

    public DatabaseManager() throws IOException {
        this.properties = readProperties();
        initWithScript();
    }

    private Connection connect() throws SQLException {
        final String url = properties.getProperty("db.uri");
        final String user = properties.getProperty("db.username");
        final String password = properties.getProperty("db.password");
        LOGGER.info("connecting to " + url + " with user " + user + " and password ******");
        return DriverManager.getConnection(url, user, password);
    }

    private Properties readProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream("/application.properties"));
        return properties;
    }

    private void initWithScript() {
        InputStream inputStream = getClass().getResourceAsStream("/init.sql");

        if (inputStream == null) {
            LOGGER.warning("No init script found.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            try (Statement statement = getConnection().createStatement()) {
                String line;
                StringBuilder sql = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    sql.append(line).append("\n");
                }

                if (!sql.isEmpty()) {
                    statement.execute(sql.toString());
                }

                LOGGER.info("Script executed successfully.");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "initWithScript", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return connect();
    }
}
