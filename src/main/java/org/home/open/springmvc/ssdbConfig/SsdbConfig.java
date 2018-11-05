package org.home.open.springmvc.ssdbConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hyd.ssdb.SsdbClient;
import com.hyd.ssdb.conf.SPOFStrategy;
import com.hyd.ssdb.conf.Server;

@Configuration
public class SsdbConfig {
	@Autowired SsdbModel ssdbModel;
	
	@Bean(name="ssdb1")
	public SsdbClient client1() {
		Server server = new Server(ssdbModel.getHost1(),ssdbModel.getPort1(),"",true,ssdbModel.getSocketTimeout(),ssdbModel.getMinIdle(),ssdbModel.getMaxIdle(),ssdbModel.getPoolMaxTotal());
		SsdbClient client = new SsdbClient(server, SPOFStrategy.PreserveKeySpaceStrategy);
		return client;
	}
	@Bean(name="ssdb2")
	public SsdbClient client2() {
		Server server = new Server(ssdbModel.getHost2(),ssdbModel.getPort2(),"",true,ssdbModel.getSocketTimeout(),ssdbModel.getMinIdle(),ssdbModel.getMaxIdle(),ssdbModel.getPoolMaxTotal());
		SsdbClient client = new SsdbClient(server, SPOFStrategy.PreserveKeySpaceStrategy);
		return client;
	}
}
