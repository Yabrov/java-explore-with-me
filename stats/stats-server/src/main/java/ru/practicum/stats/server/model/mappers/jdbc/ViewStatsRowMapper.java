package ru.practicum.stats.server.model.mappers.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.practicum.stats.server.model.ViewStats;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ViewStatsRowMapper implements RowMapper<ViewStats> {

    @Override
    public ViewStats mapRow(ResultSet rs, int rowNum) throws SQLException {
        String app = rs.getString("app");
        String uri = rs.getString("uri");
        Integer hits = rs.getInt("hits");
        return new ViewStats(app, uri, hits);
    }
}
