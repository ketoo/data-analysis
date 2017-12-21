package com.nf.DAO;

import com.nf.model.NFTaskFlowModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lushenghuang on 20/12/17.
 */
public interface NFTaskFlowDAO extends JpaRepository<NFTaskFlowModel,Integer>
{
}
