/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaConntroller;

import entities.Productsnumber;
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
public class ProductsnumberJpaController implements Serializable {

    public ProductsnumberJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Productsnumber productsnumber) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(productsnumber);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productsnumber productsnumber) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            productsnumber = em.merge(productsnumber);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = productsnumber.getProductnumberid();
                if (findProductsnumber(id) == null) {
                    throw new NonexistentEntityException("The productsnumber with id " + id + " no longer exists.");
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
            Productsnumber productsnumber;
            try {
                productsnumber = em.getReference(Productsnumber.class, id);
                productsnumber.getProductnumberid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productsnumber with id " + id + " no longer exists.", enfe);
            }
            em.remove(productsnumber);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Productsnumber> findProductsnumberEntities() {
        return findProductsnumberEntities(true, -1, -1);
    }

    public List<Productsnumber> findProductsnumberEntities(int maxResults, int firstResult) {
        return findProductsnumberEntities(false, maxResults, firstResult);
    }

    private List<Productsnumber> findProductsnumberEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productsnumber.class));
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

    public Productsnumber findProductsnumber(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productsnumber.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductsnumberCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productsnumber> rt = cq.from(Productsnumber.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
