/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jaeger.test.jaegertest.services;

import javax.ejb.Stateless;

/**
 *
 * @author lkorenko
 */
@Stateless
public class TestService {

    public String hi() {
        return "{\"hi\":\"hi\"}";
    }

}
