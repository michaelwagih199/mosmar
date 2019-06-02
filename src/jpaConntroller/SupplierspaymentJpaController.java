/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaConntroller;

import entities.Supplierspayment;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpaConntroller.exceptions.NonexistentEntityException;
import jpaConntroller.exceptions.PreexistingEntityException;

/**
 *
 * @author OM EL NOUR
 */
public class SupplierspaymentJpaController implements Serializable {

    public SupplierspaymentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Supplierspayment supplierspayment) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(supplierspayment);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSupplierspayment(supplierspayment.getSuppliersPaymentId()) != null) {
                throw new PreexistingEntityException("Supplierspayment " + supplierspayment + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Supplierspayment supplierspayment) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            supplierspayment = em.merge(supplierspayment);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = supplierspayment.getSuppliersPaymentId();
                if (findSupplierspayment(id) == null) {
                    throw new NonexistentEntityException("The supplierspayment with id " + id + " no longer exists.");
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
            Supplierspayment supplierspayment;
            try {
                supplierspayment = em.getReference(Supplierspayment.class, id);
                supplierspayment.getSuppliersPaymentId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The supplierspayment with id " + id + " no longer exists.", enfe);
            }
            em.remove(supplierspayment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Supplierspayment> findSupplierspaymentEntities() {
        return findSupplierspaymentEntities(true, -1, -1);
    }

    public List<Supplierspayment> findSupplierspaymentEntities(int maxResults, int firstResult) {
        return findSupplierspaymentEntities(false, maxResults, firstResult);
    }

    private List<Supplierspayment> findSupplierspaymentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Supplierspayment.class));
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

    public Supplierspayment findSupplierspayment(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Supplierspayment.class, id);
        } finally {
            em.close();
        }
    }

    public int getSupplierspaymentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Supplierspayment> rt = cq.from(Supplierspayment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
