/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testtomcat.testtomcat.jpa;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jorge
 */
@Entity
@Table(name = "tt_tokens")
@Cacheable(false)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TtTokens.findAll", query = "SELECT t FROM TtTokens t"),
    @NamedQuery(name = "TtTokens.findByIdApp", query = "SELECT t FROM TtTokens t WHERE t.ttTokensPK.idApp = :idApp"),
    @NamedQuery(name = "TtTokens.findByIdUser", query = "SELECT t FROM TtTokens t WHERE t.ttTokensPK.idUser = :idUser"),
    @NamedQuery(name = "TtTokens.findByToken", query = "SELECT t FROM TtTokens t WHERE t.token = :token")})
public class TtTokens implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TtTokensPK ttTokensPK;
    @Size(max = 128)
    @Column(name = "token")
    private String token;
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TtUsers ttUsers;
    @JoinColumn(name = "id_app", referencedColumnName = "id_app", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TtAplications ttAplications;

    public TtTokens() {
    }

    public TtTokens(TtTokensPK ttTokensPK) {
        this.ttTokensPK = ttTokensPK;
    }

    public TtTokens(String idApp, String idUser) {
        this.ttTokensPK = new TtTokensPK(idApp, idUser);
    }

    public TtTokensPK getTtTokensPK() {
        return ttTokensPK;
    }

    public void setTtTokensPK(TtTokensPK ttTokensPK) {
        this.ttTokensPK = ttTokensPK;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TtUsers getTtUsers() {
        return ttUsers;
    }

    public void setTtUsers(TtUsers ttUsers) {
        this.ttUsers = ttUsers;
    }

    public TtAplications getTtAplications() {
        return ttAplications;
    }

    public void setTtAplications(TtAplications ttAplications) {
        this.ttAplications = ttAplications;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ttTokensPK != null ? ttTokensPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TtTokens)) {
            return false;
        }
        TtTokens other = (TtTokens) object;
        if ((this.ttTokensPK == null && other.ttTokensPK != null) || (this.ttTokensPK != null && !this.ttTokensPK.equals(other.ttTokensPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "testtomcat.testtomcat.jpa.TtTokens[ ttTokensPK=" + ttTokensPK + " ]";
    }
    
}
