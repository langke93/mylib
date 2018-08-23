
package org.langke.java6.webservice.client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.1 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "Java6WebServiceService", targetNamespace = "http://webservice.java6/", wsdlLocation = "http://192.168.1.99:8080/java6ws/java6.webservice.Java6WebService?wsdl")
public class Java6WebServiceService
    extends Service
{

    private final static URL JAVA6WEBSERVICESERVICE_WSDL_LOCATION;

    static {
        URL url = null;
        try {
            url = new URL("http://192.168.1.99:8080/java6ws/java6.webservice.Java6WebService?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        JAVA6WEBSERVICESERVICE_WSDL_LOCATION = url;
    }

    public Java6WebServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Java6WebServiceService() {
        super(JAVA6WEBSERVICESERVICE_WSDL_LOCATION, new QName("http://webservice.java6/", "Java6WebServiceService"));
    }

    /**
     * 
     * @return
     *     returns Java6WebService
     */
    @WebEndpoint(name = "Java6WebServicePort")
    public Java6WebService getJava6WebServicePort() {
        return (Java6WebService)super.getPort(new QName("http://webservice.java6/", "Java6WebServicePort"), Java6WebService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Java6WebService
     */
    @WebEndpoint(name = "Java6WebServicePort")
    public Java6WebService getJava6WebServicePort(WebServiceFeature... features) {
        return (Java6WebService)super.getPort(new QName("http://webservice.java6/", "Java6WebServicePort"), Java6WebService.class, features);
    }

}
