package com.pluralsight.conference.repository;

import com.pluralsight.conference.model.Speaker;
import com.pluralsight.conference.repository.util.SpeakerRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository("speakerRepository")
public class SpeakerRepositoryImpl implements SpeakerRepository {

    private JdbcTemplate jdbcTemplate;

    public SpeakerRepositoryImpl(JdbcTemplate jdbc){
        this.jdbcTemplate=jdbc;
    }

    public List<Speaker> findAll() {
        // RowMapper<Speaker> rowMapper=(rs, rowNum)->{
        //     Speaker speaker=new Speaker();
        //     speaker.setId(rs.getInt("id"));
        //     speaker.setName(rs.getString("name"));
        //     return speaker;
        // };



        List<Speaker> speakers=jdbcTemplate.query("SELECT * FROM speaker", new SpeakerRowMapper());
        return speakers;
    }

    @Override
    public Speaker create(Speaker speaker) {

        //jdbcTemplate.update("INSERT INTO speaker (name) VALUES (?)",speaker.getName());
        
        // KeyHolder keyHolder=new GeneratedKeyHolder();
        // jdbcTemplate.update(new PreparedStatementCreator() {
        //     @Override
        //     public PreparedStatement createPreparedStatement(Connection conn) throws SQLException{
        //         PreparedStatement ps=conn.prepareStatement("INSERT INTO speaker (name) VALUES (?)", new String[] {"id"});
        //         ps.setString(1, speaker.getName());
        //         return ps;
        //     }
        // }, keyHolder);

        // Number id=keyHolder.getKey();


        //para hacerlo sin sql
        SimpleJdbcInsert insert=new SimpleJdbcInsert(jdbcTemplate);
        insert.setTableName("speaker");
        List<String> columns=new ArrayList<>();
        columns.add("name");

        Map<String, Object> data=new HashMap<>();
        data.put("name", speaker.getName());

        insert.setGeneratedKeyName("id");

        Number id=insert.executeAndReturnKey(data);



        return getSpeaker(id.intValue());
    }

    @Override
    public Speaker getSpeaker(int id) {
       return jdbcTemplate.queryForObject("SELECT * FROM speaker WHERE id=?", new SpeakerRowMapper(),id);
    }

    @Override
    public Speaker update(Speaker speaker) {
        jdbcTemplate.update("UPDATE speaker SET name=? WHERE id=?",speaker.getName(), speaker.getId());
        return speaker;
    }

    @Override
    public void update(List<Object[]> pairs) {
        jdbcTemplate.batchUpdate("UPDATE speaker SET skill=? WHERE id=?", pairs);
    }
    
}
