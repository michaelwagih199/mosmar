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
@Table(name = "expenses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Expenses.findAll", query = "SELECT e FROM Expenses e")
    , @NamedQuery(name = "Expenses.findByExpensesId", query = "SELECT e FROM Expenses e WHERE e.expensesId = :expensesId")
    , @NamedQuery(name = "Expenses.findByExpensesDate", query = "SELECT e FROM Expenses e WHERE e.expensesDate = :expensesDate")
    , @NamedQuery(name = "Expenses.findByExpensesValue", query = "SELECT e FROM Expenses e WHERE e.expensesValue = :expensesValue")})
public class Expenses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ExpensesId")
    private Integer expensesId;
    @Column(name = "ExpensesDate")
    @Temporal(TemporalType.DATE)
    private Date expensesDate;
    @Lob
    @Column(name = "ExpensesContext")
    private String expensesContext;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ExpensesValue")
    private Float expensesValue;
    @Lob
    @Column(name = "Notes")
    private String notes;

    public Expenses() {
    }

    public Expenses(Integer expensesId) {
        this.expensesId = expensesId;
    }

    public Integer getExpensesId() {
        return expensesId;
    }

    public void setExpensesId(Integer expensesId) {
        this.expensesId = expensesId;
    }

    public Date getExpensesDate() {
        return expensesDate;
    }

    public void setExpensesDate(Date expensesDate) {
        this.expensesDate = expensesDate;
    }

    public String getExpensesContext() {
        return expensesContext;
    }

    public void setExpensesContext(String expensesContext) {
        this.expensesContext = expensesContext;
    }

    public Float getExpensesValue() {
        return expensesValue;
    }

    public void setExpensesValue(Float expensesValue) {
        this.expensesValue = expensesValue;
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
        hash += (expensesId != null ? expensesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Expenses)) {
            return false;
        }
        Expenses other = (Expenses) object;
        if ((this.expensesId == null && other.expensesId != null) || (this.expensesId != null && !this.expensesId.equals(other.expensesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Expenses[ expensesId=" + expensesId + " ]";
    }
    
}
