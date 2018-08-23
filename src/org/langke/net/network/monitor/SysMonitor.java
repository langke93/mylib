package org.langke.net.network.monitor;
 

import java.util.*;
import java.io.*; 

import org.hyperic.sigar.Sigar;

public class SysMonitor extends Thread{
 
	   private static final int CPUTIME = 1000;

	   private static final int PERCENT = 100;

	   private static final int FAULTLENGTH = 10;

	   private static String osVersion,osName = null;

		private List<Long> memList =Collections.synchronizedList( new ArrayList<Long>()) ;
		private List<Double> cpuList = Collections.synchronizedList(new ArrayList<Double>());
		private long interval = 1000;//间隔时间
		private volatile boolean stopRequested;
	    private Thread runThread;
	public List<Long> getMemList() {
			return memList;
		}
		public long getInterval() {
		return interval;
	}
	public void setInterval(long interval) {
		this.interval = interval;
	}
		public void setMemList(List<Long> memList) {
			this.memList = memList;
		}
		public List<Double> getCpuList() {
			return cpuList;
		}
		public void setCpuList(List<Double> cpuList) {
			this.cpuList = cpuList;
		}
	public void run()	{
		runThread = Thread.currentThread();
		stopRequested = false;
		while( !stopRequested){
			long memoryUsed = getMemoryUsed();
			double cpuRatio = getSigarCpuUse();//getCpuRatio();getSigarCpuUse();
			memList.add(memoryUsed);
			cpuList.add(cpuRatio);
			try {
				Thread.sleep(1*interval);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt(); // re-assert interrupt 
				e.printStackTrace();
			}
		}
	}
    public void stopRequest() {
        stopRequested = true;
        if ( runThread != null ) {
        	//System.out.println(runThread.getState());
        	try {
				runThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            //runThread.interrupt();
        }
    }
	public  long getMemoryUsed(){
		Runtime runtime = Runtime.getRuntime();
		int unit = 1;//1024*1024;
		double freeMemory = (double)runtime.freeMemory()/(unit);
		double totalMemory = (double)runtime.totalMemory()/(unit);
		double usedMemory = totalMemory - freeMemory;
		return (long)usedMemory;
	}
	/**
	 * 使用sigar.jar取cpu利用率
	 * @return x%
	 */
	public double getSigarCpuUse(){
		 Sigar sigar = null;
		    double cpuRatio = 0;
	        try {
	            sigar = new Sigar();
	            cpuRatio = (sigar.getCpuPerc().getUser()* PERCENT);
	        } catch (Throwable t) {
	            t.printStackTrace();
	        } finally {
	            if (sigar != null)
	                sigar.close();
	        }
	        return cpuRatio;
	}
	/**
	 * 取cpu利用率，推荐使用getSigarCpuUse，此方法比较耗cpu
	 * @return
	 */
	@Deprecated
    public double getCpuRatio(){
        // 操作系统
        osName = System.getProperty("os.name");
        osVersion = System.getProperty("os.version");
        //System.out.println(osName+" : "+osVersion);
    	// 获得cpu频率
	    double cpuRatio = 0;
	    if (osName.toLowerCase().startsWith("windows")) {
	     cpuRatio = this.getCpuRatioForWindows();
	    } else {
	     cpuRatio = this.getCpuRateForLinux();
	    }   	
	    return cpuRatio;
    }
	  /**
	   * 获得Linux下CPU使用率.
	   * 
	   * @return 返回Linux下cpu使用率
	   */
	   private double getCpuRateForLinux() {
	    InputStream is = null;
	    InputStreamReader isr = null;
	    BufferedReader brStat = null;
	    StringTokenizer tokenStat = null;
	    try {
	    

	     Process process = Runtime.getRuntime().exec("top -b -n 1");
	     is = process.getInputStream();
	     isr = new InputStreamReader(is);
	     brStat = new BufferedReader(isr);

	     if (osVersion.startsWith("2.4")) {
	      brStat.readLine();
	      brStat.readLine();
	      brStat.readLine();
	      brStat.readLine();

	      tokenStat = new StringTokenizer(brStat.readLine());
	      tokenStat.nextToken();
	      tokenStat.nextToken();
	      String user = tokenStat.nextToken();
	      tokenStat.nextToken();
	      String system = tokenStat.nextToken();
	      tokenStat.nextToken();
	      String nice = tokenStat.nextToken();

	      user = user.substring(0, user.indexOf("%"));
	      system = system.substring(0, system.indexOf("%"));
	      nice = nice.substring(0, nice.indexOf("%"));

	      float userUsage = new Float(user).floatValue();
	      float systemUsage = new Float(system).floatValue();
	      float niceUsage = new Float(nice).floatValue();

	      return (userUsage + systemUsage + niceUsage) / 100;
	     } else {
	      brStat.readLine();
	      brStat.readLine();
	      String line = brStat.readLine();
	      System.out.println("tokenStat:"+line);
	      tokenStat = new StringTokenizer(line);
	      tokenStat.nextToken();
	      tokenStat.nextToken();
	      tokenStat.nextToken();
	      tokenStat.nextToken();
	      String cpuUsage = tokenStat.nextToken();

	      Float usage = new Float(cpuUsage.substring(0, cpuUsage.indexOf("%")));
	      System.out.println("usage:"+usage);
	      return ((1 - usage.floatValue() / 100) * 100);
	     }

	    } catch (IOException ioe) {
	     ioe.printStackTrace();
	     freeResource(is, isr, brStat);
	     return 1;
	    } finally {
	     freeResource(is, isr, brStat);
	    }

	   }

	   private static void freeResource(InputStream is, InputStreamReader isr,
	     BufferedReader br) {
	    try {
	     if (is != null)
	      is.close();
	     if (isr != null)
	      isr.close();
	     if (br != null)
	      br.close();
	    } catch (IOException ioe) {
	     System.out.println(ioe.getMessage());
	    }
	   }

	   /**
	   * 获得windows下CPU使用率.
	   * 
	   * @return 返回windows下cpu使用率
	   */
	   private double getCpuRatioForWindows() {
	    try {
	     String procCmd = System.getenv("windir")
	       + "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,"
	       + "KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
	     // 取进程信息
	     long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
	     Thread.sleep(CPUTIME);
	     long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
	     if (c0 != null && c1 != null) {
	      long idletime = c1[0] - c0[0];
	      long busytime = c1[1] - c0[1];
	      return Double.valueOf(
	        PERCENT * (busytime) / (busytime + idletime))
	        .doubleValue();
	     } else {
	      return 0.0;
	     }
	    } catch (Exception ex) {
	     ex.printStackTrace();
	     return 0.0;
	    }
	   }

	   /**
	   * 
	   * 读取CPU信息.
	   * 
	   * @param proc
	   * @return
	   */
	   private long[] readCpu(final Process proc) {
	    long[] retn = new long[2];
	    try {
	     proc.getOutputStream().close();
	     InputStreamReader ir = new InputStreamReader(proc.getInputStream());
	     LineNumberReader input = new LineNumberReader(ir);
	     String line = input.readLine();
	     if (line == null || line.length() < FAULTLENGTH) {
	      return null;
	     }
	     int capidx = line.indexOf("Caption");
	     int cmdidx = line.indexOf("CommandLine");
	     int rocidx = line.indexOf("ReadOperationCount");
	     int umtidx = line.indexOf("UserModeTime");
	     int kmtidx = line.indexOf("KernelModeTime");
	     int wocidx = line.indexOf("WriteOperationCount");
	     long idletime = 0;
	     long kneltime = 0;
	     long usertime = 0;
	     while ((line = input.readLine()) != null) {
	      if (line.length() < wocidx) {
	       continue;
	      }
	      // 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,
	      // ThreadCount,UserModeTime,WriteOperation
	      String caption =  Bytes.substring(line, capidx, cmdidx - 1)
	        .trim();
	      String cmd = Bytes.substring(line, cmdidx, kmtidx - 1).trim();
	      if (cmd.indexOf("wmic.exe") >= 0) {
	       continue;
	      }
	      // log.info("line="+line);
	      if (caption.equals("System Idle Process")
	        || caption.equals("System")) {
	       idletime += Long.valueOf(
	         Bytes.substring(line, kmtidx, rocidx - 1).trim())
	         .longValue();
	       idletime += Long.valueOf(
	         Bytes.substring(line, umtidx, wocidx - 1).trim())
	         .longValue();
	       continue;
	      }

	      kneltime += Long.valueOf(
	        Bytes.substring(line, kmtidx, rocidx - 1).trim())
	        .longValue();
	      usertime += Long.valueOf(
	        Bytes.substring(line, umtidx, wocidx - 1).trim())
	        .longValue();
	     }
	     retn[0] = idletime;
	     retn[1] = kneltime + usertime;
	     return retn;
	    } catch (Exception ex) {
	     ex.printStackTrace();
	    } finally {
	     try {
	      proc.getInputStream().close();
	     } catch (Exception e) {
	      e.printStackTrace();
	     }
	    }
	    return null;
	   }

	   public float[] getLinuxMemInfo() {
	    File file = new File("/proc/meminfo");  
	    float result[] = new float[4];
	    try {
	     BufferedReader br = new BufferedReader(new InputStreamReader(
	       new FileInputStream(file)));
	     String str = null;
	     StringTokenizer token = null;
	     while ((str = br.readLine()) != null) {

	      token = new StringTokenizer(str);
	      if (!token.hasMoreTokens()) {
	       continue;
	      }
	      str = token.nextToken();

	      if (!token.hasMoreTokens()) {
	       continue;
	      }
	      if (str.equalsIgnoreCase("MemTotal:")) {
	       result[0] = Long.parseLong(token.nextToken());

	      }
	      if (str.equalsIgnoreCase("Buffers:")) {
	       result[1] = Long.parseLong(token.nextToken());

	      }
	      if (str.equalsIgnoreCase("Cached:")) {
	       result[2] = Long.parseLong(token.nextToken());

	      }
	      if (str.equalsIgnoreCase("MemFree:")) {
	       result[3] = Long.parseLong(token.nextToken());

	      }
	     }
	    } catch (FileNotFoundException e) {
	     e.printStackTrace();
	    } catch (IOException e) {
	     e.printStackTrace();
	    }
	    return result;
	   }

	    public static String substring(String src, int start_idx, int end_idx){    
	        byte[] b = src.getBytes();    
	        String tgt = "";    
	        for(int i=start_idx; i<=end_idx; i++){    
	            tgt +=(char)b[i];    
	        }    
	        return tgt;    
	    }  

		/**
		 * @param args
		 */
		public static void main(String[] args) {
			SysMonitor monitor = new SysMonitor();
			System.out.println("getMemoryUsed:"+monitor.getMemoryUsed());
			//System.out.println("getCpuRatio:"+monitor.getCpuRatio());
			System.out.println(monitor.getSigarCpuUse());

			int size;
			monitor.start();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("mem:"+monitor.getMemList());
			Collections.sort(monitor.getMemList());
			size = monitor.getMemList().size();
			if(size!=0){
				size = size-1;
				System.out.println("memMaxUse:"+monitor.getMemList().get(size)+" byte");
			}
			System.out.println("cpu:"+monitor.getCpuList());
			Collections.sort(monitor.getCpuList());
			size = monitor.getCpuList().size();
			if(size!=0){
				size = size-1;
				System.out.println("cpuMaxUse:"+monitor.getCpuList().get(size)+"%");
			}
		}
}
