package com.vk.restapiproxy.database;

import com.vk.restapiproxy.IntegrationEnvironment;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatabaseTest extends IntegrationEnvironment {

    @Test
    void databaseStructureTest() {
        try (Connection connection = POSTGRE_SQL_CONTAINER.createConnection("")) {
            //given
            Map<String, List<String>> expectedColumns = Map.of(
                    "\"user\"", List.of("id", "creation_time", "password", "username"),
                    "audit", List.of("id", "creation_time", "has_access", "request_method", "request_params", "user_id", "resource_type_id"),
                    "resource_type", List.of("id", "name"),
                    "role", List.of("id", "name"),
                    "user_role", List.of("user_id", "role_id")
            );

            for (Map.Entry<String, List<String>> stringListEntry : expectedColumns.entrySet()) {
                //when
                String key = stringListEntry.getKey();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM %s".formatted(key));
                ResultSet resultSet = preparedStatement.executeQuery();

                //then
                assertThat(resultSet.getMetaData().getColumnCount()).isEqualTo(expectedColumns.get(key).size());
                for (int i = 1; i <= expectedColumns.get(key).size(); i++) {
                    assertTrue(expectedColumns.get(key).contains(resultSet.getMetaData().getColumnName(i)));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
