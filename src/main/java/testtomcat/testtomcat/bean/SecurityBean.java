/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testtomcat.testtomcat.bean;

/**
 *
 * @author jorge
 */
public class SecurityBean {
    
    private String idUser;
    
    private String dsUser;
    
    private String password;

    private String idApp;
    
    private String idSecret;
    
    private String dsApp;
    
    private String token;

    private boolean valido;

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    
}
