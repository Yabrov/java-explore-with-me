package ru.practicum.stats.server.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.server.model.EndpointHit;
import ru.practicum.stats.server.model.ViewStats;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;

@Slf4j
@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatsHistoryRepositoryImpl implements StatsHistoryRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ViewStats> viewStatsRowMapper;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String INSERT_ENDPOINT_HIT_SQL_STRING = "" +
            "INSERT INTO public.stats_history(app, uri, ip, real_time) " +
            "VALUES(?, ?, ?, CAST(? AS TIMESTAMP(0)))";

    private static final String FIND_STATS_IN_PERIOD_SQL_STRING = "" +
            "SELECT h.app, h.uri, COUNT(h.*) AS hits " +
            "FROM public.stats_history h " +
            "WHERE h.real_time BETWEEN CAST(? AS TIMESTAMP(0)) AND CAST(? AS TIMESTAMP(0)) " +
            "GROUP BY h.app, h.uri ORDER BY hits DESC";

    private static final String FIND_STATS_IN_PERIOD_WITH_UNIQ_IPS_SQL_STRING = "" +
            "SELECT h.app, h.uri, COUNT(DISTINCT(h.ip)) AS hits " +
            "FROM public.stats_history h " +
            "WHERE h.real_time BETWEEN CAST(? AS TIMESTAMP(0)) AND CAST(? AS TIMESTAMP(0)) " +
            "GROUP BY h.app, h.uri ORDER BY hits DESC";

    private static final String FIND_STATS_IN_PERIOD_BY_URIS_SQL_STRING = "" +
            "SELECT h.app, h.uri, COUNT(h.*) AS hits " +
            "FROM public.stats_history h " +
            "WHERE h.real_time BETWEEN CAST('%s' AS TIMESTAMP(0)) AND CAST('%s' AS TIMESTAMP(0)) AND h.uri IN (%s) " +
            "GROUP BY h.app, h.uri ORDER BY hits DESC";

    private static final String FIND_STATS_IN_PERIOD_BY_URIS_WITH_UNIQ_IPS_SQL_STRING = "" +
            "SELECT h.app, h.uri, COUNT(DISTINCT(h.ip)) AS hits " +
            "FROM public.stats_history h " +
            "WHERE h.real_time BETWEEN CAST('%s' AS TIMESTAMP(0)) AND CAST('%s' AS TIMESTAMP(0)) AND h.uri IN (%s) " +
            "GROUP BY h.app, h.uri ORDER BY hits DESC";

    private static final String INSERT_ERROR_MES = "Error when inserting new hit";

    private static final String SELECT_ERROR_MES = "Error when select stats";

    @Transactional
    @Override
    public EndpointHit saveStat(EndpointHit endpointHit) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection
                        .prepareStatement(INSERT_ENDPOINT_HIT_SQL_STRING, new String[]{"id"});
                stmt.setString(1, endpointHit.getApp());
                stmt.setString(2, endpointHit.getUri());
                stmt.setString(3, endpointHit.getIp());
                stmt.setString(4, endpointHit.getTimestamp().format(formatter));
                return stmt;
            }, keyHolder);
            return new EndpointHit(
                    keyHolder.getKey().intValue(),
                    endpointHit.getApp(),
                    endpointHit.getUri(),
                    endpointHit.getIp(),
                    endpointHit.getTimestamp());
        } catch (Exception e) {
            log.error(INSERT_ERROR_MES, e);
            throw e;
        }
    }

    @Override
    public Collection<ViewStats> getStatsInPeriod(LocalDateTime from, LocalDateTime to) {
        try {
            return jdbcTemplate.query(
                    FIND_STATS_IN_PERIOD_SQL_STRING,
                    viewStatsRowMapper,
                    from.format(formatter),
                    to.format(formatter));
        } catch (Exception e) {
            log.error(SELECT_ERROR_MES, e);
            throw e;
        }
    }

    @Override
    public Collection<ViewStats> getStatsInPeriodWithUniqIps(LocalDateTime from, LocalDateTime to) {
        try {
            return jdbcTemplate.query(
                    FIND_STATS_IN_PERIOD_WITH_UNIQ_IPS_SQL_STRING,
                    viewStatsRowMapper,
                    from.format(formatter),
                    to.format(formatter));
        } catch (Exception e) {
            log.error(SELECT_ERROR_MES, e);
            throw e;
        }
    }

    @Override
    public Collection<ViewStats> getStatInPeriodByUris(Collection<String> uris, LocalDateTime from, LocalDateTime to) {
        try {
            String inSql = String.join(",", Collections.nCopies(uris.size(), "?"));
            return jdbcTemplate.query(
                    String.format(
                            FIND_STATS_IN_PERIOD_BY_URIS_SQL_STRING,
                            from.format(formatter),
                            to.format(formatter),
                            inSql
                    ),
                    uris.toArray(),
                    viewStatsRowMapper);
        } catch (Exception e) {
            log.error(SELECT_ERROR_MES, e);
            throw e;
        }
    }

    @Override
    public Collection<ViewStats> getStatInPeriodByUrisWithUniqIps(Collection<String> uris, LocalDateTime from, LocalDateTime to) {
        try {
            String inSql = String.join(",", Collections.nCopies(uris.size(), "?"));
            return jdbcTemplate.query(
                    String.format(
                            FIND_STATS_IN_PERIOD_BY_URIS_WITH_UNIQ_IPS_SQL_STRING,
                            from.format(formatter),
                            to.format(formatter),
                            inSql
                    ),
                    uris.toArray(),
                    viewStatsRowMapper);
        } catch (Exception e) {
            log.error(SELECT_ERROR_MES, e);
            throw e;
        }
    }
}
