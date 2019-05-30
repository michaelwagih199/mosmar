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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "customers_payment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomersPayment.findAll", query = "SELECT c FROM CustomersPayment c")
    , @NamedQuery(name = "CustomersPayment.findByCustomersPaymentId", query = "SELECT c FROM CustomersPayment c WHERE c.customersPaymentId = :customersPaymentId")
    , @NamedQuery(name = "CustomersPayment.findByCustomerId", query = "SELECT c FROM CustomersPayment c WHERE c.customerId = :customerId")
    , @NamedQuery(name = "CustomersPayment.findByPaymentDate", query = "SELECT c FROM CustomersPayment c WHERE c.paymentDate = :paymentDate")
    , @NamedQuery(name = "CustomersPayment.findByPaymentValue", query = "SELECT c FROM CustomersPayment c WHERE c.paymentValue = :paymentValue")})
public class CustomersPayment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "customersPaymentId")
    private Integer customersPaymentId;
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "paymentDate")
    @Temporal(TemporalType.TIME)
    private Date paymentDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "paymentValue")
    private Float paymentValue;
    @Lob
    @Column(name = "notes")
    private String notes;

    public CustomersPayment() {
    }

    public CustomersPayment(Integer customersPaymentId) {
        this.customersPaymentId = customersPaymentId;
    }

    public Integer getCustomersPaymentId() {
        return customersPaymentId;
    }

    public void setCustomersPaymentId(Integer customersPaymentId) {
        this.customersPaymentId = customersPaymentId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Float getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(Float paymentValue) {
        this.paymentValue = paymentValue;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customersPaymentId != null ? customersPaymentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomersPayment)) {
            return false;
        }
        CustomersPayment other = (CustomersPayment) object;
        if ((this.customersPaymentId == null && other.customersPaymentId != null) || (this.customersPaymentId != null && !this.customersPaymentId.equals(other.customersPaymentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.CustomersPayment[ customersPaymentId=" + customersPaymentId + " ]";
    }
    
}
