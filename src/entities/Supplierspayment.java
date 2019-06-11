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
@Table(name = "supplierspayment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Supplierspayment.findAll", query = "SELECT s FROM Supplierspayment s")
    , @NamedQuery(name = "Supplierspayment.findBySuppliersPaymentId", query = "SELECT s FROM Supplierspayment s WHERE s.suppliersPaymentId = :suppliersPaymentId")
    , @NamedQuery(name = "Supplierspayment.findBySuppliersId", query = "SELECT s FROM Supplierspayment s WHERE s.suppliersId = :suppliersId")
    , @NamedQuery(name = "Supplierspayment.findByPaymentDate", query = "SELECT s FROM Supplierspayment s WHERE s.paymentDate = :paymentDate")
    , @NamedQuery(name = "Supplierspayment.findByPaymentValue", query = "SELECT s FROM Supplierspayment s WHERE s.paymentValue = :paymentValue")})
public class Supplierspayment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SuppliersPaymentId")
    private Integer suppliersPaymentId;
    @Column(name = "SuppliersId")
    private Integer suppliersId;
    @Column(name = "paymentDate")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
    @Lob
    @Column(name = "notes")
    private String notes;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "paymentValue")
    private Float paymentValue;

    public Supplierspayment() {
    }

    public Supplierspayment(Integer suppliersPaymentId) {
        this.suppliersPaymentId = suppliersPaymentId;
    }

    public Integer getSuppliersPaymentId() {
        return suppliersPaymentId;
    }

    public void setSuppliersPaymentId(Integer suppliersPaymentId) {
        this.suppliersPaymentId = suppliersPaymentId;
    }

    public Integer getSuppliersId() {
        return suppliersId;
    }

    public void setSuppliersId(Integer suppliersId) {
        this.suppliersId = suppliersId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Float getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(Float paymentValue) {
        this.paymentValue = paymentValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (suppliersPaymentId != null ? suppliersPaymentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Supplierspayment)) {
            return false;
        }
        Supplierspayment other = (Supplierspayment) object;
        if ((this.suppliersPaymentId == null && other.suppliersPaymentId != null) || (this.suppliersPaymentId != null && !this.suppliersPaymentId.equals(other.suppliersPaymentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Supplierspayment[ suppliersPaymentId=" + suppliersPaymentId + " ]";
    }
    
}
