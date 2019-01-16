package com.example.yinyue_xiazai.urlToHtml;
 
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import jpcap.*;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
 
public class Test {
 
	public static void main(String[] args) throws UnsupportedEncodingException {
		/*--------------	第一步绑定网络设备       --------------*/
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
 
		for (NetworkInterface n : devices) {
			System.out.println(n.name + "     |     " + n.description);
		}
		System.out.println("-------------------------------------------");
 
		JpcapCaptor jpcap = null;
		int caplen = 1512;
		boolean promiscCheck = true;
 
		try {
			jpcap = JpcapCaptor.openDevice(devices[1], caplen, promiscCheck, 50);
			//0 或 1
		} catch (IOException e) {
			e.printStackTrace();
		}
 
		/*----------第二步抓包-----------------*/
		int i = 0;
		while (i < 1000) {
			Packet packet = jpcap.getPacket();
				
			if(packet!=null){
				
				byte[] hader = packet.header;
				System.out.println();
				System.out.println("解压hader");
				System.out.println();

			       System.out.println(new String(hader));//5
			       
			       
			       String courseName11 = new String(hader, "UTF-8");
			       	String courseName1 = new String(hader,"UTF-8");
					String courseName2 = new String(hader,"GBK");
					String courseName3 = new String(hader,"GB2312");
					String courseName4 = new String(hader,"ISO8859_1");
					String courseName5 = new String(hader,"US-ASCII");
					String courseName6 = new String(hader,"UTF-16BE");
					String courseName7 = new String(hader,"UTF-16LE");
					String courseName8 = new String(hader,"UTF-16");
					String courseName9 = new String(hader,"UTF-8");
					String courseName0 = new String(hader,"UTF-8");
					
					System.out.println(courseName0);
					System.out.println(courseName1);
					System.out.println(courseName2);
					System.out.println(courseName3);
					System.out.println(courseName4);
					System.out.println(courseName5);
					System.out.println(courseName6);
					System.out.println(courseName7);
					System.out.println(courseName8);
					System.out.println(courseName9);
					System.out.println(courseName11);

			       
				System.out.println();
				System.out.println("解压hader");
				System.out.println();
			}
				
				
			if (packet instanceof IPPacket && ((IPPacket) packet).version == 4) {
				i++;
				IPPacket ip = (IPPacket) packet;// 强转
 
				System.out.print("版本：IPv4");
				System.out.print("优先权：" + ip.priority);
				System.out.print("区分服务：最大的吞吐量： " + ip.t_flag);
				System.out.print("区分服务：最高的可靠性：" + ip.r_flag);
				System.out.print("长度：" + ip.length);
				System.out.print("标识：" + ip.ident);
				System.out.print("DF:Don't Fragment: " + ip.dont_frag);
				System.out.print("NF:Nore Fragment: " + ip.more_frag);
				System.out.print("片偏移：" + ip.offset);
				System.out.print("生存时间：" + ip.hop_limit);
 
				String protocol = "";
				switch (new Integer(ip.protocol)) {
				case 1:
					protocol = "ICMP";
					break;
				case 2:
					protocol = "IGMP";
					break;
				case 6:
					protocol = "TCP";
					break;
				case 8:
					protocol = "EGP";
					break;
				case 9:
					protocol = "IGP";
					break;
				case 17:
					protocol = "UDP";
					break;
				case 41:
					protocol = "IPv6";
					break;
				case 89:
					protocol = "OSPF";
					break;
				default:
					break;
				}
				System.out.print("协议：" + protocol);
				System.out.print("源IP " + ip.src_ip.getHostAddress());
				System.out.print("目的IP " + ip.dst_ip.getHostAddress());
				System.out.print("源主机名： " + ip.src_ip);
				System.out.print("目的主机名： " + ip.dst_ip);
				System.out.print("----------------------------------------------");
			}
		}
 
	}
 
}
