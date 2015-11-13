/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elevenpaths.srv;

import com.elevenpaths.latch.Latch;
import com.elevenpaths.latch.LatchResponse;
import com.google.gson.JsonElement;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.faces.event.ActionEvent;
import testtomcat.testtomcat.exception.ServiceException;
import testtomcat.testtomcat.util.Config;

/**
 *
 * @author jorge
 */
public class LatchSrv {

    private String appId; 
    
    private String secret;
    
    private Latch latch;
    
    private static LatchSrv srv;
    
    private LatchSrv(String appId, String secret) {
        this.appId = appId;
        this.secret = secret;
        latch = new Latch(appId, secret);
        //Config cfg = Config.getInstance();
        //String APP_ID = cfg.get("latch.id.aplicacion");
        //String SECRET = cfg.get("latch.secreto");
    }
    
    public static LatchSrv getInstance(String appId, String secret) {
        if (srv == null) {
            srv = new LatchSrv(appId, secret);
        }
        return srv;
    }

        
    public String solicitarToken(String token) throws ServiceException {
        LatchResponse pairResponse = latch.pair(token);
        String codigoPareado = null;
        if (pairResponse.getError() == null) {
            //this.codigoPareado = pairResponse.getData().entrySet().toArray()[0];
            //accountId => "ZeQ8PvnmB9L4hhKRLUkdcjLn7amBU9bn2rAjMRGHwTrR4QUzGgPxTY2PpQ8vbzgZ"
            //accountId => "TUan4VbQF2zvmZC9jEfh7BYaUJ7NppcGh27XePW4a9ARLz3G2HJYd3N7rtceUyxi"
            JsonElement element = pairResponse.getData().get("accountId");
            codigoPareado = element.getAsString();
            System.out.print("Codigo pareado " + codigoPareado);
        } else {
            throw new ServiceException (pairResponse.getError().getCode() + "", pairResponse.getError().getMessage());
        }
        return codigoPareado;
    }

        
    public void acceder(String idCuenta) throws ServiceException {
        //Config cfg = Config.getInstance();
        //String ACCOUNT_ID = cfg.get("latch.id.cuenta");
        LatchResponse statusResponse = latch.status(idCuenta);
        if (statusResponse.getError() != null) {
            throw new ServiceException (statusResponse.getError().getCode() + "", statusResponse.getError().getMessage());
        } else if (!estaActivo(statusResponse)) {
            throw new ServiceException ("-1", "Sin acceso");
        } 
    }

    
    public void desvincularToken(String idCuenta) throws ServiceException {
        //Config cfg = Config.getInstance();
        //String ACCOUNT_ID = cfg.get("latch.id.cuenta");
        LatchResponse unpairResponse = latch.unpair(idCuenta);
        if (unpairResponse.getError() != null) {
            throw new ServiceException (unpairResponse.getError().getCode() + "", unpairResponse.getError().getMessage());
        }    
    }

    private static boolean estaActivo(LatchResponse statusResponse) {
        //operations => {"cKbvzaZ6TYUWCCMsNUBU":{"status":"off"}}
        boolean salida = false;    
        JsonElement operations = statusResponse.getData().get("operations");
        Set<Map.Entry<String, JsonElement>> keys = operations.getAsJsonObject().entrySet();
        for (Iterator<Map.Entry<String, JsonElement>> iterator = keys.iterator(); iterator.hasNext();) {
            Map.Entry<String, JsonElement> key = iterator.next();
            JsonElement status = key.getValue().getAsJsonObject().get("status");
            if ("on".equals(status.getAsString())) {
                salida = true;
            }
        }
        return salida;    
    }
    
    
    
}
