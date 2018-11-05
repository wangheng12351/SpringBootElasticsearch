package org.home.open.springmvc.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/***********
 * 练习index
 *
 * @author wh
 *
 *         创建日期：2018年4月27日 创建时间：下午3:11:39
 ************
 */
public class TestIndex {
	public static void main(String[] args) throws Exception {
		TransportClient client = client();
		// 添加
		// addIndex(client);
		// 更新
		// updaet(client);
		// 多get
//		multGet(client);
		//Bulk_API
//		Bulk_API(client);
		//Update By Query API
//		Update_By_Query_API(client);
//		queryApi1(client);
//		ScrollQueryFetchSearchResult(client);
//		mult_query(client);
//		QUeryAggregations(client);
//		minormax(client);
		QueryBuilders.queryStringQuery("min");
		QueryBuilders.matchQuery("sendstatus", "0");
		QueryBuilders.termQuery("sendstatus", "0");
		QueryBuilders.multiMatchQuery("0", "sendstatus","sendstatus");
		QueryBuilders.commonTermsQuery("sendstatus", "0");
		QueryBuilders.rangeQuery("channelid").from(0).to(10).includeLower(false).includeUpper(true).lte(100).gte(900);
		Simple(client);
		
//		BoolQueryBuilder _QueryBuilders = QueryBuilders.boolQuery();
//		_QueryBuilders.must(QueryBuilders.matchQuery("sendstatus", "0"));
//		_QueryBuilders.must(QueryBuilders.matchQuery("number", "15810934276"));
//		_QueryBuilders.must(QueryBuilders.rangeQuery("createtime").lte("10").gte("100"));
//		TermsAggregationBuilder  _TermsAggregationBuilder = AggregationBuilders.terms("agg2").field("srcnumber");
////				.subAggregation(AggregationBuilders.avg("ant").field("number"));
//		System.out.println(_TermsAggregationBuilder.toString());
		

	}
	public static void Simple(TransportClient client){
		SearchResponse scrollResp = client.prepareSearch("home_platform_background").setTypes("sendallinfo")
				.addSort("createtime", SortOrder.ASC)
				.setFrom(0)
				.setSize(10)
				.setQuery(QueryBuilders.boolQuery()
						.must(QueryBuilders.termQuery("sendstatus", "0"))
						.must(QueryBuilders.termQuery("number", "15810934276"))
						)
//				.setQuery(QueryBuilders.boolQuery()
//						.mustNot(QueryBuilders.matchPhraseQuery("sendstatus", "0"))
//						.must(QueryBuilders.matchPhraseQuery("number", "15810934276"))
////						.should(QueryBuilders.matchPhraseQuery(name, text))
//						)
//				.setQuery(QueryBuilders.matchQuery("number", "15810934276"))
//			    .setQuery(QueryBuilders.matchQuery("sendstatus", "0"))
//			    .setQuery(QueryBuilders.matchQuery("smsid", "1145576586181343"))
			    .get();
		 for (SearchHit hit : scrollResp.getHits().getHits()) {
		    	System.out.println(hit.getSourceAsString());
		    }
	}
	//求最大值，最小值，求和
	public static void minormax(TransportClient client){
		        
		SearchResponse sr = client.prepareSearch("home_platform_background").setTypes("sendallinfo")
			    .setQuery(QueryBuilders.matchAllQuery())
			    .addAggregation(
			    		AggregationBuilders
		                .min("min")
		                .field("channelid")
			    )
//			    .addAggregation(
//			            AggregationBuilders.dateHistogram("agg2")
//			                    .field("birth")
//			                    .dateHistogramInterval(DateHistogramInterval.YEAR)
//			    )
			    .get();
		Min agg = sr.getAggregations().get("min");
		double value = agg.getValue();
		System.out.println("min_"+value);
		SearchResponse sr1 = client.prepareSearch("home_platform_background").setTypes("sendallinfo")
			    .setQuery(QueryBuilders.matchAllQuery())
			    .addAggregation(
			    		AggregationBuilders
		                .max("max")
		                .field("channelid").subAggregation(AggregationBuilders.terms("").field("sendstatus"))
			    )
//			    .addAggregation(
//			            AggregationBuilders.dateHistogram("agg2")
//			                    .field("birth")
//			                    .dateHistogramInterval(DateHistogramInterval.YEAR)
//			    )
			    .get();
		Max agg1 = sr1.getAggregations().get("max");
		double value1 = agg1.getValue();
		System.out.println("max_"+value1);
		
	}
	public static void  QUeryAggregations(TransportClient client) {
		SearchResponse sr = client.prepareSearch("home_platform_background").setTypes("sendallinfo")
			    .setQuery(QueryBuilders.matchQuery("sendstatus", "0"))
			    .addAggregation( AggregationBuilders.terms("agg1").field("statusvalue") )
			    .addAggregation( AggregationBuilders.terms("agg2").field("srcnumber"))
//			    .addAggregation(AggregationBuilders.avg("avg").field("number").subAggregation(aggregation))
//			    .addAggregation(
//			            AggregationBuilders.dateHistogram("agg2")
//			                    .field("birth")
//			                    .dateHistogramInterval(DateHistogramInterval.YEAR)
//			    )
			    .setSize(100)
			    .setFrom(0)
			    .get();

			// Get your facet results
		
		//解析分组数据
			Terms agg1 = sr.getAggregations().get("agg1");
			List<? extends Bucket> _Aggregation = agg1.getBuckets();
			for (Bucket bucket : _Aggregation) {
				System.out.println(bucket.getDocCount());
				System.out.println(bucket.getKeyAsString());
			}
			Terms agg2 = sr.getAggregations().get("agg2");
			List<? extends Bucket> _Aggregation2 = agg2.getBuckets();
			for (Bucket bucket : _Aggregation2) {
				System.out.println(bucket.getDocCount());
				System.out.println(bucket.getKeyAsString());
			}
			
//			Histogram agg2 = sr.getAggregations().get("agg2");
	}
	public static void mult_query(TransportClient client) {
		SearchRequestBuilder srb1 = client
			    .prepareSearch("home_platform_background").setTypes("sendallinfo").setQuery(QueryBuilders.queryStringQuery("UNDELIVRD")).setSize(1);
			SearchRequestBuilder srb2 = client
					.prepareSearch("home_platform_background").setTypes("sendallinfo").setQuery(QueryBuilders.matchQuery("statusvalue", "UNDELIVRD")).setSize(1);

			MultiSearchResponse sr = client.prepareMultiSearch()
			        .add(srb1)
			        .add(srb2)
			        .get();
			long nbHits = 0;
			for (MultiSearchResponse.Item item : sr.getResponses()) {
			    SearchResponse response = item.getResponse();
			    nbHits += response.getHits().getTotalHits();
			    System.out.println(nbHits);
			}
	}
	public static void ScrollQueryFetchSearchResult(TransportClient client){
		QueryBuilder qb = QueryBuilders.termQuery("statusvalue", "UNDELIVRD");

		SearchResponse scrollResp = client.prepareSearch("home_platform_background")
				.setTypes("sendallinfo")
		        .addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
		        .setScroll(new TimeValue(60000))
		        .setQuery(qb)
		        .setSize(100).get(); //max of 100 hits will be returned for each scroll
		do {
		    for (SearchHit hit : scrollResp.getHits().getHits()) {
		    	System.out.println(hit.getSourceAsString());
		    }

		    scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
		 // Zero hits mark the end of the scroll and the while loop.
		} while(scrollResp.getHits().getHits().length != 0); 
	}
	public static void queryApi1(TransportClient client) {
		SearchResponse response = client.prepareSearch("twitter")
		        .setTypes("tweet")
		        .setSearchType(SearchType.QUERY_THEN_FETCH)
		        .setQuery(QueryBuilders.termQuery("user", "kimchy"))                 // Query
		        .setQuery(QueryBuilders.termQuery("message", "trying"))
//		        .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
		        .setFrom(0).setSize(60).setExplain(true)
		        .get();
		SearchHits _queryApi1 = response.getHits();
//		response.getScrollId();
		for (SearchHit searchHit : _queryApi1) {
			System.out.println(searchHit.getSourceAsString());
		}
	}
	//Update By Query API
	public static void Update_By_Query_API(TransportClient client) {
//		UpdateByQueryRequestBuilder updateByQuery = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
//		updateByQuery.source("twitter")
//		.abortOnVersionConflict(true);
//		BulkByScrollResponse response = updateByQuery.get();
//		System.out.println(response.getStatus());
		UpdateByQueryRequestBuilder updateByQuery = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
		updateByQuery.source("twitter").size(100)
		    .source().addSort("postDate", SortOrder.DESC);
		BulkByScrollResponse response = updateByQuery.get();
		System.out.println(response.toString());
		
	}
//	Bulk_API
	public static void Bulk_API(TransportClient client) throws Exception {
		BulkRequestBuilder bulkRequest = client.prepareBulk();

		// either use client#prepare, or use Requests# to directly build index/delete requests
		bulkRequest.add(client.prepareIndex("twitter", "tweet", "1")
		        .setSource(XContentFactory.jsonBuilder()
		                    .startObject()
		                        .field("user", "kimchy")
		                        .field("postDate", new Date())
		                        .field("message", "trying out Elastic真是好search")
		                    .endObject()
		                  )
		        );

		bulkRequest.add(client.prepareIndex("twitter", "tweet", "2")
		        .setSource(XContentFactory.jsonBuilder()
		                    .startObject()
		                        .field("user", "kimchy")
		                        .field("postDate", new Date())
		                        .field("message", "another post")
		                    .endObject()
		                  )
		        );

		BulkResponse bulkResponse = bulkRequest.get();
		if (bulkResponse.hasFailures()) {
		    System.out.println("process failures by iterating through each bulk response item");
		}
	}

