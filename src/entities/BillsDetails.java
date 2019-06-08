/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OM EL NOUR
 */
@Entity
@Table(name = "bills_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BillsDetails.findAll", query = "SELECT b FROM BillsDetails b")
    , @NamedQuery(name = "BillsDetails.findByBilsDetailsId", query = "SELECT b FROM BillsDetails b WHERE b.bilsDetailsId = :bilsDetailsId")
    , @NamedQuery(name = "BillsDetails.findBySuppliersBilsId", query = "SELECT b FROM BillsDetails b WHERE b.suppliersBilsId = :suppliersBilsId")
    , @NamedQuery(name = "BillsDetails.findByProductId", query = "SELECT b FROM BillsDetails b WHERE b.productId = :productId")
    , @NamedQuery(name = "BillsDetails.findByQuantity", query = "SELECT b FROM BillsDetails b WHERE b.quantity = :quantity")
    , @NamedQuery(name = "BillsDetails.findByPrice", query = "SELECT b FROM BillsDetails b WHERE b.price = :price")
    , @NamedQuery(name = "BillsDetails.findByTotal", query = "SELECT b FROM BillsDetails b WHERE b.total = :total")
    , @NamedQuery(name = "BillsDetails.findByCategoryId", query = "SELECT b FROM BillsDetails b WHERE b.categoryId = :categoryId")})
public class BillsDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bilsDetailsId")
    private Integer bilsDetailsId;
    @Column(name = "SuppliersBilsId")
    private Integer suppliersBilsId;
    @Column(name = "productId")
    private Integer productId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "quantity")
    private Float quantity;
    @Column(name = "price")
    private Float price;
    @Column(name = "total")
    private Float total;
    @Column(name = "categoryId")
    private Integer categoryId;

    public BillsDetails() {
    }

    public BillsDetails(Integer bilsDetailsId) {
        this.bilsDetailsId = bilsDetailsId;
    }

    public Integer getBilsDetailsId() {
        return bilsDetailsId;
    }

    public void setBilsDetailsId(Integer bilsDetailsId) {
        this.bilsDetailsId = bilsDetailsId;
    }

    public Integer getSuppliersBilsId() {
        return suppliersBilsId;
    }

    public void setSuppliersBilsId(Integer suppliersBilsId) {
        this.suppliersBilsId = suppliersBilsId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bilsDetailsId != null ? bilsDetailsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BillsDetails)) {
            return false;
        }
        BillsDetails other = (BillsDetails) object;
        if ((this.bilsDetailsId == null && other.bilsDetailsId != null) || (this.bilsDetailsId != null && !this.bilsDetailsId.equals(other.bilsDetailsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.BillsDetails[ bilsDetailsId=" + bilsDetailsId + " ]";
    }
    
}
