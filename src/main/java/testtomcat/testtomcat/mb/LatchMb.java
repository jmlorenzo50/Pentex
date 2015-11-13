/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testtomcat.testtomcat.mb;

import com.elevenpaths.srv.SecuritySrv;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import com.elevenpaths.srv.LatchSrv;
import testtomcat.testtomcat.bean.SecurityBean;
import testtomcat.testtomcat.exception.ServiceException;
import testtomcat.testtomcat.util.Constantes;

/**
 *
 * @author jorge
 */
@Named(value = "latchMb")
@ManagedBean
@RequestScoped
public class LatchMb implements Serializable {
    
    private SecurityBean security;
    
    private SecurityBean pantalla;

    public SecurityBean getPantalla() {
        return pantalla;
    }

    public void setPantalla(SecurityBean pantalla) {
        this.pantalla = pantalla;
    }

    
    private String navegacion;

    public LatchMb() {
        security = new SecurityBean();
        security.setValido(false);
        
        pantalla = new SecurityBean();
        navegacion = null;
    }
    
    
    @PostConstruct
    public void initialize() {
        FacesContext context = FacesContext.getCurrentInstance();
        security = (SecurityBean) context.getExternalContext().getSessionMap().get("security");
        if (security == null) {
            SecuritySrv srvS = SecuritySrv.getInstance();
            security = srvS.datosAplicacion(Constantes.NOMBRE_APP.getValue());
            if (security == null) {
                navegacion = "errorApp";
            } else {
                try {
                    SecurityBean temp = srvS.consultar(security.getIdApp(), security.getIdUser());
                    if (temp == null) {
                        navegacion = "tokens";
                    } else {
                        security = temp;
                    }
                } catch (Exception e) {
                    navegacion = "bloqueo";
                }
            }   
        }
        
        if (security != null && security.getToken() != null) {
            LatchSrv srv = LatchSrv.getInstance(security.getIdApp(), security.getIdSecret());
            try {
                srv.acceder(security.getToken());
            } catch (ServiceException ex) {
                navegacion = "bloqueo";
                //Logger.getLogger(LatchMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    private void irA(String pagina) {
        FacesContext context = FacesContext.getCurrentInstance();
        ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler)context.getApplication().getNavigationHandler();
        handler.performNavigation(pagina);
    }

    public void checkNavegacion(ComponentSystemEvent event) { 
        if (navegacion != null) {
            irA(navegacion);
        }
    }

    
    
    public void checkToken(ComponentSystemEvent event) { 
        SecuritySrv srv = SecuritySrv.getInstance();
        SecurityBean temp = srv.existeToken(Constantes.NOMBRE_APP.getValue());
        if (temp == null) {
            irA("tokens");
        } else {
            security = temp;
        }
        
    }
    
    public void checkLogin(ComponentSystemEvent event) { 
        FacesContext context = FacesContext.getCurrentInstance();
        security = (SecurityBean) context.getExternalContext().getSessionMap().get("security");
        if (security == null || !security.isValido()) {
            irA("login");
        }
    }
    
    public SecurityBean getSecurity() {
        return security;
    }

    public void setSecurity(SecurityBean security) {
        this.security = security;
    }
    
    
    public String solicitarToken() {
        SecuritySrv srvS = SecuritySrv.getInstance();
        LatchSrv srv = LatchSrv.getInstance(security.getIdApp(), security.getIdSecret());
        String codigoPareado;
        try {
            codigoPareado = srv.solicitarToken(pantalla.getToken());
            System.out.print("Codigo pareado " + codigoPareado);
            srvS.vincular(security.getIdApp(), security.getIdUser(), codigoPareado);
            return "login";
        } catch (ServiceException ex) {
            addMessage(ex.getMensaje());
        }
        return "tokens";
    }
    
    public String desvincularToken() {
        LatchSrv srv = LatchSrv.getInstance(security.getIdApp(), security.getIdSecret());
        SecuritySrv srvS = SecuritySrv.getInstance();
        try {
            srv.desvincularToken(security.getToken());
            srvS.desvincular(security.getToken());
            addMessage("Desvinculación realizada");
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().remove("security");
            return "tokens";
        } catch (ServiceException ex) {
            addMessage(ex.getMensaje());
        }
        return "acceso";
    }
    
    
    public String acceder() {
        
        LatchSrv srv = LatchSrv.getInstance(security.getIdApp(), security.getIdSecret());
        SecuritySrv srvS = SecuritySrv.getInstance();
        
        try {
            SecurityBean temp = srvS.consultar(security.getIdApp(), pantalla.getIdUser());
            if (temp.getPassword().equals(pantalla.getPassword()) ) {
                srv.acceder(temp.getToken());
                security = temp;
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getSessionMap().put("security", security);
            }
            return "acceso";
        } catch (ServiceException ex) {
            if ("-1".equals(ex.getCodigo())){
                irA("bloqueo");
            } else {
                addMessage(ex.getMensaje());    
            }
        }
        return "login";
    }
    
    
    public String salir() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("security");
        return "login";
    }
    
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }    

    
    public String getNavegacion() {
        return navegacion;
    }

    public void setNavegacion(String navegacion) {
        this.navegacion = navegacion;
    }
 
}
