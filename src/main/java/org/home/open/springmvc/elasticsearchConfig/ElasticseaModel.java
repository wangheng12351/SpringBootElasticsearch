package org.home.open.springmvc.elasticsearchConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="es")
public class ElasticseaModel {
//	url: 192.168.1.186
//	port: 9300
//	clustername: TEST-ES
	String url;
	int port;
	String clustername;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getClustername() {
		return clustername;
	}
	public void setClustername(String clustername) {
		this.clustername = clustername;
	}
	
	
}
