/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testtomcat.testtomcat.util;

/**
 *
 * @author jorge
 */
public enum Constantes {
    
    NOMBRE_APP("TestTomcat");
    
    private String value;

    private Constantes(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
}
