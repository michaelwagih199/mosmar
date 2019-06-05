
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


@Entity
@Table(name = "suppliers_bills")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SuppliersBills.findAll", query = "SELECT s FROM SuppliersBills s")
    , @NamedQuery(name = "SuppliersBills.findBySuppliersBilsId", query = "SELECT s FROM SuppliersBills s WHERE s.suppliersBilsId = :suppliersBilsId")
    , @NamedQuery(name = "SuppliersBills.findBySuppliersId", query = "SELECT s FROM SuppliersBills s WHERE s.suppliersId = :suppliersId")
    , @NamedQuery(name = "SuppliersBills.findByBilsDate", query = "SELECT s FROM SuppliersBills s WHERE s.bilsDate = :bilsDate")
    , @NamedQuery(name = "SuppliersBills.findByBilsTotal", query = "SELECT s FROM SuppliersBills s WHERE s.bilsTotal = :bilsTotal")
    , @NamedQuery(name = "SuppliersBills.findByBilsPaid", query = "SELECT s FROM SuppliersBills s WHERE s.bilsPaid = :bilsPaid")
    , @NamedQuery(name = "SuppliersBills.findByBilsRemaining", query = "SELECT s FROM SuppliersBills s WHERE s.bilsRemaining = :bilsRemaining")})
public class SuppliersBills implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SuppliersBilsId")
    private Integer suppliersBilsId;
    @Column(name = "SuppliersId")
    private Integer suppliersId;
    @Column(name = "bilsDate")
    @Temporal(TemporalType.DATE)
    private Date bilsDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "bilsTotal")
    private Float bilsTotal;
    @Column(name = "bilsPaid")
    private Float bilsPaid;
    @Column(name = "bilsRemaining")
    private Float bilsRemaining;
    @Lob
    @Column(name = "Notes")
    private String notes;
    @Lob
    @Column(name = "UUID")
    private String uuid;

    public SuppliersBills() {
    }

    public SuppliersBills(Integer suppliersBilsId) {
        this.suppliersBilsId = suppliersBilsId;
    }

    public Integer getSuppliersBilsId() {
        return suppliersBilsId;
    }

    public void setSuppliersBilsId(Integer suppliersBilsId) {
        this.suppliersBilsId = suppliersBilsId;
    }

    public Integer getSuppliersId() {
        return suppliersId;
    }

    public void setSuppliersId(Integer suppliersId) {
        this.suppliersId = suppliersId;
    }

    public Date getBilsDate() {
        return bilsDate;
    }

    public void setBilsDate(Date bilsDate) {
        this.bilsDate = bilsDate;
    }

    public Float getBilsTotal() {
        return bilsTotal;
    }

    public void setBilsTotal(Float bilsTotal) {
        this.bilsTotal = bilsTotal;
    }

    public Float getBilsPaid() {
        return bilsPaid;
    }

    public void setBilsPaid(Float bilsPaid) {
        this.bilsPaid = bilsPaid;
    }

    public Float getBilsRemaining() {
        return bilsRemaining;
    }

    public void setBilsRemaining(Float bilsRemaining) {
        this.bilsRemaining = bilsRemaining;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
        hash += (suppliersBilsId != null ? suppliersBilsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuppliersBills)) {
            return false;
        }
        SuppliersBills other = (SuppliersBills) object;
        if ((this.suppliersBilsId == null && other.suppliersBilsId != null) || (this.suppliersBilsId != null && !this.suppliersBilsId.equals(other.suppliersBilsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SuppliersBills[ suppliersBilsId=" + suppliersBilsId + " ]";
    }
    
}
