/* * Copyright (c) 2019 Negocio Varejo. All rights reserved. * */
package br.com.negocio.varejo.tag.emitter.models;

import gnu.io.CommPortIdentifier;
import gnu.io.ParallelPort;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import java.io.OutputStream;

/**
 *
 * @author derickfelix
 */
public class SComm {
    public static final int PARITY_NONE = 0;
    public static final int PARITY_ODD = 1;
    public static final int PARITY_EVEN = 2;
    public static final int PARITY_MARK = 3;
    public static final int PARITY_SPACE = 4;
    
    private final String port;
    private final int baudrate;
    private final int timeout;
    private final int databits;
    private final int stopbits;
    private final int parity;
    
    private CommPortIdentifier cp;
    private SerialPort serialPort;
    private ParallelPort parallelPort;
    private OutputStream outputStream;
    private boolean read;
    private boolean write;
    
    
    public SComm(String port, int baudrate)
    {
        this.port = port;
        this.baudrate = baudrate;
        this.timeout = 1000;
        this.parity = PARITY_NONE;
        this.databits = 8;
        this.stopbits = 1;
    }
    
    public void portOpen() throws Exception
    {
        this.cp = CommPortIdentifier.getPortIdentifier(port);
        if (this.cp == null) {
            throw new Exception("A porta " + port + " não existe!");
        } else {
            try {
                if (this.cp.getPortType() == 1) {
                    this.serialPort = (SerialPort) this.cp.open(port, this.timeout);
                } else if (this.cp.getPortType() == 2) {
                    this.parallelPort = (ParallelPort) this.cp.open(port, 100);
                }
            } catch (PortInUseException ignore) {
                throw new Exception("A porta " + port + " já está sendo usada!");
            }

            if (this.cp.getPortType() == 1) {
                this.serialPort.setSerialPortParams(this.baudrate, this.databits, this.stopbits, this.parity);
                this.serialPort.setRTS(true);
            }

        }
    }
    
    public void sendData(String msg) throws Exception
    {
        sendData(msg.getBytes());
    }
    
    public void sendData(byte[] msg) throws Exception
    {
        if (write) {
            if (this.cp.getPortType() == 1) {
                this.outputStream = this.serialPort.getOutputStream();
            } else if (this.cp.getPortType() == 2) {
                this.outputStream = this.parallelPort.getOutputStream();
            }

            this.outputStream.write(msg);
            Thread.sleep(100L);
            this.outputStream.flush();
        }
    }
    
    public void portClose() throws Exception
    {
        if (this.cp.getPortType() == 1) {
            this.serialPort.close();
        } else if (this.cp.getPortType() == 2) {
            this.parallelPort.close();
        }
    }

    public boolean isWrite()
    {
        return write;
    }

    public void setWrite(boolean write)
    {
        this.write = write;
    }

}
