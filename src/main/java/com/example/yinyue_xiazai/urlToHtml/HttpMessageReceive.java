package com.example.yinyue_xiazai.urlToHtml;
 
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
 
 
 
 
import jpcap.*;
import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
 
 
 
public class HttpMessageReceive implements PacketReceiver {
	

	public static String[] HttpInfoStr = {"0","0","0","0","0","0","0","0"};
	public static final String[] HTTPStart = {"GET","POST","OPTIONS"}; 	//HTTP协议有效信息开始的三个标志
	public static final String[] StrLabel = { "TimeOnLine", "SRC_MAC","DST_MAC", "SRC_IP", "DST_IP", "GETInfo", "RefererInfo", "HostInfo" };
		
	public static void main(String[] args) throws Exception {
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		if(args.length<1){
			System.out.println("usage: java Tcpdump <select a number from the following>");			
			for (int i = 0; i < devices.length; i++) {
				System.out.print(i+" :"+devices[i].name + "(" + devices[i].description+")");
				System.out.println("data link:"+devices[i].datalink_name + "("
						+ devices[i].datalink_description+")");
				System.out.print("MAC address:");
				for (byte b : devices[i].mac_address)
					System.out.print(Integer.toHexString(b&0xff) + ":");	
					System.out.println("");
				for (NetworkInterfaceAddress a : devices[i].addresses)
					System.out.println("address:"+a.address + " " + a.subnet + " "
							+ a.broadcast);
			}
		}else{
			JpcapCaptor jpcap = JpcapCaptor.openDevice(devices[Integer.parseInt(args[0])], 2000, false, 20);
			jpcap.setFilter("tcp", true);    //设置过滤规则，只抓取tcps数据包
			jpcap.loopPacket(-1, new HttpMessageReceive());
		}
	}
	public void receivePacket(Packet packet) {			
			try {							
				String TimeOnLine = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
				HttpInfoStr[0] = TimeOnLine;    //时间戳			
				TCPPacket tcpPacket = (TCPPacket) packet;					
				EthernetPacket ethernetPacket = (EthernetPacket) packet.datalink;				
				HttpInfoStr[1] = ethernetPacket.getSourceAddress();                	//SRC_MAC：
				HttpInfoStr[2] = ethernetPacket.getDestinationAddress();			//DST_MAC
				HttpInfoStr[3] = tcpPacket.src_ip.toString().substring(1);						//SRC_IP
				HttpInfoStr[4] = tcpPacket.dst_ip.toString().substring(1);						//DST_IP					
				String HTTPData = new String(tcpPacket.data,"utf-8");
				if(HTTPData.startsWith(HTTPStart[0])){  //GET方法访问					
					int GETInfoStart = 0;
					int GETInfoEnd = HTTPData.indexOf("\r\n",GETInfoStart);
					String GETInfo = HTTPData.substring(GETInfoStart, GETInfoEnd);
					HttpInfoStr[5] = GETInfo;
 
					int RefererInfoStart = HTTPData.indexOf("Referer");
					int RefererInfoEnd = HTTPData.indexOf("\r\n",RefererInfoStart);
					String RefererInfo = HTTPData.substring(RefererInfoStart, RefererInfoEnd);
					HttpInfoStr[6] = RefererInfo;
					
					int HostInfoStart = HTTPData.indexOf("Host");
					int HostInfoEnd = HTTPData.indexOf("\r\n",HostInfoStart);
					String HostInfo = HTTPData.substring(HostInfoStart, HostInfoEnd);
					HttpInfoStr[7] = HostInfo;				
				}else if(HTTPData.startsWith(HTTPStart[1])){ //POST方法访问
					//后续改进
					
				}	
				
				//将sbIPv4Data,GETInfo，HostInfo，RefererInfo信息写入文件DetailHTTPData.xls
				if(!"0".equals(HttpInfoStr[6])&& !"0".equals(HttpInfoStr[7])){   //当Host和Referer不为空时写入文件
						
						String sheetName = HttpInfoStr[0].substring(0, 8);
						int indexStrLabel = 0;
						int indexHttpInfo = 0;
						
						
							System.out.println(sheetName);
							while(indexStrLabel < StrLabel.length){
								System.out.println(indexStrLabel);
								System.out.println(StrLabel[indexStrLabel]);
								System.out.println(indexHttpInfo);
								System.out.println(HttpInfoStr[indexHttpInfo]);
								indexStrLabel++;
							}
							while(indexHttpInfo < HttpInfoStr.length ){
								System.out.println(indexHttpInfo);
								System.out.println(HttpInfoStr[indexHttpInfo]);
								System.out.println(HttpInfoStr[indexHttpInfo]);
								indexHttpInfo++;
							}

							
						
				}			
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
	}
}
