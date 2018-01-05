package com.nf.DAO;

import com.nf.model.NFDailyRetentionModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lushenghuang on 20/12/17.
 */
public interface NFDailyRetentionDAO extends JpaRepository<NFDailyRetentionModel,Integer>
{
}
