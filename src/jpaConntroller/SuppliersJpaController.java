/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaConntroller;

import entities.Suppliers;
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
public class SuppliersJpaController implements Serializable {

    public SuppliersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Suppliers suppliers) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(suppliers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Suppliers suppliers) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            suppliers = em.merge(suppliers);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = suppliers.getSupplierid();
                if (findSuppliers(id) == null) {
                    throw new NonexistentEntityException("The suppliers with id " + id + " no longer exists.");
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
            Suppliers suppliers;
            try {
                suppliers = em.getReference(Suppliers.class, id);
                suppliers.getSupplierid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suppliers with id " + id + " no longer exists.", enfe);
            }
            em.remove(suppliers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Suppliers> findSuppliersEntities() {
        return findSuppliersEntities(true, -1, -1);
    }

    public List<Suppliers> findSuppliersEntities(int maxResults, int firstResult) {
        return findSuppliersEntities(false, maxResults, firstResult);
    }

    private List<Suppliers> findSuppliersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Suppliers.class));
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

    public Suppliers findSuppliers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Suppliers.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuppliersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Suppliers> rt = cq.from(Suppliers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
