/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaConntroller;

import entities.Retrivaldetails;
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
public class RetrivaldetailsJpaController implements Serializable {

    public RetrivaldetailsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Retrivaldetails retrivaldetails) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(retrivaldetails);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Retrivaldetails retrivaldetails) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            retrivaldetails = em.merge(retrivaldetails);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = retrivaldetails.getRetrivalDetailsId();
                if (findRetrivaldetails(id) == null) {
                    throw new NonexistentEntityException("The retrivaldetails with id " + id + " no longer exists.");
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
            Retrivaldetails retrivaldetails;
            try {
                retrivaldetails = em.getReference(Retrivaldetails.class, id);
                retrivaldetails.getRetrivalDetailsId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The retrivaldetails with id " + id + " no longer exists.", enfe);
            }
            em.remove(retrivaldetails);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Retrivaldetails> findRetrivaldetailsEntities() {
        return findRetrivaldetailsEntities(true, -1, -1);
    }

    public List<Retrivaldetails> findRetrivaldetailsEntities(int maxResults, int firstResult) {
        return findRetrivaldetailsEntities(false, maxResults, firstResult);
    }

    private List<Retrivaldetails> findRetrivaldetailsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Retrivaldetails.class));
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

    public Retrivaldetails findRetrivaldetails(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Retrivaldetails.class, id);
        } finally {
            em.close();
        }
    }

    public int getRetrivaldetailsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Retrivaldetails> rt = cq.from(Retrivaldetails.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