	public static void multGet(TransportClient client) {
		MultiGetResponse multiGetItemResponses = client.prepareMultiGet().add("wangheng", "wangheng1", "1").add("twitter", "tweet", "2", "3", "4").add("another", "type", "foo").get();

		for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
			GetResponse response = itemResponse.getResponse();
			if (response.isExists()) {
				String json = response.getSourceAsString();
				System.out.println(json);
			}
		}
	}

	// Update API
	public static void updaet(TransportClient client) throws IOException, InterruptedException, ExecutionException {
		// 第一种
		// UpdateRequest updateRequest = new UpdateRequest();
		// updateRequest.index("wangheng");
		// updateRequest.type("wangheng1");
		// updateRequest.id("1");
		// updateRequest.doc(XContentFactory.jsonBuilder()
		// .startObject()
		// .field("gender", "我是王恒")
		// .endObject());
		// client.update(updateRequest).get();
		// 第二种
		IndexRequest indexRequest = new IndexRequest("wangheng", "wangheng1", "1").source(XContentFactory.jsonBuilder().startObject().field("name", "我是王恒呀").field("name", "我是王恒呀").field("gender", "male").endObject());
		UpdateRequest updateRequest = new UpdateRequest("wangheng", "wangheng1", "1").doc(XContentFactory.jsonBuilder().startObject().field("gender", "男").field("name", "我是王恒呀撒啊飒飒").endObject()).upsert(indexRequest);
		client.update(updateRequest).get();
	}

	public static TransportClient client() throws UnknownHostException {
		InetSocketTransportAddress nodeAddress = new InetSocketTransportAddress(InetAddress.getByName("192.168.1.186"), 9300);
		Settings _Settings = Settings.builder().put("cluster.name", "TEST-ES").build();
		TransportClient client = new PreBuiltTransportClient(_Settings);
		client.addTransportAddress(nodeAddress);
		return client;
	}

	public static void addIndex(TransportClient client) throws Exception {
		// // 第一种
		// String json = "{" + "\"user\":\"kimchy\"," +
		// "\"postDate\":\"2013-01-30\"," +
		// "\"message\":\"trying out Elasticsearch\"" + "}";
		// // 第二种
		// Map<String, Object> json2 = new HashMap<String, Object>();
		// json2.put("user", "kimchy");
		// json2.put("postDate", new Date());
		// json2.put("message", "trying out Elasticsearch");
		//
		// // 第三种
		// // instance a json mapper
		// ObjectMapper mapper = new ObjectMapper(); // create once, reuse
		//
		// // generate json
		// byte[] json3 = mapper.writeValueAsBytes(json2);
		// System.out.println(new String(json3));
		// // 第四种
		// XContentBuilder builder =
		// XContentFactory.jsonBuilder()
		// .startObject().field("user", "kimchy")
		// .field("postDate", new Date())
		// .field("message", "trying out Elasticsearch")
		// .endObject();
		// System.out.println(builder.string());
		// 第五种
		// IndexResponse response = client.prepareIndex("wangheng", "wangheng1",
		// "1")
		// .setSource(XContentFactory.jsonBuilder()
		// .startObject()
		// .field("user", "kimchy")
		// .field("postDate", new Date())
		// .field("message", "trying out Elasticsearch")
		// .endObject()
		// )
		// .get();
		// System.out.println(response.getIndex());

		// 第六种
		// IndexResponse response = client.prepareIndex("wangheng", "wangheng1",
		// "4")
		// .setSource(json, XContentType.JSON)
		// .get();
		// System.out.println(response.status().getStatus());
		// get
		// GetResponse response = client.prepareGet("wangheng", "wangheng1",
		// "1").get();
		// System.out.println(response.getSourceAsString()+"--");
		// System.out.println(response.getField("user")+"--");
		IndexResponse response = client.prepareIndex("wangheng", "wangheng1", "1").setSource(XContentFactory.jsonBuilder().startObject().field("user", "kimchy").field("postDate", new Date()).field("message", "trying out Elasticsearch").endObject()).get();
		System.out.println(response);
	}

	// 删除
	public void delete(TransportClient client) {
		// 删除
		// DeleteResponse response = client.prepareDelete("wangheng",
		// "wangheng1", "1").get();
		// System.out.println(response.getResult());
		// Delete By Query API
		// addIndex(client);
		BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(client).filter(QueryBuilders.matchQuery("user", "kimchy")).source("wangheng").get();
		long deleted = response.getDeleted();
		System.out.println(deleted);
		System.out.println(response.getStatus());
		// 下面的没有实验成功
		// DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
		// .filter(QueryBuilders.matchQuery("user", "kimchy"))
		// .source("wangheng")
		// .execute(new ActionListener<BulkByScrollResponse>() {
		// @Override
		// public void onResponse(BulkByScrollResponse response) {
		// long deleted = response.getDeleted();
		// System.out.println(deleted);
		// }
		// @Override
		// public void onFailure(Exception e) {
		// e.printStackTrace();
		// }
		// });
	}

}
