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
@Table(name = "retrievals")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Retrievals.findAll", query = "SELECT r FROM Retrievals r")
    , @NamedQuery(name = "Retrievals.findByRetrievalId", query = "SELECT r FROM Retrievals r WHERE r.retrievalId = :retrievalId")
    , @NamedQuery(name = "Retrievals.findByRetrievalDate", query = "SELECT r FROM Retrievals r WHERE r.retrievalDate = :retrievalDate")
    , @NamedQuery(name = "Retrievals.findByRetrievalTime", query = "SELECT r FROM Retrievals r WHERE r.retrievalTime = :retrievalTime")
    , @NamedQuery(name = "Retrievals.findByCustomerId", query = "SELECT r FROM Retrievals r WHERE r.customerId = :customerId")
    , @NamedQuery(name = "Retrievals.findByBillsValue", query = "SELECT r FROM Retrievals r WHERE r.billsValue = :billsValue")})
public class Retrievals implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "retrievalId")
    private Integer retrievalId;
    @Column(name = "retrievalDate")
    @Temporal(TemporalType.DATE)
    private Date retrievalDate;
    @Column(name = "retrievalTime")
    @Temporal(TemporalType.TIME)
    private Date retrievalTime;
    @Basic(optional = false)
    @Column(name = "customer_id")
    private int customerId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "billsValue")
    private Float billsValue;
    @Lob
    @Column(name = "UUID")
    private String uuid;

    public Retrievals() {
    }

    public Retrievals(Integer retrievalId) {
        this.retrievalId = retrievalId;
    }

    public Retrievals(Integer retrievalId, int customerId) {
        this.retrievalId = retrievalId;
        this.customerId = customerId;
    }

    public Integer getRetrievalId() {
        return retrievalId;
    }

    public void setRetrievalId(Integer retrievalId) {
        this.retrievalId = retrievalId;
    }

    public Date getRetrievalDate() {
        return retrievalDate;
    }

    public void setRetrievalDate(Date retrievalDate) {
        this.retrievalDate = retrievalDate;
    }

    public Date getRetrievalTime() {
        return retrievalTime;
    }

    public void setRetrievalTime(Date retrievalTime) {
        this.retrievalTime = retrievalTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Float getBillsValue() {
        return billsValue;
    }

    public void setBillsValue(Float billsValue) {
        this.billsValue = billsValue;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (retrievalId != null ? retrievalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Retrievals)) {
            return false;
        }
        Retrievals other = (Retrievals) object;
        if ((this.retrievalId == null && other.retrievalId != null) || (this.retrievalId != null && !this.retrievalId.equals(other.retrievalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Retrievals[ retrievalId=" + retrievalId + " ]";
    }
    
}
