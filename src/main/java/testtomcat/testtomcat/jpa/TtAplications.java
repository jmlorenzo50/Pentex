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
@Table(name = "tt_aplications")
@Cacheable(false)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TtAplications.findAll", query = "SELECT t FROM TtAplications t"),
    @NamedQuery(name = "TtAplications.findByIdApp", query = "SELECT t FROM TtAplications t WHERE t.idApp = :idApp"),
    @NamedQuery(name = "TtAplications.findByIdSecret", query = "SELECT t FROM TtAplications t WHERE t.idSecret = :idSecret"),
    @NamedQuery(name = "TtAplications.findByDsApp", query = "SELECT t FROM TtAplications t WHERE t.dsApp = :dsApp")})
public class TtAplications implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "id_app")
    private String idApp;
    @Size(max = 45)
    @Column(name = "id_secret")
    private String idSecret;
    @Size(max = 45)
    @Column(name = "ds_app")
    private String dsApp;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ttAplications")
    private List<TtTokens> ttTokensList;

    public TtAplications() {
    }

    public TtAplications(String idApp) {
        this.idApp = idApp;
    }

    public String getIdApp() {
        return idApp;
    }

    public void setIdApp(String idApp) {
        this.idApp = idApp;
    }

    public String getIdSecret() {
        return idSecret;
    }

    public void setIdSecret(String idSecret) {
        this.idSecret = idSecret;
    }

    public String getDsApp() {
        return dsApp;
    }

    public void setDsApp(String dsApp) {
        this.dsApp = dsApp;
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
        hash += (idApp != null ? idApp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TtAplications)) {
            return false;
        }
        TtAplications other = (TtAplications) object;
        if ((this.idApp == null && other.idApp != null) || (this.idApp != null && !this.idApp.equals(other.idApp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "testtomcat.testtomcat.jpa.TtAplications[ idApp=" + idApp + " ]";
    }
    
}
