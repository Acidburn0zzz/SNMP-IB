package example;

import java.net.InetAddress;

import org.apache.log4j.BasicConfigurator;

import com.intrbiz.snmp.SNMPTransport;
import com.intrbiz.snmp.SNMPV3Context;
import com.intrbiz.snmp.handler.ResponseHandler;
import com.intrbiz.snmp.security.SNMPAuthMode;
import com.intrbiz.snmp.security.SNMPPrivMode;

public class SysInfoV3
{
    public static void main(String[] args) throws Exception
    {
        BasicConfigurator.configure();
        // Create the transport which will be used to send our SNMP messages
        SNMPTransport transport = SNMPTransport.open();
        
        // A context represents an Agent we are going to contact, or which is going to contact us
        SNMPV3Context swAgent    = new SNMPV3Context(InetAddress.getByName("172.30.12.1"));
        swAgent.setEngineId("8000002B0016E0357E406877");
        // swAgent.setUser("admin", SNMPAuthMode.SHA1, "abcde12345", SNMPPrivMode.AES128);
        swAgent.setUser("testdes", SNMPAuthMode.SHA1, "abcde12345", SNMPPrivMode.DES);
        
        SNMPV3Context sw2Agent    = new SNMPV3Context(InetAddress.getByName("172.30.12.3"));
        sw2Agent.setEngineId("0000002b0300051ad8f88000");
        sw2Agent.setUser("test3des", SNMPAuthMode.SHA1, "abcde12345", SNMPPrivMode.NULL);
        
        // Register the context with the transport so we can send messages
        swAgent.register(transport);
        sw2Agent.register(transport);
        
        // Use the context to send messages
        // The callback will be executed when a response to a request is received
        swAgent.get(new ResponseHandler.LoggingHandler(), "1.3.6.1.2.1.1.1.0", "1.3.6.1.2.1.1.3.0");
        // sw2Agent.get(new ResponseHandler.LoggingHandler(), "1.3.6.1.2.1.1.1.0", "1.3.6.1.2.1.1.3.0");
        
        // Run our transport to send and receive messages
        transport.run();
    }
}
