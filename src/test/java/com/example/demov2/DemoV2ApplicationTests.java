package com.example.demov2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.ws.test.server.MockWebServiceClient;

import java.util.Map;

import static org.springframework.ws.test.server.RequestCreators.withSoapEnvelope;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.xpath;

@SpringBootTest
class DemoV2ApplicationTests {
    @Autowired
    private ApplicationContext applicationContext;
    private MockWebServiceClient mockClient;

    @BeforeEach
    public void createClient() {
        mockClient = MockWebServiceClient.createClient(applicationContext);
    }

    @Value("classpath:request.xml")
    Resource requestForSpain;

    @Test
    void testWebservice() throws Exception {

        mockClient.sendRequest(withSoapEnvelope(requestForSpain))
                .andExpect(noFault())
                .andExpect(xpath("/ns:getCountryResponse/ns:country/ns:name/text()",
                        Map.of("ns", "http://spring.io/guides/gs-producing-web-service"))
                        .evaluatesTo("Spain"));
        ;
    }

    /*
    <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
    <SOAP-ENV:Header/>
    <SOAP-ENV:Body>
        <ns2:getCountryResponse xmlns:ns2="http://spring.io/guides/gs-producing-web-service">
            <ns2:country>
                <ns2:name>Spain</ns2:name>
                <ns2:population>46704314</ns2:population>
                <ns2:capital>Madrid</ns2:capital>
                <ns2:currency>EUR</ns2:currency>
            </ns2:country>
        </ns2:getCountryResponse>
    </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
     */
}
