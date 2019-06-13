/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaConntroller;

import entities.Retrievals;
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
public class RetrievalsJpaController implements Serializable {

    public RetrievalsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Retrievals retrievals) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(retrievals);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRetrievals(retrievals.getRetrievalId()) != null) {
                throw new PreexistingEntityException("Retrievals " + retrievals + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Retrievals retrievals) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            retrievals = em.merge(retrievals);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = retrievals.getRetrievalId();
                if (findRetrievals(id) == null) {
                    throw new NonexistentEntityException("The retrievals with id " + id + " no longer exists.");
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
            Retrievals retrievals;
            try {
                retrievals = em.getReference(Retrievals.class, id);
                retrievals.getRetrievalId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The retrievals with id " + id + " no longer exists.", enfe);
            }
            em.remove(retrievals);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Retrievals> findRetrievalsEntities() {
        return findRetrievalsEntities(true, -1, -1);
    }

    public List<Retrievals> findRetrievalsEntities(int maxResults, int firstResult) {
        return findRetrievalsEntities(false, maxResults, firstResult);
    }

    private List<Retrievals> findRetrievalsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Retrievals.class));
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

    public Retrievals findRetrievals(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Retrievals.class, id);
        } finally {
            em.close();
        }
    }

    public int getRetrievalsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Retrievals> rt = cq.from(Retrievals.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
