/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaConntroller;

import entities.Productmappping;
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
public class ProductmapppingJpaController implements Serializable {

    public ProductmapppingJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Productmappping productmappping) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(productmappping);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProductmappping(productmappping.getProductMappingId()) != null) {
                throw new PreexistingEntityException("Productmappping " + productmappping + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productmappping productmappping) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            productmappping = em.merge(productmappping);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = productmappping.getProductMappingId();
                if (findProductmappping(id) == null) {
                    throw new NonexistentEntityException("The productmappping with id " + id + " no longer exists.");
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
            Productmappping productmappping;
            try {
                productmappping = em.getReference(Productmappping.class, id);
                productmappping.getProductMappingId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productmappping with id " + id + " no longer exists.", enfe);
            }
            em.remove(productmappping);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Productmappping> findProductmapppingEntities() {
        return findProductmapppingEntities(true, -1, -1);
    }

    public List<Productmappping> findProductmapppingEntities(int maxResults, int firstResult) {
        return findProductmapppingEntities(false, maxResults, firstResult);
    }

    private List<Productmappping> findProductmapppingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productmappping.class));
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

    public Productmappping findProductmappping(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productmappping.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductmapppingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productmappping> rt = cq.from(Productmappping.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
