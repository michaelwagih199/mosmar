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
@Table(name = "productmappping")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productmappping.findAll", query = "SELECT p FROM Productmappping p")
    , @NamedQuery(name = "Productmappping.findByProductMappingId", query = "SELECT p FROM Productmappping p WHERE p.productMappingId = :productMappingId")
    , @NamedQuery(name = "Productmappping.findByProductmainId", query = "SELECT p FROM Productmappping p WHERE p.productmainId = :productmainId")
    , @NamedQuery(name = "Productmappping.findBySubProductId", query = "SELECT p FROM Productmappping p WHERE p.subProductId = :subProductId")})
public class Productmappping implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "productMappingId")
    private Integer productMappingId;
    @Column(name = "productmainId")
    private Integer productmainId;
    @Column(name = "subProductId")
    private Integer subProductId;

    public Productmappping() {
    }

    public Productmappping(Integer productMappingId) {
        this.productMappingId = productMappingId;
    }

    public Integer getProductMappingId() {
        return productMappingId;
    }

    public void setProductMappingId(Integer productMappingId) {
        this.productMappingId = productMappingId;
    }

    public Integer getProductmainId() {
        return productmainId;
    }

    public void setProductmainId(Integer productmainId) {
        this.productmainId = productmainId;
    }

    public Integer getSubProductId() {
        return subProductId;
    }

    public void setSubProductId(Integer subProductId) {
        this.subProductId = subProductId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productMappingId != null ? productMappingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productmappping)) {
            return false;
        }
        Productmappping other = (Productmappping) object;
        if ((this.productMappingId == null && other.productMappingId != null) || (this.productMappingId != null && !this.productMappingId.equals(other.productMappingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Productmappping[ productMappingId=" + productMappingId + " ]";
    }
    
}
