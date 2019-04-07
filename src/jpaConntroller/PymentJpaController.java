/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaConntroller;

import entities.Pyment;
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
public class PymentJpaController implements Serializable {

    public PymentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pyment pyment) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(pyment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pyment pyment) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            pyment = em.merge(pyment);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pyment.getPaymentId();
                if (findPyment(id) == null) {
                    throw new NonexistentEntityException("The pyment with id " + id + " no longer exists.");
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
            Pyment pyment;
            try {
                pyment = em.getReference(Pyment.class, id);
                pyment.getPaymentId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pyment with id " + id + " no longer exists.", enfe);
            }
            em.remove(pyment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pyment> findPymentEntities() {
        return findPymentEntities(true, -1, -1);
    }

    public List<Pyment> findPymentEntities(int maxResults, int firstResult) {
        return findPymentEntities(false, maxResults, firstResult);
    }

    private List<Pyment> findPymentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pyment.class));
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

    public Pyment findPyment(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pyment.class, id);
        } finally {
            em.close();
        }
    }

    public int getPymentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pyment> rt = cq.from(Pyment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
