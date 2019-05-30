/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaConntroller;

import entities.CustomersPayment;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpaConntroller.exceptions.NonexistentEntityException;

/**
 *
 * @author OM EL NOUR
 */
public class CustomersPaymentJpaController implements Serializable {

    public CustomersPaymentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CustomersPayment customersPayment) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(customersPayment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CustomersPayment customersPayment) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            customersPayment = em.merge(customersPayment);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = customersPayment.getCustomersPaymentId();
                if (findCustomersPayment(id) == null) {
                    throw new NonexistentEntityException("The customersPayment with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CustomersPayment customersPayment;
            try {
                customersPayment = em.getReference(CustomersPayment.class, id);
                customersPayment.getCustomersPaymentId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customersPayment with id " + id + " no longer exists.", enfe);
            }
            em.remove(customersPayment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CustomersPayment> findCustomersPaymentEntities() {
        return findCustomersPaymentEntities(true, -1, -1);
    }

    public List<CustomersPayment> findCustomersPaymentEntities(int maxResults, int firstResult) {
        return findCustomersPaymentEntities(false, maxResults, firstResult);
    }

    private List<CustomersPayment> findCustomersPaymentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CustomersPayment.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CustomersPayment findCustomersPayment(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CustomersPayment.class, id);
        } finally {
            em.close();
        }
    }

    public int getCustomersPaymentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CustomersPayment> rt = cq.from(CustomersPayment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
