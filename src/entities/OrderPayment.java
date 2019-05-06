/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OM EL NOUR
 */
@Entity
@Table(name = "order_payment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderPayment.findAll", query = "SELECT o FROM OrderPayment o")
    , @NamedQuery(name = "OrderPayment.findByOrderPaymentId", query = "SELECT o FROM OrderPayment o WHERE o.orderPaymentId = :orderPaymentId")
    , @NamedQuery(name = "OrderPayment.findByOrderId", query = "SELECT o FROM OrderPayment o WHERE o.orderId = :orderId")
    , @NamedQuery(name = "OrderPayment.findByTotslCost", query = "SELECT o FROM OrderPayment o WHERE o.totslCost = :totslCost")
    , @NamedQuery(name = "OrderPayment.findByPaid", query = "SELECT o FROM OrderPayment o WHERE o.paid = :paid")
    , @NamedQuery(name = "OrderPayment.findByRemaining", query = "SELECT o FROM OrderPayment o WHERE o.remaining = :remaining")
    , @NamedQuery(name = "OrderPayment.findByDate", query = "SELECT o FROM OrderPayment o WHERE o.date = :date")
    , @NamedQuery(name = "OrderPayment.findByOrderDiscount", query = "SELECT o FROM OrderPayment o WHERE o.orderDiscount = :orderDiscount")})
public class OrderPayment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "order_payment_id")
    private Integer orderPaymentId;
    @Column(name = "order_id")
    private Integer orderId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "totsl_cost")
    private Float totslCost;
    @Column(name = "paid")
    private Float paid;
    @Column(name = "remaining")
    private Float remaining;
    @Lob
    @Column(name = "notes")
    private String notes;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date =new Date();
    @Column(name = "order_discount")
    private Float orderDiscount;

    public OrderPayment() {
    }

    public OrderPayment(Integer orderPaymentId) {
        this.orderPaymentId = orderPaymentId;
    }

    public Integer getOrderPaymentId() {
        return orderPaymentId;
    }

    public void setOrderPaymentId(Integer orderPaymentId) {
        this.orderPaymentId = orderPaymentId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Float getTotslCost() {
        return totslCost;
    }

    public void setTotslCost(Float totslCost) {
        this.totslCost = totslCost;
    }

    public Float getPaid() {
        return paid;
    }

    public void setPaid(Float paid) {
        this.paid = paid;
    }

    public Float getRemaining() {
        return remaining;
    }

    public void setRemaining(Float remaining) {
        this.remaining = remaining;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getOrderDiscount() {
        return orderDiscount;
    }

    public void setOrderDiscount(Float orderDiscount) {
        this.orderDiscount = orderDiscount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderPaymentId != null ? orderPaymentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderPayment)) {
            return false;
        }
        OrderPayment other = (OrderPayment) object;
        if ((this.orderPaymentId == null && other.orderPaymentId != null) || (this.orderPaymentId != null && !this.orderPaymentId.equals(other.orderPaymentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.OrderPayment[ orderPaymentId=" + orderPaymentId + " ]";
    }
    
}
