package org.lccgymnastics.ezscore.api;

import javax.enterprise.inject.Default;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

@Singleton
@Named("userTransaction")
@Default
public class UserTransactionImpl implements UserTransaction {

	private EntityManager em;
	public UserTransactionImpl() {
	}
	@Override
	public void start() {
		em = EMF.createEntityManager();
		em.getTransaction().begin();
	}

	@Override
	public void commit() {
		em.getTransaction().commit();
	}

	@Override
	public void close() {
		if (em.isOpen()) {
			em.close();
		}
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}

}
