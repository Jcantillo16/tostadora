/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author juan_cantillo
 */
@Entity
@Table(name = "sg_payments")
@NamedQueries({
    @NamedQuery(name = "Payments.findAll", query = "SELECT p FROM Payments p")})
public class Payments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "total")
    private Integer total;
    @JoinColumn(name = "method_payment_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private MethodPayments methodPaymentId;
    @JoinColumns({
        @JoinColumn(name = "user_id", referencedColumnName = "id"),
        @JoinColumn(name = "user_id", referencedColumnName = "id")})
    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;
    @OneToMany(mappedBy = "paymentId", fetch = FetchType.LAZY)
    private Collection<OrderDetailsPayments> orderDetailsPaymentsCollection;

    public Payments() {
    }

    public Payments(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public MethodPayments getMethodPaymentId() {
        return methodPaymentId;
    }

    public void setMethodPaymentId(MethodPayments methodPaymentId) {
        this.methodPaymentId = methodPaymentId;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Collection<OrderDetailsPayments> getOrderDetailsPaymentsCollection() {
        return orderDetailsPaymentsCollection;
    }

    public void setOrderDetailsPaymentsCollection(Collection<OrderDetailsPayments> orderDetailsPaymentsCollection) {
        this.orderDetailsPaymentsCollection = orderDetailsPaymentsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payments)) {
            return false;
        }
        Payments other = (Payments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Payments[ id=" + id + " ]";
    }
    
}
