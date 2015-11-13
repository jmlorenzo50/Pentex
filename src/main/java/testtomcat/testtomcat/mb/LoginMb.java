/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testtomcat.testtomcat.mb;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import testtomcat.testtomcat.bean.SecurityBean;

/**
 *
 * @author jorge
 */
@Named(value = "loginMb")
@SessionScoped
public class LoginMb implements Serializable {

    private SecurityBean security;

    public SecurityBean getSecurity() {
        return security;
    }

    public void setSecurity(SecurityBean security) {
        this.security = security;
    }
    
    
}
