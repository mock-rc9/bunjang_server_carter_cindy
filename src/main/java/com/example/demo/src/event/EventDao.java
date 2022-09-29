package com.example.demo.src.event;

import com.example.demo.src.event.model.GetEventRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class EventDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public GetEventRes getEvent(int eventIdx) {
        String checkEventQuery = "select * from Event where eventIdx=? and eventStatus='active'";
        int geteventParams= eventIdx;
        return this.jdbcTemplate.queryForObject(checkEventQuery,
                (rs,rowNum)->new GetEventRes(
                        rs.getString("eventTItle"),
                        rs.getString("eventContent"),
                        rs.getString("eventOpening"),
                        rs.getString("eventEnding"),
                        rs.getString("eventImgUrl")),geteventParams);
    }

    public List<GetEventRes> getEvents() {
        String checkEventsQuery = "select * from Event where eventStatus='active'";
        return this.jdbcTemplate.query(checkEventsQuery,
                (rs,rowNum)->new GetEventRes(
                        rs.getString("eventTItle"),
                        rs.getString("eventContent"),
                        rs.getString("eventOpening"),
                        rs.getString("eventEnding"),
                        rs.getString("eventImgUrl")));
    }
}
