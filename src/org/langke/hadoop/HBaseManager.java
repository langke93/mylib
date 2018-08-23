package org.langke.hadoop;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.NavigableMap;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * http://labs.chinamobile.com/mblog/4110_22332
 */
public class HBaseManager {
	final static String hbase_master = "hadoop2:2181";
	final static String hbase_zookeeper_quorum="hadoop2";
	public static void main(String[] args) throws Exception {
		HBaseManager manager = new HBaseManager();
		manager.test();
		manager.testGet();
		//manager.testQueryRS();
		//manager.testQueryCommodity();
		//manager.testScanner();
	}

	public void test() throws Exception {
		HBaseConfiguration config = new HBaseConfiguration();
		//config.set("hbase.master", hbase_master);
		config.set("hbase.zookeeper.property.clientPort", "2181");
		config.set("hbase.zookeeper.quorum", hbase_zookeeper_quorum);
		HBaseAdmin admin = new HBaseAdmin(config);

		if (admin.tableExists("scores")) {
			System.out.println("drop table");
			admin.disableTable("scores");
			admin.deleteTable("scores");
		}

		System.out.println("create table");
		HTableDescriptor tableDescripter = new HTableDescriptor("scores".getBytes());
		tableDescripter.addFamily(new HColumnDescriptor("grade"));
		tableDescripter.addFamily(new HColumnDescriptor("course"));

		admin.createTable(tableDescripter);

		System.out.println("add Tom's data");

		Put tomPut = new Put(new String("Tom").getBytes());
		tomPut.add(new String("grade").getBytes(), new byte[] {}, new String("1").getBytes());
		tomPut.add(new String("grade").getBytes(), new String("math").getBytes(), new String("87").getBytes());
		tomPut.add(new String("course").getBytes(), new String("math").getBytes(), new String("97").getBytes());

		HTable table = new HTable(config, "scores");
		table.put(tomPut);

		System.out.println("add Jerry's data");

		Put jerryPut = new Put(new String("Jerry").getBytes());
		jerryPut.add(new String("grade").getBytes(), new byte[] {}, new String("2").getBytes());
		jerryPut.add(new String("grade").getBytes(), new String("math").getBytes(), new String("77").getBytes());
		jerryPut.add(new String("course").getBytes(), new String("math").getBytes(), new String("92").getBytes());
		table.put(jerryPut);

		System.out.println("Get Tom's data");
		Get tomGet = new Get(new String("Tom").getBytes());
		Result tomResult = table.get(tomGet);
		System.out.println(tomResult.toString());

		System.out.println("Get Tom's Math grade");
		Get mathGet = new Get(new String("Tom").getBytes());
		mathGet.addColumn(Bytes.toBytes("grade"));
		mathGet.setMaxVersions();
		Result rs = table.get(mathGet);

		NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> nMap = rs.getMap();
		NavigableMap<byte[], NavigableMap<Long, byte[]>> columnMap = nMap.get(Bytes.toBytes("grade"));
		NavigableMap<Long, byte[]> qualMap = columnMap.get(Bytes.toBytes("math"));

		for (Map.Entry<Long, byte[]> m : qualMap.entrySet()) {
			System.out.println("TimeStamp:" + m.getKey());
			System.out.println("Value:" + new String(m.getValue()));
		}

		System.out.println("Delete a column");
		Delete deleteArt = new Delete(Bytes.toBytes("Tom"));
		deleteArt.deleteColumn(Bytes.toBytes("grade"), Bytes.toBytes("math"));
		deleteArt.deleteColumn(Bytes.toBytes("grade"),Bytes.toBytes(""));
		table.delete(deleteArt);
	}

	public void testGet() throws IOException {
		HBaseConfiguration config = new HBaseConfiguration();
		//config.set("hbase.master", hbase_master);
		config.set("hbase.zookeeper.property.clientPort", "2181");
		config.set("hbase.zookeeper.quorum", hbase_zookeeper_quorum);
		final String table_name = "commodity";

		HBaseAdmin admin = new HBaseAdmin(config);
		admin.disableTable(table_name);
		admin.deleteTable(table_name);
		if(!admin.tableExists(table_name)){
			System.out.println("create table");
			HTableDescriptor tableDescripter = new HTableDescriptor(table_name.getBytes());
			tableDescripter.addFamily(new HColumnDescriptor("widgetname"));
			tableDescripter.addFamily(new HColumnDescriptor("filename"));
			tableDescripter.addFamily(new HColumnDescriptor("description"));
			tableDescripter.addFamily(new HColumnDescriptor("createtime"));
			admin.createTable(tableDescripter);
		}
		
		HTable table = new HTable(config, table_name);

		Put Spin = new Put(new String("Spin").getBytes());
		Spin.add(new String("widgetname").getBytes(), new String("name").getBytes(), new String("widgetname1").getBytes());
		Spin.add(new String("filename").getBytes(), new String("name").getBytes(), new String("filename1").getBytes());
		Spin.add(new String("filename").getBytes(), new String("name2").getBytes(), new String("undefined1").getBytes());
		Spin.add(new String("description").getBytes(), new byte[] {}, new String("description1").getBytes());
		Spin.add(new String("createtime").getBytes(), new byte[] {}, new String("createtime1").getBytes());
		table.put(Spin);
		
		Get get = new Get(new String("Spin").getBytes());
		get.addColumn(Bytes.toBytes("widgetname"));
		get.addColumn(Bytes.toBytes("filename"));
		get.addColumn(Bytes.toBytes("description"));
		get.addColumn(Bytes.toBytes("createtime"));

		get.setMaxVersions(2);
		System.out.println("00000000000000");
		Result dbResult = table.get(get);

		System.out.println("11111111111111");
		System.out.println(dbResult.size());
		System.out.println("2222222222222222");
		System.out.println(new String(dbResult.value()));
		System.out.println("3333333333333333");
		System.out.println(dbResult.containsColumn(
				Bytes.toBytes("description"), new byte[] {}));
		System.out.println("44444444444444444");
		System.out.println(dbResult.isEmpty());
		System.out.println("55555555555555555");
		System.out.println(dbResult.list());
		System.out.println("66666666666666666");
		System.out.println(dbResult.raw());
		System.out.println("77777777777777777");
		System.out.println(dbResult.toString());
		System.out.println("88888888888888888");
		System.out.println(dbResult.raw().clone());
		System.out.println("99999999999999999");
	}
	
