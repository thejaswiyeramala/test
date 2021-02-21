package com.wf.ibs.bootappsecure.repository;


import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

public class KycRepositoryCustomImpl implements KycRepositoryCustom{

	// special bean exposed by JPA for custom DB interaction
		@SuppressWarnings("unused")
		@Autowired
		private EntityManager entityManager;
		
		/*@Override
		public List<Kyc> veryComplexBusinessLogicReq(String email) {
			// entityManager.
			return null;
		}*/
}
