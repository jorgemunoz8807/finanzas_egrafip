package citmatel.cu.class_Pack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ClassPack.jWMI;

public class SerialNumberGetter {
	
	public static boolean isWindows(String OS) {
		return (OS.indexOf("win") >= 0);
	}

	public static boolean isLinux(String OS) {
		//return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
		return (OS.indexOf("nux") >= 0);
	}
		
	/**
	 * Determina la dirección MAC de una máquina (con sistema Windows o Linux).
	 * @return La dirección MAC. Retorna 'null' en caso de que ocurra algún error ejecutando el comando que permite determinar dicha dirección.
	 * @throws Exception Si el sistema actual no es ni Windows ni Linux.
	 */
	public static String getMACAddress() throws Exception
	{
		String OS = System.getProperty("os.name").toLowerCase();
		if(isWindows(OS))
		{
			//System.out.println("OS found: Windows");
			try {
				return getMACInWindows();
			} catch (IOException e) {
				return null;
			}}
		if(isLinux(OS))
		{
			//System.out.println("OS found: Linux");
			try {
				return getMACInLinux();
			} catch (Exception e) {
				return null;
			}
		}
	
		throw new Exception ("imposible to get this Id for this OS");
		
	}
	
	private static Pattern macPattern = Pattern.compile("[0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:]"
	            + "[0-9a-fA-F]{2}[-:][0-9a-fA-F]{2}[-:][0 -9a-fA-F]{2}[-:][0-9a-fA-F]{2}");
	
	public static String getMACInWindows() throws IOException 
	{
		String mac = null;
		//String command = "ipconfig /all";
		String command = "getmac /fo csv /nh";
	    Process p = null;
		p = Runtime.getRuntime().exec(command);
		Scanner sc = new Scanner(p.getInputStream());        
        String line = "null";
        int i = 0;
        String[]splittedLine = null;
        while(sc.hasNext())
        {
        	line = sc.next();
        	i++;
        	splittedLine = line.split(",");
        
        	Matcher mat = macPattern.matcher(splittedLine[0]);
        	if (mat.find()) {
        		mac = mat.group();
        		break;
        	}
        }
        
       // return mac!=null?mac:"none";
        return mac;
	}
	
	public static String getMACInLinux() throws Exception
	{
		return (String)getLinuxMACAddresses().get(0);
	}
	
