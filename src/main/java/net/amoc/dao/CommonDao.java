package net.amoc.dao;

import net.amoc.command.TemperatureAvg;
import net.amoc.command.WeatherAvg;
import net.amoc.domain.common.Station;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Atiqur Rahman
 * @since Jun 1, 2013
 */
@SuppressWarnings({"unchecked"})
@Repository
@Transactional
public class CommonDao {
    private static final Logger log = Logger.getLogger(CommonDao.class);

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Autowired
    protected JdbcTemplate jdbcTemplate;
    private List<WeatherAvg> weatherAvg;

    public List<Station> getAllStation() {
        return hibernateTemplate.find("FROM Station s");
    }

    public List<WeatherAvg> getWeatherAvg(String stationId) {
        String sql = "select year, avg(rain_fall) * 100 as avg_rain from weather where station_id = ? and rain_fall != -99.9 group by year order by year;";
        List<Object> params = new ArrayList<Object>();

        params.add(stationId);
        List<WeatherAvg> lst = jdbcTemplate.query(sql, params.toArray(),
                new RowMapper() {
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        WeatherAvg bd = new WeatherAvg();
                        bd.setAvgRainFall(rs.getDouble("avg_rain"));
                        bd.setYear(rs.getString("year"));
                        return bd;
                    }
                });
        return lst;
    }

    public List<TemperatureAvg> getTemAvg(String stationId) {
        String sql = "select year, avg(max_temperature) as avg_max, avg(min_temperature) as avg_min from weather where station_id = ? and (max_temperature != -99.9 or min_temperature != -99.9) group by year order by year";
        List<Object> params = new ArrayList<Object>();

        params.add(stationId);
        List<TemperatureAvg> lst = jdbcTemplate.query(sql, params.toArray(),
                new RowMapper() {
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        TemperatureAvg bd = new TemperatureAvg();
                        bd.setAvgMax(rs.getDouble("avg_max"));
                        bd.setAvgMin(rs.getDouble("avg_min"));
                        bd.setYear(rs.getString("year"));
                        return bd;
                    }
                });
        return lst;
    }
}