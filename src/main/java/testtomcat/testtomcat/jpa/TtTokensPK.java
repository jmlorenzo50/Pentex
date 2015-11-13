/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testtomcat.testtomcat.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jorge
 */
@Embeddable
public class TtTokensPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "id_app")
    private String idApp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id_user")
    private String idUser;

    public TtTokensPK() {
    }

    public TtTokensPK(String idApp, String idUser) {
        this.idApp = idApp;
        this.idUser = idUser;
    }

    public String getIdApp() {
        return idApp;
    }

    public void setIdApp(String idApp) {
        this.idApp = idApp;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idApp != null ? idApp.hashCode() : 0);
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TtTokensPK)) {
            return false;
        }
        TtTokensPK other = (TtTokensPK) object;
        if ((this.idApp == null && other.idApp != null) || (this.idApp != null && !this.idApp.equals(other.idApp))) {
            return false;
        }
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "testtomcat.testtomcat.jpa.TtTokensPK[ idApp=" + idApp + ", idUser=" + idUser + " ]";
    }
    
}
