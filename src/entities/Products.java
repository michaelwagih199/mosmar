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
@Table(name = "products")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Products.findAll", query = "SELECT p FROM Products p")
    , @NamedQuery(name = "Products.findByProductid", query = "SELECT p FROM Products p WHERE p.productid = :productid")
    , @NamedQuery(name = "Products.findByProductWeight", query = "SELECT p FROM Products p WHERE p.productWeight = :productWeight")
    , @NamedQuery(name = "Products.findByUnitInStock", query = "SELECT p FROM Products p WHERE p.unitInStock = :unitInStock")
    , @NamedQuery(name = "Products.findByPerchusePrice", query = "SELECT p FROM Products p WHERE p.perchusePrice = :perchusePrice")
    , @NamedQuery(name = "Products.findByGomlaBuyPrice", query = "SELECT p FROM Products p WHERE p.gomlaBuyPrice = :gomlaBuyPrice")
    , @NamedQuery(name = "Products.findByPartitionBuyPrice", query = "SELECT p FROM Products p WHERE p.partitionBuyPrice = :partitionBuyPrice")
    , @NamedQuery(name = "Products.findByGomelGomlaBuyPrice", query = "SELECT p FROM Products p WHERE p.gomelGomlaBuyPrice = :gomelGomlaBuyPrice")
    , @NamedQuery(name = "Products.findBySupplierid", query = "SELECT p FROM Products p WHERE p.supplierid = :supplierid")
    , @NamedQuery(name = "Products.findByAllertWeight", query = "SELECT p FROM Products p WHERE p.allertWeight = :allertWeight")
    , @NamedQuery(name = "Products.findByAlertUnit", query = "SELECT p FROM Products p WHERE p.alertUnit = :alertUnit")
    , @NamedQuery(name = "Products.findByUnitsWeightInStock", query = "SELECT p FROM Products p WHERE p.unitsWeightInStock = :unitsWeightInStock")})
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Product_id")
    private Integer productid;
    @Lob
    @Column(name = "ProductName")
    private String productName;
    @Column(name = "productWeight")
    private Integer productWeight;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "UnitInStock")
    private Float unitInStock;
    @Column(name = "PerchusePrice")
    private Float perchusePrice;
    @Column(name = "GomlaBuyPrice")
    private Float gomlaBuyPrice;
    @Column(name = "PartitionBuyPrice")
    private Float partitionBuyPrice;
    @Column(name = "GomelGomlaBuyPrice")
    private Float gomelGomlaBuyPrice;
    @Column(name = "Supplier_id")
    private Integer supplierid;
    @Column(name = "allertWeight")
    private Float allertWeight;
    @Column(name = "alertUnit")
    private Integer alertUnit;
    @Column(name = "Units_Weight_In_Stock")
    private Float unitsWeightInStock;

    public Products() {
    }

    public Products(Integer productid) {
        this.productid = productid;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(Integer productWeight) {
        this.productWeight = productWeight;
    }

    public Float getUnitInStock() {
        return unitInStock;
    }

    public void setUnitInStock(Float unitInStock) {
        this.unitInStock = unitInStock;
    }

    public Float getPerchusePrice() {
        return perchusePrice;
    }

    public void setPerchusePrice(Float perchusePrice) {
        this.perchusePrice = perchusePrice;
    }

    public Float getGomlaBuyPrice() {
        return gomlaBuyPrice;
    }

    public void setGomlaBuyPrice(Float gomlaBuyPrice) {
        this.gomlaBuyPrice = gomlaBuyPrice;
    }

    public Float getPartitionBuyPrice() {
        return partitionBuyPrice;
    }

    public void setPartitionBuyPrice(Float partitionBuyPrice) {
        this.partitionBuyPrice = partitionBuyPrice;
    }

    public Float getGomelGomlaBuyPrice() {
        return gomelGomlaBuyPrice;
    }

    public void setGomelGomlaBuyPrice(Float gomelGomlaBuyPrice) {
        this.gomelGomlaBuyPrice = gomelGomlaBuyPrice;
    }

    public Integer getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(Integer supplierid) {
        this.supplierid = supplierid;
    }

    public Float getAllertWeight() {
        return allertWeight;
    }

    public void setAllertWeight(Float allertWeight) {
        this.allertWeight = allertWeight;
    }

    public Integer getAlertUnit() {
        return alertUnit;
    }

    public void setAlertUnit(Integer alertUnit) {
        this.alertUnit = alertUnit;
    }

    public Float getUnitsWeightInStock() {
        return unitsWeightInStock;
    }

    public void setUnitsWeightInStock(Float unitsWeightInStock) {
        this.unitsWeightInStock = unitsWeightInStock;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productid != null ? productid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Products)) {
            return false;
        }
        Products other = (Products) object;
        if ((this.productid == null && other.productid != null) || (this.productid != null && !this.productid.equals(other.productid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Products[ productid=" + productid + " ]";
    }
    
}
