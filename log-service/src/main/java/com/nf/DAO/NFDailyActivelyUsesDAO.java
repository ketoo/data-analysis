package com.nf.DAO;

import com.nf.model.NFDailyActivelyUsersModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lushenghuang on 20/12/17.
 */
public interface NFDailyActivelyUsesDAO extends JpaRepository<NFDailyActivelyUsersModel,Integer>
{
}
