
package org.langke.java6.webservice.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the java6.webservice.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DoSomething_QNAME = new QName("http://webservice.java6/", "doSomething");
    private final static QName _DoSomethingResponse_QNAME = new QName("http://webservice.java6/", "doSomethingResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: java6.webservice.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DoSomethingResponse }
     * 
     */
    public DoSomethingResponse createDoSomethingResponse() {
        return new DoSomethingResponse();
    }

    /**
     * Create an instance of {@link DoSomething }
     * 
     */
    public DoSomething createDoSomething() {
        return new DoSomething();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoSomething }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.java6/", name = "doSomething")
    public JAXBElement<DoSomething> createDoSomething(DoSomething value) {
        return new JAXBElement<DoSomething>(_DoSomething_QNAME, DoSomething.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoSomethingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.java6/", name = "doSomethingResponse")
    public JAXBElement<DoSomethingResponse> createDoSomethingResponse(DoSomethingResponse value) {
        return new JAXBElement<DoSomethingResponse>(_DoSomethingResponse_QNAME, DoSomethingResponse.class, null, value);
    }

}
