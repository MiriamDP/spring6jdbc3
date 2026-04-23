package com.pluralsight.conference.repository;

import com.pluralsight.conference.model.Speaker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
        RowMapper<Speaker> rowMapper=(rs, rowNum)->{
            Speaker speaker=new Speaker();
            speaker.setId(rs.getInt("id"));
            speaker.setName(rs.getString("name"));
            return speaker;
        };

        List<Speaker> speakers=jdbcTemplate.query("SELECT * FROM speaker", rowMapper);
        return speakers;
    }

    @Override
    public Speaker create(Speaker speaker) {

        jdbcTemplate.update("INSERT INTO speaker (name) VALUES (?)",speaker.getName());

        //para hacerlo sin sql
        // SimpleJdbcInsert insert=new SimpleJdbcInsert(jdbcTemplate);
        // insert.setTableName("speaker");
        // List<String> columns=new ArrayList<>();
        // columns.add("name");

        // Map<String, Object> data=new HashMap<>();
        // data.put("name", speaker.getName());

        // insert.setGeneratedKeyName("id");

        // Number key=insert.executeAndReturnKey(data);

        // System.out.println(key);


        return null;
    }
    
}
