package com.wf.ibs.bootappsecure.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wf.ibs.bootappsecure.entity.Kyc;

@Repository
public interface KycRepository extends JpaRepository<Kyc, Long>, KycRepositoryCustom{

	/*List<Kyc> findByEmail(String email);
	List<Kyc> findByBasicPayGreaterThan(Double basicSalary);
	
	@Query("select e from Employee e where e.basicPay >:pay and e.email=:email")
	List<Kyc> findBySomeComplexQuery(@Param("pay") Double basicPay, @Param("email")String email);*/
}
