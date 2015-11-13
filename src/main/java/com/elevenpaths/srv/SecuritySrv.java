/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elevenpaths.srv;

import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import testtomcat.testtomcat.bean.SecurityBean;
import testtomcat.testtomcat.exception.ServiceException;
import testtomcat.testtomcat.jpa.TtAplications;
import testtomcat.testtomcat.jpa.TtTokens;
import testtomcat.testtomcat.jpa.TtTokensPK;
import testtomcat.testtomcat.jpa.TtUsers;
import testtomcat.testtomcat.util.Constantes;

/**
 *
 * @author jorge
 */
public class SecuritySrv {
    
    private SecuritySrv() {
        super();
    }
    
    public static SecuritySrv getInstance() {
        SecuritySrv srv = new SecuritySrv();
        return srv;
    }
    
    private static EntityManager getEntityManager() {
        EntityManager em;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("testtomcat.persistence");        
        emf.getCache().evictAll();
        em = emf.createEntityManager();
        return em;
    }
    
    public SecurityBean consultar(String idApp, String idUser) throws ServiceException{
        EntityManager em = getEntityManager();
        SecurityBean salida = null;
        TtUsers ttUser = null;
        TtTokens ttTokens = null;
        TtAplications ttAplications = null;
        try {
            if (idUser != null) {
                Query q1 = em.createNamedQuery("TtUsers.findByIdUser", TtUsers.class);
                q1.setParameter("idUser", idUser);
                ttUser = (TtUsers) q1.getSingleResult();
            }
            if (ttUser != null && !ttUser.getTtTokensList().isEmpty()) {
                List<TtTokens> lista = ttUser.getTtTokensList();
                for (Iterator<TtTokens> iterator = lista.iterator(); iterator.hasNext();) {
                    TtTokens next = iterator.next();
                    if (next.getTtAplications().getIdApp().equals(idApp)) {
                        ttTokens = ttUser.getTtTokensList().get(0);
                    }
                }
            }
            if (ttTokens != null) {
                ttAplications = ttTokens.getTtAplications();
            }
            if (ttAplications != null) {
                salida = new SecurityBean();
                salida.setIdUser(ttUser.getIdUser());
                salida.setDsUser(ttUser.getDsUser());
                salida.setPassword(ttUser.getPassword());
                salida.setIdApp(ttAplications.getIdApp());
                salida.setIdSecret(ttAplications.getIdSecret());
                salida.setDsApp(ttAplications.getDsApp());
                salida.setToken(ttTokens.getToken());
                salida.setValido(true);
            }
        } catch (Exception e) {
            throw new ServiceException("-2", "Acceso denegado");
        } finally {
            em.close();
        }
        return salida;
    }

    
    public SecurityBean datosAplicacion(String dsApp) {
        EntityManager em = getEntityManager();
        SecurityBean salida = null;
        Query q1 = em.createNamedQuery("TtAplications.findByDsApp", TtAplications.class);
        q1.setParameter("dsApp", dsApp);
        TtAplications ttAplications = (TtAplications) q1.getSingleResult();

        Query q2 = em.createNamedQuery("TtUsers.findByIdUser", TtUsers.class);
        q2.setParameter("idUser", "Admin");
        TtUsers ttUsers = (TtUsers) q2.getSingleResult();
        
        if (ttAplications != null && ttUsers != null) {
            salida = new SecurityBean();
            salida.setIdUser(ttUsers.getIdUser());
            salida.setDsUser(ttUsers.getDsUser());
            salida.setPassword(ttUsers.getPassword());
            salida.setIdApp(ttAplications.getIdApp());
            salida.setIdSecret(ttAplications.getIdSecret());
            salida.setDsApp(ttAplications.getDsApp());
            salida.setToken(null);
            salida.setValido(false);
        }
        em.close();
        return salida;
    }

    
    
    public SecurityBean existeToken(String dsApp) {
        EntityManager em = getEntityManager();
        SecurityBean salida = null;
        Query q1 = em.createNamedQuery("TtAplications.findByDsApp", TtAplications.class);
        q1.setParameter("dsApp", dsApp);
        TtAplications ttAplications = (TtAplications) q1.getSingleResult();
        
        if (ttAplications.getTtTokensList()!= null && ttAplications.getTtTokensList().size() > 0) {
            salida = new SecurityBean();
            salida.setIdUser(null);
            salida.setDsUser(null);
            salida.setPassword(null);
            salida.setIdApp(ttAplications.getIdApp());
            salida.setIdSecret(ttAplications.getIdSecret());
            salida.setDsApp(ttAplications.getDsApp());
            salida.setToken(null);
            salida.setValido(false);
        }
        em.close();
        return salida;
    }

    public void vincular(String idApp, String idUser, String token) {
        EntityManager em = getEntityManager();
        TtTokensPK pk = new TtTokensPK(idApp, idUser);
        TtTokens reg = new TtTokens();
        reg.setTtTokensPK(pk);
        reg.setToken(token);
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(reg);
        em.flush();
        tx.commit();
        em.close();
    }

    public void desvincular(String token) {
        EntityManager em = getEntityManager();
        Query q1 = em.createNamedQuery("TtTokens.findByToken", TtTokens.class);
        q1.setParameter("token", token);
        TtTokens ttTokens = (TtTokens) q1.getSingleResult();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(ttTokens);
        em.flush();
        tx.commit();
        em.close();
    }

    
    
    
    
    
}
