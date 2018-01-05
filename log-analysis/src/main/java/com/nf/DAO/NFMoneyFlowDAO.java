package com.nf.DAO;

import com.nf.model.NFMoneyFlowModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lushenghuang on 20/12/17.
 */
public interface NFMoneyFlowDAO extends JpaRepository<NFMoneyFlowModel,Integer>
{
}
