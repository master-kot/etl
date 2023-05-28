package com.nikolay.etl.daos;

import com.nikolay.etl.entities.BusinessData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessDataRepository extends JpaRepository<BusinessData, Long>,
        JpaSpecificationExecutor<BusinessData> {
}
