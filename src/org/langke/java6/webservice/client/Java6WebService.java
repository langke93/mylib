
package org.langke.java6.webservice.client;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.1 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "Java6WebService", targetNamespace = "http://webservice.java6/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Java6WebService {


    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "doSomething", targetNamespace = "http://webservice.java6/", className = "java6.webservice.client.DoSomething")
    @ResponseWrapper(localName = "doSomethingResponse", targetNamespace = "http://webservice.java6/", className = "java6.webservice.client.DoSomethingResponse")
    public String doSomething(String ... args);

}
