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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OM EL NOUR
 */
@Entity
@Table(name = "productsnumber")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productsnumber.findAll", query = "SELECT p FROM Productsnumber p")
    , @NamedQuery(name = "Productsnumber.findByProductnumberid", query = "SELECT p FROM Productsnumber p WHERE p.productnumberid = :productnumberid")
    , @NamedQuery(name = "Productsnumber.findByPerchusenumberPrice", query = "SELECT p FROM Productsnumber p WHERE p.perchusenumberPrice = :perchusenumberPrice")
    , @NamedQuery(name = "Productsnumber.findByGomlaBuynumberPrice", query = "SELECT p FROM Productsnumber p WHERE p.gomlaBuynumberPrice = :gomlaBuynumberPrice")
    , @NamedQuery(name = "Productsnumber.findByPartitionnumberBuyPrice", query = "SELECT p FROM Productsnumber p WHERE p.partitionnumberBuyPrice = :partitionnumberBuyPrice")
    , @NamedQuery(name = "Productsnumber.findByGomelGomlanumberBuyPrice", query = "SELECT p FROM Productsnumber p WHERE p.gomelGomlanumberBuyPrice = :gomelGomlanumberBuyPrice")
    , @NamedQuery(name = "Productsnumber.findBySupplierid", query = "SELECT p FROM Productsnumber p WHERE p.supplierid = :supplierid")
    , @NamedQuery(name = "Productsnumber.findByAllertNumber", query = "SELECT p FROM Productsnumber p WHERE p.allertNumber = :allertNumber")
    , @NamedQuery(name = "Productsnumber.findByUnitsInStock", query = "SELECT p FROM Productsnumber p WHERE p.unitsInStock = :unitsInStock")})
public class Productsnumber implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Product_number_id")
    private Integer productnumberid;
    @Lob
    @Column(name = "Product_number_Name")
    private String productnumberName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Perchuse_number_Price")
    private Float perchusenumberPrice;
    @Column(name = "GomlaBuy_number_Price")
    private Float gomlaBuynumberPrice;
    @Column(name = "Partition_number_BuyPrice")
    private Float partitionnumberBuyPrice;
    @Column(name = "GomelGomla_number_BuyPrice")
    private Float gomelGomlanumberBuyPrice;
    @Column(name = "Supplier_id")
    private Integer supplierid;
    @Column(name = "allert_number")
    private Float allertNumber;
    @Column(name = "Units_In_Stock")
    private Float unitsInStock;

    public Productsnumber() {
    }

    public Productsnumber(Integer productnumberid) {
        this.productnumberid = productnumberid;
    }

    public Integer getProductnumberid() {
        return productnumberid;
    }

    public void setProductnumberid(Integer productnumberid) {
        this.productnumberid = productnumberid;
    }

    public String getProductnumberName() {
        return productnumberName;
    }

    public void setProductnumberName(String productnumberName) {
        this.productnumberName = productnumberName;
    }

    public Float getPerchusenumberPrice() {
        return perchusenumberPrice;
    }

    public void setPerchusenumberPrice(Float perchusenumberPrice) {
        this.perchusenumberPrice = perchusenumberPrice;
    }

    public Float getGomlaBuynumberPrice() {
        return gomlaBuynumberPrice;
    }

    public void setGomlaBuynumberPrice(Float gomlaBuynumberPrice) {
        this.gomlaBuynumberPrice = gomlaBuynumberPrice;
    }

    public Float getPartitionnumberBuyPrice() {
        return partitionnumberBuyPrice;
    }

    public void setPartitionnumberBuyPrice(Float partitionnumberBuyPrice) {
        this.partitionnumberBuyPrice = partitionnumberBuyPrice;
    }

    public Float getGomelGomlanumberBuyPrice() {
        return gomelGomlanumberBuyPrice;
    }

    public void setGomelGomlanumberBuyPrice(Float gomelGomlanumberBuyPrice) {
        this.gomelGomlanumberBuyPrice = gomelGomlanumberBuyPrice;
    }

    public Integer getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(Integer supplierid) {
        this.supplierid = supplierid;
    }

    public Float getAllertNumber() {
        return allertNumber;
    }

    public void setAllertNumber(Float allertNumber) {
        this.allertNumber = allertNumber;
    }

    public Float getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(Float unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productnumberid != null ? productnumberid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productsnumber)) {
            return false;
        }
        Productsnumber other = (Productsnumber) object;
        if ((this.productnumberid == null && other.productnumberid != null) || (this.productnumberid != null && !this.productnumberid.equals(other.productnumberid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Productsnumber[ productnumberid=" + productnumberid + " ]";
    }
    
}
