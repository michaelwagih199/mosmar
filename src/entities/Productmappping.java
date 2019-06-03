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
@Table(name = "productmappping")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productmappping.findAll", query = "SELECT p FROM Productmappping p")
    , @NamedQuery(name = "Productmappping.findByProductMappingId", query = "SELECT p FROM Productmappping p WHERE p.productMappingId = :productMappingId")
    , @NamedQuery(name = "Productmappping.findByProductmainId", query = "SELECT p FROM Productmappping p WHERE p.productmainId = :productmainId")
    , @NamedQuery(name = "Productmappping.findBySubProductId1", query = "SELECT p FROM Productmappping p WHERE p.subProductId1 = :subProductId1")
    , @NamedQuery(name = "Productmappping.findBySubProduct2", query = "SELECT p FROM Productmappping p WHERE p.subProduct2 = :subProduct2")
    , @NamedQuery(name = "Productmappping.findBySubProduct3", query = "SELECT p FROM Productmappping p WHERE p.subProduct3 = :subProduct3")
    , @NamedQuery(name = "Productmappping.findBySubProduct4", query = "SELECT p FROM Productmappping p WHERE p.subProduct4 = :subProduct4")})
public class Productmappping implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "productMappingId")
    private Integer productMappingId;
    @Column(name = "productmainId")
    private Integer productmainId;
    @Column(name = "subProductId_1")
    private Integer subProductId1;
    @Column(name = "subProduct_2")
    private Integer subProduct2;
    @Column(name = "subProduct_3")
    private Integer subProduct3;
    @Column(name = "subProduct_4")
    private Integer subProduct4;

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

    public Integer getSubProductId1() {
        return subProductId1;
    }

    public void setSubProductId1(Integer subProductId1) {
        this.subProductId1 = subProductId1;
    }

    public Integer getSubProduct2() {
        return subProduct2;
    }

    public void setSubProduct2(Integer subProduct2) {
        this.subProduct2 = subProduct2;
    }

    public Integer getSubProduct3() {
        return subProduct3;
    }

    public void setSubProduct3(Integer subProduct3) {
        this.subProduct3 = subProduct3;
    }

    public Integer getSubProduct4() {
        return subProduct4;
    }

    public void setSubProduct4(Integer subProduct4) {
        this.subProduct4 = subProduct4;
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
