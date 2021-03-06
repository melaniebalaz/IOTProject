package IOT.IOT_SOAP.testcases;

import static org.junit.Assert.*;

import IOT.IOTApplication.alarmclock.ACRestService;
import IOT.IOTApplication.alarmclock.AlarmClockService;
import IOT.IOT_SOAP.ACSoapService;
import IOT.IOT_SOAP.IACSoapService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * JUnit test class for all four operation of the AlarmClock Soap Service.
 * @author Mai
 * @version M2
 */
public class AcSoapServiceTest {
    /** Alarm clock service to create and play alarms. */
    static private AlarmClockService alarmClockService = new AlarmClockService(null);
    /** Delay after which time the alarm should sound (from current time). */
    static private long alarmDelay = 5000;
    /** Time at which the alarm should sound. */
    static private long timeInMs;

    /**
     * Specifies the URL at which the services are offered.
     */
    static public String serviceUrl =  "http://localhost:8080/ACSoapService";

    /**
     * Interface for the instance of the SOAP service to be tested.
     */
    private IACSoapService soapService = null;

    /**
     * True if server and client are initialized and running.
     * False else.
     */
    static private boolean endpointsReady = false;

    /**
     * A list of all currently added alarms.
     */
    private ArrayList<Long> alarms = null;

    /**
     * Unit test constructor. It starts both a test server and client.
     * with some test alarms.
     */
    public AcSoapServiceTest() {
        System.out.println("Setting alarm clock service...");
        soapService = new ACSoapService(alarmClockService);

        if (!endpointsReady) {
            System.out.println("Starting server...");
            startServer();

            System.out.println("Starting client...");
            startClient();

            endpointsReady = true;
        }

        alarms = new ArrayList<>();
        fillServiceWithAlarms();
    }

    /**
     * Starts a demo client at the serviceURL.
     */
    public void startClient() {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(IACSoapService.class);
        factory.setAddress(serviceUrl);
        soapService = (IACSoapService) factory.create();
    }

    /**
     * Starts a demo server at the serviceURL.
     */
    public void startServer() {
        JaxWsServerFactoryBean svrFactory = new JaxWsServerFactoryBean();
        svrFactory.setAddress(serviceUrl);
        svrFactory.setServiceBean(soapService);
        svrFactory.create();
    }

    /**
     * Adds a few test alarms into the AlarmClockService.
     */
    public void fillServiceWithAlarms() {
        // set the first alarm to now + delay
        Calendar now = Calendar.getInstance();
        timeInMs = Calendar.getInstance().getTimeInMillis() + alarmDelay;
        now.setTimeInMillis(timeInMs);
        alarmClockService.setAlarm(now);
        alarmClockService.startAlarm(now);
        alarms.add(now.getTimeInMillis());

        // set the second one now + 100*delay
        timeInMs = Calendar.getInstance().getTimeInMillis() + 100*alarmDelay;
        now.setTimeInMillis(timeInMs);
        alarmClockService.setAlarm(now);
        alarmClockService.startAlarm(now);
        alarms.add(now.getTimeInMillis());
    }

    /**
     * Tests if the SOAP server returns all alarms.
     */
    @Test
    public void getAlarms() {
        System.out.println("Testing getAlarms()...");

        // base test - get one of the pre-added alarms
        ArrayList<Long> res = soapService.getAlarms();
        assertEquals(alarms.size(),res.size());
        assertTrue(res.contains(alarms.get(0)));
    }

    /**
     * Tests if a specific alarm can be retrieved from the SOAP server.
     */
    @Test
    public void getAlarm() {
        System.out.println("Testing getAlarm()...");

        HashMap<Long,Boolean> res = soapService.getAlarm(alarms.get(0).toString());
        assertNotNull(res);
    }

    /**
     * Tests whether alarms can be added via the SOAP server.
     */
    @Test
    public void postAlarm() {
        System.out.println("Testing postAlarm()...");
        Calendar now = Calendar.getInstance();
        now.set(Calendar.MONTH,Calendar.DECEMBER);

        int result = soapService.postAlarm(String.valueOf(now.getTimeInMillis()));
        assertEquals(0,result);

        assertTrue(soapService.getAlarms().contains(now.getTimeInMillis()));
    }

    /**
     * Tests whether alarms can be deleted via the SOAP server.
     */
    @Test
    public void delAlarm() {
        System.out.println("Testing delAlarm()...");

        Calendar deleteMe = Calendar.getInstance();
        deleteMe.set(Calendar.MONTH,Calendar.DECEMBER);
        // first add an alarm
        int result = soapService.postAlarm(String.valueOf(deleteMe.getTimeInMillis()));
        assertEquals(0,result);

        // then delete it
        result = soapService.delAlarm(String.valueOf(deleteMe.getTimeInMillis()));
        assertEquals(0,result);

        // see if it's gone
        assertFalse(soapService.getAlarms().contains(deleteMe.getTimeInMillis()));
    }
}