	private static List getLinuxMACAddresses() throws Exception {
       
            Process conf = Runtime.getRuntime().exec("/sbin/ifconfig");
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(conf.getInputStream()));
            return getMACAddresses(input);
       
    }
	
	private static List getMACAddresses(BufferedReader input) throws Exception {
        List MACs = new ArrayList(1);
        String theLine;
        while ((theLine = input.readLine()) != null) {
            String[] ss = macPattern.split(theLine);
            for (int p = 0; p < ss.length; p++) {
                String s = theLine.substring(theLine.indexOf(ss[p]) + ss[p].length()).trim();
                if (!s.isEmpty()) {
                    String s1 = s.replaceAll("-", ":");
                    String s2 = s1.substring(0, s1.lastIndexOf(':') + 3);
                    if (s2.length() == 16 || s2.length() == 17) {
                        MACs.add(s2);
                    }
                }
            }
        }
        return MACs;
    }
	
	/**
	 * Determina el número de serie de la motherboard de una máquina (con sistema Windows o Linux).
	 * @return El número de serie de la motherboard. 
	 * Retorna 'null' en caso de que ocurra algún error ejecutando el comando que permite determinar dicho número. 
	 * @throws Exception Si el sistema actual no es ni Windows ni Linux.
	 */
	public static String getMotherboardSN() throws Exception
	{
		String OS = System.getProperty("os.name").toLowerCase();
		if(isWindows(OS))
		{
			//System.out.println("OS found: Windows");
			try {
				return getMBInWindows();
			} catch (Exception e) {
				return null;
			}}
		if(isLinux(OS))
		{
			//System.out.println("OS found: Linux");
			try {
				return getMBInLinux();
			} catch (IOException e) {
				return null;
			}
		}
		throw new Exception ("Invalid operation for this OS");
	}
	private static String getMBInWindows() throws Exception {
	return jWMI.getWMIValue("Select * from Win32_BaseBoard", "SerialNumber");
	}

	private static String getMBInLinux() throws IOException {
            Process p = Runtime.getRuntime().exec("cat /sys/devices/virtual/dmi/id/board_serial");
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            String mb;
            while ((mb = input.readLine()) != null) {
            	if(!mb.equals(""))
            		return mb;
                }
            return null;
    }
	
	/**
	 * Determina el número de serie del micro de una máquina (con sistema Windows).
	 * @return El número de serie del micro. 
	 * Retorna 'null' en caso de que ocurra algún error ejecutando el comando que permite determinar dicho número o para Linux. 
	 * @throws Exception Si el sistema actual no es ni Windows ni Linux.
	 */
	public static String getProcessorSN() throws Exception {
		String OS = System.getProperty("os.name").toLowerCase();
		if(isWindows(OS))
		{try {
			return jWMI.getWMIValue("Select * from Win32_Processor", "ProcessorId");
		} catch (Exception e) {
			return null;
		}
		}
		
		if(isLinux(OS))
			return null;
		
		throw new Exception ("Invalid operation for this OS");
		}
	
	/**
	 * Determina el número de serie del disco duro de una máquina (con sistema Windows).
	 * @return El número de serie del disco duro. 
	 * Retorna 'null' en caso de que ocurra algún error ejecutando el comando que permite determinar dicho número o para Linux. 
	 * @throws Exception Si el sistema actual no es ni Windows ni Linux.
	 */
	public static String getHDDSN() throws Exception {
		String OS = System.getProperty("os.name").toLowerCase();
		if(isWindows(OS)){
		try {
			return jWMI.getWMIValue("Select * from Win32_PhysicalMedia", "SerialNumber");
		} catch (Exception e) {
			return null;
		}
		}
		
		if(isLinux(OS))
			return null;
		
		throw new Exception ("Invalid operation for this OS");
		}
	
	
	private static void executeDemoQueries()
	{
		try
		{
			
			System.out.println(jWMI.getWMIValue("Select * from Win32_DiskDrive", "DeviceId"));
			System.out.println(jWMI.getWMIValue("Select * from Win32_DiskDrive", "Model"));
			System.out.println(jWMI.getWMIValue("Select * from Win32_PhysicalMedia", "SerialNumber"));
			System.out.println(jWMI.getWMIValue("Select * from Win32_BaseBoard", "SerialNumber"));
			//System.out.println(getWMIValue("Select * from Win32_BIOS", "ProcessorId"));
			System.out.println(jWMI.getWMIValue("Select * from Win32_Processor", "ProcessorId"));
			System.out.println(jWMI.getWMIValue("Select * from Win32_NetworkAdapter", "Description"));
			//System.out.println(getWMIValue("Select Name from Win32_ComputerSystem", "Name"));
			//System.out.println(getWMIValue("Select Description from Win32_PnPEntity", "Description"));
			//System.out.println(getWMIValue("Select Description, Manufacturer from Win32_PnPEntity", "Description,Manufacturer"));
			//System.out.println(getWMIValue("Select * from Win32_Service WHERE State = 'Stopped'", "Name"));
			//this will return everything since the field is incorrect and was not used to a filter
			//System.out.println(getWMIValue("Select * from Win32_Service", "Name"));
			//this will return nothing since there is no field specified
			//System.out.println(getWMIValue("Select Name from Win32_ComputerSystem", ""));
			//this is a failing case where the Win32_Service class does not contain the 'Name' field
			//System.out.println(getWMIValue("Select * from Win32_Service", "Name"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	 /**
	  * Determina la MAC actual de la máquina
	  * @return Un string con la MAC
	 * @throws UnknownHostException 
	 * @throws SocketException 
	  */
	 public static String getMACTest() throws UnknownHostException, SocketException
	 {
		 InetAddress ip = InetAddress.getLocalHost();
			System.out.println("Current IP address: " + ip.getHostAddress());
			
		NetworkInterface network = NetworkInterface.getByInetAddress(ip);
		//NetworkInterface.getNetworkInterfaces();
		 
		byte[] mac = network.getHardwareAddress();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
		}
	
		return sb.toString();
	 }
	 
	 protected static String formatMac(byte[] mac) {
	        if (mac == null)
	            return "UNKNOWN";
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < mac.length; i++) {
	            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
	        }
	        return sb.toString();
	    }

	   public static String getMACOffline() throws Exception {
	       for(Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements();)
	       {
	           NetworkInterface ni = e.nextElement();
	           byte[] hardwareAddress = ni.getHardwareAddress();
	           
	           if(hardwareAddress != null)
	           {
	        	  String formattedMac = formatMac(hardwareAddress);
	        	  return formattedMac;
	           }
	       }
	       return "none";
	   }

	
	
	
	
}
