package com.progetto.flyway;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testConnessioneAlDatabase() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            assertNotNull(conn, "Connessione attiva");
            System.out.println(" Connessione riuscita!");
        }
    }
}
