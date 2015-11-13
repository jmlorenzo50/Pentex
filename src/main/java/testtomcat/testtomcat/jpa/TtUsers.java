/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testtomcat.testtomcat.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jorge
 */
@Entity
@Table(name = "tt_users")
@Cacheable(false)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TtUsers.findAll", query = "SELECT t FROM TtUsers t"),
    @NamedQuery(name = "TtUsers.findByIdUser", query = "SELECT t FROM TtUsers t WHERE t.idUser = :idUser"),
    @NamedQuery(name = "TtUsers.findByDsUser", query = "SELECT t FROM TtUsers t WHERE t.dsUser = :dsUser"),
    @NamedQuery(name = "TtUsers.findByPassword", query = "SELECT t FROM TtUsers t WHERE t.password = :password")})
public class TtUsers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id_user")
    private String idUser;
    @Size(max = 45)
    @Column(name = "ds_user")
    private String dsUser;
    @Size(max = 45)
    @Column(name = "password")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ttUsers")
    private List<TtTokens> ttTokensList;

    public TtUsers() {
    }

    public TtUsers(String idUser) {
        this.idUser = idUser;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getDsUser() {
        return dsUser;
    }

    public void setDsUser(String dsUser) {
        this.dsUser = dsUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public List<TtTokens> getTtTokensList() {
        return ttTokensList;
    }

    public void setTtTokensList(List<TtTokens> ttTokensList) {
        this.ttTokensList = ttTokensList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TtUsers)) {
            return false;
        }
        TtUsers other = (TtUsers) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "testtomcat.testtomcat.jpa.TtUsers[ idUser=" + idUser + " ]";
    }
    
}
