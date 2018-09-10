function make(JSName) {
	//Java代码中定义的，用来建立SOCKET连接的类
	var KMT2KMS = Java.type("com.kmt.functions.KMTConnectToKMS");
 
	// Java ArrayList类型定义
	var ArrayList = Java.type("java.util.ArrayList");
	/**
	 * Server信息
	 */
	var serverIP = "10.88.104.44";
	var serverPort = 8007;
 
	var keySizeReportTimer = 5000;
	//定义设备数量
        var kmtNum = 3;
	var kmts = new Array(kmtNum);
	for(var i=0; i<kmtNum; i++){
		//每个kmt应该有不同的名字，再for后面的离网时，通过对象名调用离网
		kmts[i] = new KMT2KMS(JSName, serverIP, serverPort);
		kmts[i].sslConnection();//kmt的对象，调用kmt的成员函数
	}
       
        //在JS脚本中如何使用Java ArrayList类型
	for(var i=0; i<kmtNum; i++){
		//定义Java的ArrayList, 
		var keyPairSizeList = new ArrayList();
		keyPairSize = kmtNum;
		for(var j=0; j<keyPairSize; j++){
			var keySizeTargetId = startId + j;
			if(kmts[i].startId == keySizeTargetId){//如果keySizeTargetId与本KMT startId相等，则跳过继续
			    continue;
			}
			var sendKey = 100;
			var recKey = 100;
			var keySizeInfo = new KeySizeInfo(keySizeTargetId, sendKey, recKey);
			keyPairSizeList.add(keySizeInfo)
		}
		//在keySizeReportTimer时间后，执行该函数，在java代码中sleep对应时间
		kmts[i].keySizeReport(keySizeReportTimer, keyPairSizeList);
	}
	
	/**
	 * 设备离网
	 */
	for(var k=0; k<kmtNum; k++){
		kmts[k].kmtDisconnectKMS(3000);
	}
}