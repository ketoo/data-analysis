package com.nf.DAO;

import com.nf.model.NFDailyNewUsersModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lushenghuang on 20/12/17.
 */
public interface NFDailyNewUsersDAO extends JpaRepository<NFDailyNewUsersModel,Integer>
{
}
