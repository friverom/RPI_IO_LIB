package RPI_IO_Lib;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements a class to interact with RPI_IO board
 * @author Federico
 */
public class RPI_IO {
    
    private DS1307 rtc = null;
    private LTC2309 adc = null;
    private MCP23017 gpio = null;
   
    /**
     * RPI_IO class constructor
     * @throws InterruptedException 
     */
    public RPI_IO() throws InterruptedException {

        I2CBus i2c;
        try {
            i2c = I2CFactory.getInstance(I2CBus.BUS_1);
            System.out.println("I2C Bus ready");
            System.out.println("Installing RPI_IO Board Drivers");
            rtc = new DS1307(i2c);
            gpio = new MCP23017(i2c);
            adc = new LTC2309(i2c);
        } catch (I2CFactory.UnsupportedBusNumberException ex) {
            System.out.println("I2C Error. RPI_IO board not active");
            Logger.getLogger(RPI_IO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("I2C Error. RPI_IO board not active");
            Logger.getLogger(RPI_IO.class.getName()).log(Level.SEVERE, null, ex);
        }
        gpio.setOFF_All();
        
    }
   
 /**
  * Turn off all relay outputs
  */   
    public void allRly_off(){
        gpio.setOFF_All();
    }
/**
 * Turns on all relay outputs
 */
    public void allRly_on(){
        gpio.setON_All();
    }
/**
 * Read relays out port
 * @return int with port value
 */
    public int getRlyStatus(){
        int data = 0;
        data = gpio.getRLYS();
        return data;
    }
/**
 * Set's relay output ON
 * @param r relay number
 */
    public void setRly(int r) {
           gpio.setON_Rly(r);
    }
/**
 * Reset relay output OFF
 * @param r relay number
 */
    public void resetRly(int r) {
        gpio.setOFF_Rly(r);
    }
/**
 * Toggles relay output
 * @param r relay number
 */
    public void toggleRly(int r) {
        gpio.toggle_Rly(r);
    }
/**
 * Pulse relay output ON for t decimal seconds
 * @param r relay number
 * @param t time in decimal seconds.
 */
    public void pulseRly(int r, int t) {
        gpio.pulseON_Rly(r, t);
    }
/**
 * Toggle relay output t decimal seconds
 * @param r relay number
 * @param time decimal seconds
 */
    public void pulseToggle(int r, int time) {
        gpio.pulseToggle(r, time);
    }
/**
 * Read Input Port
 * @return int value of Port
 */
    public int getInputs() {
        int data = 0;
        data = gpio.getInputs();
        return data;
    }
/**
 * Read Analog Input Channel
 * @param c Analog Input Number
 * @return int value of input
 */
    public int getChannel(int c) {
        int data = 0;
        data = adc.getAnalogIn(c);
        return data;
    }
/**
 * Get RTC Time
 * @return String format hh:mm:ss
 */
    public String getTime() {
        String time = null;
        time = rtc.getTime();
        return time;
    }
/**
 * Get RTC Date
 * @return String format dd/mm/yyyy
 */
    public String getDate() {
        String date = null;
        date = rtc.getDate();
        return date;
    }
/**
 * Set RTC time
 * @param s String format hh:mm:ss
 */
    public void setTime(String s) {
        rtc.setTime(s);
    }
/**
 * Set RTC date
 * @param s String format dd/mm/yyyy
 */
    public void setDate(String s) {
        rtc.setDate(s);
    }
    
    public Calendar getCalendarRTC(){
        return rtc.getCalendarRTC();
    }
    
    public void setCalendarRTC(Calendar date){
        rtc.setCalendarRTC(date);
    }
/**
 * Blink RTC led at 1Hz
 */
    public void blink_1Hz() {
        try {
            rtc.blink();
        } catch (IOException ex) {
            Logger.getLogger(RPI_IO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/**
 * Turn ON RTC Led
 */
    public void out_on() {
        try {
            rtc.out_on();
        } catch (IOException ex) {
            Logger.getLogger(RPI_IO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/**
 * Turn OFF RTC Led
 */
    public void out_off() {
        try {
            rtc.out_off();
        } catch (IOException ex) {
            Logger.getLogger(RPI_IO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/**
 * Write string to RTC memory at address
 * @param a address (00-55)
 * @param s String to be save to memory
 */
    public void writeString(int a, String s) {
        try {
            rtc.writeString(a+0x08, s);
        } catch (IOException ex) {
            Logger.getLogger(RPI_IO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/**
 * Read String from RTC memory at address
 * @param a address (0-55)
 * @return STring
 */
    public String readString(int a) {
        String data = null;
        try {
            data = rtc.readString(a+0x08);
        } catch (IOException ex) {
            Logger.getLogger(RPI_IO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
/**
 * Write int number to RTC memory at address
 * @param a address (0-55)
 * @param i int value
 */
    public void writeInt(int a, int i) {
        try {
            rtc.writeInt(a, i);
        } catch (IOException ex) {
            Logger.getLogger(RPI_IO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/**
 * Get int from RTC memory at address
 * @param a address (0-55)
 * @return int value
 */
    public int readInt(int a) {
        int data = 0;
        try {
            data = rtc.readInt(a);
        } catch (IOException ex) {
            Logger.getLogger(RPI_IO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
/**
 * 
 * @param r 
 */
    public void pulseRly(int r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
/**
 * Set relay output port
 * @param p port value
 */
    public void setPort(int p) {
        gpio.setPort(p);
    }
/**
 * Read input number
 * @param r input number
 * @return 
 */
    public boolean getInput(int r) {
        return gpio.getInput(r);
    }
    
    
}
