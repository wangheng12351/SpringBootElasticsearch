package org.home.open.springmvc.elasticsearchConfig;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {
	@Autowired ElasticseaModel elasticseaModel;
	@Bean
	public TransportClient client() throws UnknownHostException {
		InetSocketTransportAddress nodeAddress = new InetSocketTransportAddress(InetAddress.getByName(elasticseaModel.getUrl()), elasticseaModel.getPort());
		Settings _Settings = Settings.builder()
				.put("cluster.name", elasticseaModel.getClustername())
//              .put("client.transport.sniff", true) // 开启嗅探 , 开启后会一直连接不上, 原因未知
				.put("client.transport.ignore_cluster_name", true) // 忽略集群名字验证, 打开后集群名字不对也能连接上
				.build();
		TransportClient client = new PreBuiltTransportClient(_Settings);
		client.addTransportAddress(nodeAddress);
		return client;
	}
}
