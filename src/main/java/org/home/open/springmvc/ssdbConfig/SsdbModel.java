package org.home.open.springmvc.ssdbConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ssdb")
public class SsdbModel {
	String host1 = "192.168.1.186";
	int port1 = 8888;
	String host2 = "192.168.1.186";
	int port2 = 8889;
	int socketTimeout = 30000;
	int minIdle = 5;
	int maxIdle = 10;
	int poolMaxTotal = 10;
	int begintime = 10;
	int endtime = 10;
	public String getHost1() {
		return host1;
	}
	public void setHost1(String host1) {
		this.host1 = host1;
	}
	public int getPort1() {
		return port1;
	}
	public void setPort1(int port1) {
		this.port1 = port1;
	}
	public String getHost2() {
		return host2;
	}
	public void setHost2(String host2) {
		this.host2 = host2;
	}
	public int getPort2() {
		return port2;
	}
	public void setPort2(int port2) {
		this.port2 = port2;
	}
	public int getSocketTimeout() {
		return socketTimeout;
	}
	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}
	public int getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}
	public int getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
	public int getPoolMaxTotal() {
		return poolMaxTotal;
	}
	public void setPoolMaxTotal(int poolMaxTotal) {
		this.poolMaxTotal = poolMaxTotal;
	}
	public int getBegintime() {
		return begintime;
	}
	public void setBegintime(int begintime) {
		this.begintime = begintime;
	}
	public int getEndtime() {
		return endtime;
	}
	public void setEndtime(int endtime) {
		this.endtime = endtime;
	}
}
