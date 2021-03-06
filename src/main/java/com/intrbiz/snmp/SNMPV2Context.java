package com.intrbiz.snmp;

import java.io.IOException;
import java.net.InetAddress;

import com.intrbiz.snmp.handler.OnError;
import com.intrbiz.snmp.handler.OnMessage;
import com.intrbiz.snmp.model.PDU;
import com.intrbiz.snmp.model.v2.SNMPMessageV2;
import com.intrbiz.snmp.wrapper.SNMPV2ContextWrapper;

public abstract class SNMPV2Context extends SNMPContext<SNMPV2Context>
{
    protected String community = "public";

    public SNMPV2Context(InetAddress agent, int port)
    {
        super(SNMPVersion.V2C, agent, port);
    }

    public String getCommunity()
    {
        return community;
    }

    public SNMPV2Context setCommunity(String community)
    {
        this.community = community;
        return this;
    }
    
    //

    @Override
    public void send(PDU pdu, OnMessage messageCallback, OnError errorCallback) throws IOException
    {
        SNMPMessageV2 msg = new SNMPMessageV2(this.getVersion(), this.getCommunity(), pdu);
        // send the actual message
        this.send(msg, messageCallback, errorCallback);
    }
    
    @Override
    public SNMPContextId getContextId()
    {
        return new SNMPContextId(this.getAgent());
    }
    
    @Override
    public SNMPV2Context with(OnError defaultErrorHandler)
    {
        return new SNMPV2ContextWrapper(this, defaultErrorHandler);
    }
    
    @Override
    public SNMPV2Context readOnly()
    {
        return new SNMPV2ContextWrapper(this);
    }
}
