package com.cbs.middleware.repository;

import com.cbs.middleware.domain.IsCalculateTemp;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the IsCalculateTemp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IsCalculateTempRepository extends JpaRepository<IsCalculateTemp, Long>, JpaSpecificationExecutor<IsCalculateTemp>

{

//    @Query(value = "delete from application_log where iss_file_parser_id=:id ",nativeQuery = true)
//    void deleteByIssFileParser(@Param("id") Long id);


@Query(value = "select * from is_calculate_temp where pacs_number=:pacsNumber and financial_year=:financialYear ", nativeQuery = true)
List<IsCalculateTemp> FindFromIscalculateTemp(@Param("pacsNumber") String pacsNumber, @Param("financialYear") String financialYear);
}
