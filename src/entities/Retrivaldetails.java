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
@Table(name = "retrivaldetails")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Retrivaldetails.findAll", query = "SELECT r FROM Retrivaldetails r")
    , @NamedQuery(name = "Retrivaldetails.findByRetrivalDetailsId", query = "SELECT r FROM Retrivaldetails r WHERE r.retrivalDetailsId = :retrivalDetailsId")
    , @NamedQuery(name = "Retrivaldetails.findByProductID", query = "SELECT r FROM Retrivaldetails r WHERE r.productID = :productID")
    , @NamedQuery(name = "Retrivaldetails.findByExpenseValue", query = "SELECT r FROM Retrivaldetails r WHERE r.expenseValue = :expenseValue")
    , @NamedQuery(name = "Retrivaldetails.findByQuantity", query = "SELECT r FROM Retrivaldetails r WHERE r.quantity = :quantity")
    , @NamedQuery(name = "Retrivaldetails.findByRetrivalsId", query = "SELECT r FROM Retrivaldetails r WHERE r.retrivalsId = :retrivalsId")
    , @NamedQuery(name = "Retrivaldetails.findByProductCategoryId", query = "SELECT r FROM Retrivaldetails r WHERE r.productCategoryId = :productCategoryId")})
public class Retrivaldetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "retrivalDetailsId")
    private Integer retrivalDetailsId;
    @Column(name = "ProductID")
    private Integer productID;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "expenseValue")
    private Float expenseValue;
    @Column(name = "quantity")
    private Float quantity;
    @Column(name = "retrivalsId")
    private Integer retrivalsId;
    @Column(name = "ProductCategoryId")
    private Integer productCategoryId;

    public Retrivaldetails() {
    }

    public Retrivaldetails(Integer retrivalDetailsId) {
        this.retrivalDetailsId = retrivalDetailsId;
    }

    public Integer getRetrivalDetailsId() {
        return retrivalDetailsId;
    }

    public void setRetrivalDetailsId(Integer retrivalDetailsId) {
        this.retrivalDetailsId = retrivalDetailsId;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Float getExpenseValue() {
        return expenseValue;
    }

    public void setExpenseValue(Float expenseValue) {
        this.expenseValue = expenseValue;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Integer getRetrivalsId() {
        return retrivalsId;
    }

    public void setRetrivalsId(Integer retrivalsId) {
        this.retrivalsId = retrivalsId;
    }

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (retrivalDetailsId != null ? retrivalDetailsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Retrivaldetails)) {
            return false;
        }
        Retrivaldetails other = (Retrivaldetails) object;
        if ((this.retrivalDetailsId == null && other.retrivalDetailsId != null) || (this.retrivalDetailsId != null && !this.retrivalDetailsId.equals(other.retrivalDetailsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Retrivaldetails[ retrivalDetailsId=" + retrivalDetailsId + " ]";
    }
    
}
