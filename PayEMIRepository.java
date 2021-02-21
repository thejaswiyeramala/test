package com.wf.ibs.bootappsecure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wf.ibs.bootappsecure.entity.PayEMI;


//@Component
@Repository
public interface PayEMIRepository 
		extends JpaRepository<PayEMI, Long>{
}
