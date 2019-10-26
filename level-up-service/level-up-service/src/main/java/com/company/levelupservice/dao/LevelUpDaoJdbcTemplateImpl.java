package com.company.levelupservice.dao;

import com.company.levelupservice.dto.LevelUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

@Repository
public class LevelUpDaoJdbcTemplateImpl implements LevelUpDao{

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public LevelUpDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    //prepared statements
    private static final String INSERT_LEVEL_UP =
            "insert into level_up (customer_id,points,member_date) values (?,?,?)";
    private static final String SELECT_LEVEL_UP =
            "select * from level_up where level_up_id = ?";
    private static final String SELECT_ALL_LEVEL_UPS =
            "select * from level_up";
    private static final String UPDATE_LEVEL_UP =
            "update level_up set customer_id=?, points=?, member_date=? where level_up_id=?";
    private static final String DELETE_LEVEL_UP =
            "delete from level_up where level_up_id = ?";
    private static final String SELECT_LEVEL_UP_BY_CUSTOMER_ID =
            "select * from level_up where customer_id = ?";


    //Row mapper function
    public LevelUp mapRowToLevelUp(ResultSet rs, int rowNum) throws SQLException {
        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(rs.getInt("level_up_id"));
        levelUp.setCustomerId(rs.getInt("customer_id"));
        levelUp.setPoints(rs.getInt("points"));
        levelUp.setMemberDate(rs.getDate("member_date").toLocalDate());
        return levelUp;
    }

    @Override
    @Transactional
    public LevelUp addLevelUp(LevelUp levelUp) {
        jdbcTemplate.update(INSERT_LEVEL_UP,
                levelUp.getCustomerId(),
                levelUp.getPoints(),
                levelUp.getMemberDate()
        );
        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        levelUp.setLevelUpId(id);
        return levelUp;
    }

    @Override
    public LevelUp getLevelUpById(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_LEVEL_UP, this::mapRowToLevelUp, id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<LevelUp> getAllLevelUps() {
        return jdbcTemplate.query(SELECT_ALL_LEVEL_UPS,this::mapRowToLevelUp);
    }

    @Override
    public LevelUp updateLevelUp(LevelUp levelUp) {
        jdbcTemplate.update(UPDATE_LEVEL_UP,
                levelUp.getCustomerId(),
                levelUp.getPoints(),
                levelUp.getMemberDate(),
                levelUp.getLevelUpId()
        );
        return levelUp;
    }

    @Override
    public void deleteLevelUp(int id) {
        jdbcTemplate.update(DELETE_LEVEL_UP,id);
    }

    @Override
    public int getLevelUpPointsByCustomerId(int id){
        //LevelUp levelUp = new LevelUp();
        List<LevelUp> levelUps = jdbcTemplate.query(SELECT_LEVEL_UP_BY_CUSTOMER_ID,this::mapRowToLevelUp,id);

        int totalPoints = 0;

        for(LevelUp levelUp : levelUps){
            totalPoints += levelUp.getPoints();
        }

        return totalPoints;
    }
}