	public void testQueryRS() throws Exception {
		HBaseConfiguration config = new HBaseConfiguration();
		//config.set("hbase.master", hbase_master);
		config.set("hbase.zookeeper.property.clientPort", "2181");
		config.set("hbase.zookeeper.quorum", hbase_zookeeper_quorum);

		HTable table = new HTable(config, "commodity");

		System.out.println("Get Spin's commodity info");

		Scan scanner = new Scan();
		scanner.addColumn(Bytes.toBytes("description"));
		scanner.setMaxVersions();
		ResultScanner rsScanner = table.getScanner(scanner);
		System.out.println(rsScanner.toString());
		Result rs ;
		while (null != (rs=rsScanner.next())) {
			System.out.println(rs.size());
			NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> nMap = rs.getMap();
			NavigableMap<byte[], NavigableMap<Long, byte[]>> columnMap = nMap.get(Bytes.toBytes("description"));
			NavigableMap<Long, byte[]> qualMap = columnMap.get(new byte[] {});

			if (qualMap.entrySet().size() > 0) {
				System.out.println("---------------------------");
				for (Map.Entry<Long, byte[]> m : qualMap.entrySet()) {
					System.out.println("Value:" + new String(m.getValue()));
				}
			}
		}
	}

	public void testQueryCommodity() throws Exception {
		HBaseConfiguration config = new HBaseConfiguration();
		config.set("hbase.master", hbase_master);
		config.set("hbase.zookeeper.quorum", hbase_zookeeper_quorum);

		HTable table = new HTable(config, "commodity");

		System.out.println("Get Spin's commodity info");
		Get mathGet = new Get(new String("Spin").getBytes());
		mathGet.addColumn(Bytes.toBytes("createtime"));
		mathGet.setMaxVersions();
		Result rs = table.get(mathGet);

		NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> nMap = rs	.getMap();
		NavigableMap<byte[], NavigableMap<Long, byte[]>> columnMap = nMap.get(Bytes.toBytes("createtime"));
		NavigableMap<Long, byte[]> qualMap = columnMap.get(new byte[] {});

		if (qualMap.entrySet().size() > 0) {
			for (Map.Entry<Long, byte[]> m : qualMap.entrySet()) {
				System.out.println("Value:" + new String(m.getValue()));
				break;
			}
		}
	}

	public void testScanner() throws IOException {
		HBaseConfiguration config = new HBaseConfiguration();
		config.set("hbase.master", hbase_master);
		config.set("hbase.zookeeper.quorum", hbase_zookeeper_quorum);

		HTable table = new HTable(config, "commodity");

		System.out.println("Scan commodity info");

		Scan scanner = new Scan();
		scanner.addColumn(Bytes.toBytes("widgetname"));
		scanner.addColumn(Bytes.toBytes("filename"));
		scanner.addColumn(Bytes.toBytes("description"));
		scanner.addColumn(Bytes.toBytes("createtime"));
		// scanner.setMaxVersions();
		// scanner.setMaxVersions(4);
		ResultScanner rsScanner = table.getScanner(scanner);

		Result rs = rsScanner.next();
		for (; null != rs; rs = rsScanner.next()) {
			KeyValue kv = (KeyValue) rs.list().get(0);
			System.out.println("rs.getRow()[" + new String(rs.getRow()) + "]");
			System.out.println("["
					+ new String(rs.getValue(Bytes.toBytes("widgetname"),kv.getQualifier()))
					+ "]");
			System.out.println("["
					+ new String(rs.getValue(Bytes.toBytes("filename"),kv.getQualifier()))
					+ "]");
			System.out.println("["
					+ new String(rs.getValue(Bytes.toBytes("description"),kv.getQualifier()))
					+ "]");
			String timeStr = new String(rs.getValue(Bytes.toBytes("createtime"),kv.getQualifier()));
			System.out.println("[" + timeStr + "]");

			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			try {
				Date after = dateFormat.parse(timeStr);
				System.out.println(after);
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}
	}

}