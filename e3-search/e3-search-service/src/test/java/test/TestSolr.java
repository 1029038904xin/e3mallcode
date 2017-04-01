package test;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolr {
	
	@Test
	public void testSolrj(){
		String zkHost="192.168.242.128:2281,192.168.242.128:2282,192.168.242.128:2283";
		CloudSolrServer cloudSolrServer = new CloudSolrServer(zkHost);
		cloudSolrServer.setDefaultCollection("collection2");
		SolrInputDocument doc = new SolrInputDocument();
		
		
		
	}
	

}
