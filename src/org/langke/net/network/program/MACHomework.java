package org.langke.net.network.program;

/*
 FileName:MACHomework.java
 Author:流浪小子
 Date:2004-7-5
 E-mail:qiyadeng@hotmail.com
 Purpose:获取本地主机的MAC地址
 */
import java.io.*;
import java.util.*;

public class MACHomework {
	static private final int MACLength = 18;

	public static void main(String args[]) {
		System.out.print("本机的物理地址是：");
		System.out.println(getMACAddress());
	}

	static public String getMACAddress() {
		SysCommand syscmd = new SysCommand();
		// 系统命令
		String cmd = "cmd.exe /c ipconfig/all";
		Vector result;
		result = syscmd.execute(cmd);
		return getCmdStr(result.toString());
	}

	static public String getCmdStr(String outstr) {
		String find = "Physical Address. . . . . . . . . :";
		int findIndex = outstr.indexOf(find);
		if (findIndex == -1) {
			return "未知错误！";
		} else {
			return outstr.substring(findIndex + find.length() + 1, findIndex
					+ find.length() + MACLength);
		}
	}
}

class SysCommand {
	Process p;

	public Vector execute(String cmd) {
		try {
			Start(cmd);
			Vector vResult = new Vector();
			DataInputStream in = new DataInputStream(p.getInputStream());
			BufferedReader myReader = new BufferedReader(new InputStreamReader(
					in));
			String line;
			do {
				line = myReader.readLine();
				if (line == null) {
					break;
				} else {
					vResult.addElement(line);
				}
			} while (true);
			myReader.close();
			return vResult;
		} catch (Exception e) {
			return null;

		}

	}

	public void Start(String cmd) {
		try {
			if (p != null) {
				kill();
			}
			Runtime sys = Runtime.getRuntime();
			p = sys.exec(cmd);

		} catch (Exception e) {

		}
	}

	public void kill() {
		if (p != null) {
			p.destroy();
			p = null;
		}
	}

}
