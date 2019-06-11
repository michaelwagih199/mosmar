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
@Table(name = "assets")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Assets.findAll", query = "SELECT a FROM Assets a")
    , @NamedQuery(name = "Assets.findByAssetsId", query = "SELECT a FROM Assets a WHERE a.assetsId = :assetsId")
    , @NamedQuery(name = "Assets.findByAssetsValue", query = "SELECT a FROM Assets a WHERE a.assetsValue = :assetsValue")})
public class Assets implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "assetsId")
    private Integer assetsId;
    @Lob
    @Column(name = "assetsName")
    private String assetsName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "assetsValue")
    private Float assetsValue;
    @Lob
    @Column(name = "notes")
    private String notes;

    public Assets() {
    }

    public Assets(Integer assetsId) {
        this.assetsId = assetsId;
    }

    public Integer getAssetsId() {
        return assetsId;
    }

    public void setAssetsId(Integer assetsId) {
        this.assetsId = assetsId;
    }

    public String getAssetsName() {
        return assetsName;
    }

    public void setAssetsName(String assetsName) {
        this.assetsName = assetsName;
    }

    public Float getAssetsValue() {
        return assetsValue;
    }

    public void setAssetsValue(Float assetsValue) {
        this.assetsValue = assetsValue;
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
        hash += (assetsId != null ? assetsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Assets)) {
            return false;
        }
        Assets other = (Assets) object;
        if ((this.assetsId == null && other.assetsId != null) || (this.assetsId != null && !this.assetsId.equals(other.assetsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Assets[ assetsId=" + assetsId + " ]";
    }
    
}
