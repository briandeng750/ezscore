package org.lccgymnastics.ezscore.api;

import javax.persistence.EntityManager;

public interface UserTransaction {
	public void start();
	public void commit();
	public void close();
	public EntityManager getEntityManager();
}
