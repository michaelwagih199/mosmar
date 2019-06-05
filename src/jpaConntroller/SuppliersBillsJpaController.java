
package jpaConntroller;

import entities.SuppliersBills;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpaConntroller.exceptions.NonexistentEntityException;


public class SuppliersBillsJpaController implements Serializable {

    public SuppliersBillsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SuppliersBills suppliersBills) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(suppliersBills);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SuppliersBills suppliersBills) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            suppliersBills = em.merge(suppliersBills);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = suppliersBills.getSuppliersBilsId();
                if (findSuppliersBills(id) == null) {
                    throw new NonexistentEntityException("The suppliersBills with id " + id + " no longer exists.");
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
            SuppliersBills suppliersBills;
            try {
                suppliersBills = em.getReference(SuppliersBills.class, id);
                suppliersBills.getSuppliersBilsId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suppliersBills with id " + id + " no longer exists.", enfe);
            }
            em.remove(suppliersBills);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SuppliersBills> findSuppliersBillsEntities() {
        return findSuppliersBillsEntities(true, -1, -1);
    }

    public List<SuppliersBills> findSuppliersBillsEntities(int maxResults, int firstResult) {
        return findSuppliersBillsEntities(false, maxResults, firstResult);
    }

    private List<SuppliersBills> findSuppliersBillsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SuppliersBills.class));
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

    public SuppliersBills findSuppliersBills(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SuppliersBills.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuppliersBillsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SuppliersBills> rt = cq.from(SuppliersBills.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
