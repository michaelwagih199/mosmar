/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaConntroller;

import entities.OrderPayment;
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
public class OrderPaymentJpaController implements Serializable {

    public OrderPaymentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrderPayment orderPayment) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(orderPayment);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrderPayment(orderPayment.getOrderPaymentId()) != null) {
                throw new PreexistingEntityException("OrderPayment " + orderPayment + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrderPayment orderPayment) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            orderPayment = em.merge(orderPayment);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orderPayment.getOrderPaymentId();
                if (findOrderPayment(id) == null) {
                    throw new NonexistentEntityException("The orderPayment with id " + id + " no longer exists.");
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
            OrderPayment orderPayment;
            try {
                orderPayment = em.getReference(OrderPayment.class, id);
                orderPayment.getOrderPaymentId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderPayment with id " + id + " no longer exists.", enfe);
            }
            em.remove(orderPayment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrderPayment> findOrderPaymentEntities() {
        return findOrderPaymentEntities(true, -1, -1);
    }

    public List<OrderPayment> findOrderPaymentEntities(int maxResults, int firstResult) {
        return findOrderPaymentEntities(false, maxResults, firstResult);
    }

    private List<OrderPayment> findOrderPaymentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrderPayment.class));
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

    public OrderPayment findOrderPayment(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrderPayment.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrderPaymentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrderPayment> rt = cq.from(OrderPayment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
