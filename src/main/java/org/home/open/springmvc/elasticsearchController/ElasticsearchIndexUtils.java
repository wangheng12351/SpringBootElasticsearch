package org.home.open.springmvc.elasticsearchController;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.InternalAggregations;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filter.InternalFilter;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/***********
 * 练习index
 *
 * @author wh
 *
 *         创建日期：2018年4月27日 创建时间：下午3:11:39
 ************
 */
@RestController
public class ElasticsearchIndexUtils {
	Logger _log = LoggerFactory.getLogger("System");
	@Autowired
	private TransportClient client;
	
	/********************************
	 * 接口列表
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/listApi")
	@ResponseBody
	public ResponseEntity listApi() {
		Map<String, String> _Map = new HashMap<String, String>();
		_Map.put("接口URL/cpUserStatisNew", "接口参数：servicecode&beginsubmittime&endsubmittime  实例： http://192.168.1.186/channelStatisNew?channelid=1&beginsubmittime=2017-09-26 00:16:55.00&endsubmittime=2018-09-29 03:16:55.00");
		_Map.put("接口URL/channelStatisNew", "接口参数：channelid&beginsubmittime&endsubmittime  实例：http://192.168.1.186/cpUserStatisNew?servicecode=000002&beginsubmittime=2017-09-2 00:16:55.00&endsubmittime=2018-09-29 03:16:55.00");
		
		return new ResponseEntity(_Map, HttpStatus.OK);
	}

	/****************
	 * TermsAggregation and FilterAggregationBuilder
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/FilterAggregationBuilde")
	@ResponseBody
	public ResponseEntity termsQuery() {
		BoolQueryBuilder _BoolQueryBuilder = QueryBuilders.boolQuery();
		RangeQueryBuilder dateTime = QueryBuilders.rangeQuery("smsmtsubmittime");
		dateTime.lte("2018-09-30T13:32:37+0800").gte("2018-05-25T13:32:37+0800");
		_BoolQueryBuilder.must(dateTime);
		QueryStringQueryBuilder _qQueryBuilder1 = QueryBuilders.queryStringQuery("status:0");
		QueryStringQueryBuilder _qQueryBuilder2 = QueryBuilders.queryStringQuery("-status:0");
		QueryStringQueryBuilder _qQueryBuilder3 = QueryBuilders.queryStringQuery("sendstatus:0");
		QueryStringQueryBuilder _qQueryBuilder4 = QueryBuilders.queryStringQuery("-sendstatus:0");
		FilterAggregationBuilder _FilterAggregationBuilder1 = AggregationBuilders.filter("sendSuccessCount", _qQueryBuilder1);
		FilterAggregationBuilder _FilterAggregationBuilder2 = AggregationBuilders.filter("sendFailCount", _qQueryBuilder2);
		FilterAggregationBuilder _FilterAggregationBuilder3 = AggregationBuilders.filter("statusReportSuccessCount", _qQueryBuilder3);
		FilterAggregationBuilder _FilterAggregationBuilder4 = AggregationBuilders.filter("statusReportFailCount", _qQueryBuilder4);
		TermsAggregationBuilder aggregations = AggregationBuilders.terms("cpuserservicecode").field("servicecode").size(100);
		aggregations.subAggregation(_FilterAggregationBuilder1).subAggregation(_FilterAggregationBuilder2).subAggregation(_FilterAggregationBuilder3).subAggregation(_FilterAggregationBuilder4);

		Map<String, Object> _Map = new HashMap<String, Object>();
		SearchResponse scrollResp = this.client.prepareSearch("home_platform_background").setTypes("sendallinfo").addSort("id", SortOrder.ASC).setFrom(0).setSize(5).setQuery(_BoolQueryBuilder).addAggregation(aggregations).get();
		if (!scrollResp.isTimedOut()) {
			Aggregations _Aggregations = scrollResp.getAggregations();
			for (Aggregation aggregation : _Aggregations) {
				System.out.println(aggregation.getName());
			}
			Terms _Terms = _Aggregations.get("cpuserservicecode");
			List<? extends Bucket> _BucketS = _Terms.getBuckets();
			for (Terms.Bucket bucket : _BucketS) {
				_Map.put(bucket.getKey() + "", bucket.getDocCount());
				_log.info("查询到的客户id：" + bucket.getKeyAsString() + "   总条数" + bucket.getDocCount() + "");
				InternalAggregations _AggregationTemp = (InternalAggregations) bucket.getAggregations();
				_AggregationTemp.asList();
				for (Aggregation aggregation : _AggregationTemp) {
					InternalFilter _InternalFilter = (InternalFilter) aggregation;
					_log.info("过滤分组" + _InternalFilter.getName() + "=" + _InternalFilter.getDocCount() + "");
				}
			}
		} else {
			_log.info(scrollResp.status().getStatus() + "");
		}
		return new ResponseEntity(_Map, HttpStatus.OK);
	}

	// 简单查询match查询 精确查询
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/match")
	@ResponseBody
	public ResponseEntity match() {
		// match 模糊查询
		// match_phrase 精确查询
//			MatchQueryBuilder _MatchPhrasePrefixQueryBuilder = QueryBuilders.matchQuery("smscontent", "3896887105");
//			MatchPhrasePrefixQueryBuilder _MatchPhrasePrefixQueryBuilder = QueryBuilders.matchPhrasePrefixQuery("smscontent", "【容大友信】CMPP更新测试长短信少时诵诗书少时诵诗书所属1阿萨飒飒我去我驱蚊器奥撒所按时asas按时啊是阿萨飒飒是阿萨飒飒是阿萨飒飒");
//			MatchPhraseQueryBuilder _MatchPhrasePrefixQueryBuilder =QueryBuilders.matchPhraseQuery("smscontent", "CMPP更新测试长短信少时诵诗书少时诵诗书所属1阿萨飒飒我去我驱蚊器奥撒所按时asas按时啊是阿萨飒飒是阿萨飒飒是阿萨飒飒");
//			MatchAllQueryBuilder _MatchPhrasePrefixQueryBuilder = QueryBuilders.matchAllQuery();
		BoolQueryBuilder _BoolQueryBuilder = QueryBuilders.boolQuery();
		MatchQueryBuilder match1 = QueryBuilders.matchQuery("number", "15810934276");
		MatchQueryBuilder match2 = QueryBuilders.matchQuery("smscontent", "超值套盒");
		_BoolQueryBuilder.must(match1).must(match2);
//			MultiMatchQueryBuilder _MatchPhrasePrefixQueryBuilder = QueryBuilders.multiMatchQuery("超值套盒", "smscontent");
		Map<String, JSONObject> _Map = new HashMap<String, JSONObject>();
		long totall = 0;

		SearchResponse scrollResp = this.client.prepareSearch("home_platform_background").setTypes("sendallinfo").addSort("id", SortOrder.ASC).setFrom(0).setSize(5).setQuery(_BoolQueryBuilder).get();
		if (!scrollResp.isTimedOut()) {
			totall = scrollResp.getHits().getTotalHits();
			_log.info("查询的总条数=" + totall);
			for (SearchHit hit : scrollResp.getHits().getHits()) {
				_log.info(hit.getSourceAsString());
				JSONObject _sendInfo = JSON.parseObject(hit.getSourceAsString());
				_Map.put(_sendInfo.getString("id"), _sendInfo);

			}
		} else {
			_log.info(scrollResp.status().getStatus() + "");
		}
		return new ResponseEntity(_Map, HttpStatus.OK);
	}

	// 简单查询 team查询 精确查询
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/getSimple")
	@ResponseBody
	public ResponseEntity Simple() {

		// Query termsQuery用法
//		TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("country", "比利时", "德国");
//		// Search
//		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
//		searchRequestBuilder.setTypes(type);
//		searchRequestBuilder.setQuery(termsQueryBuilder);
		// 执行
//		SearchResponse searchResponse = searchRequestBuilder.get();

		Map<String, JSONObject> _Map = new HashMap<String, JSONObject>();
		long totall = 0;
		BoolQueryBuilder _BoolQueryBuilder = QueryBuilders.boolQuery();
		TermQueryBuilder _TermQueryBuilder = QueryBuilders.termQuery("sendstatus", "0");
		TermQueryBuilder _TermQueryBuilder2 = QueryBuilders.termQuery("number", "15810934276");
		RangeQueryBuilder _RangeQueryBuilder = QueryBuilders.rangeQuery("smsmtsubmittime").lte("2018-09-30T13:32:37+0800").gte("2018-09-10T13:32:37+0800");// 大于小于用法
		_BoolQueryBuilder.must(_TermQueryBuilder).must(_TermQueryBuilder2).must(_RangeQueryBuilder);

		SearchResponse scrollResp = this.client.prepareSearch("home_platform_background").setTypes("sendallinfo").addSort("id", SortOrder.ASC).setFrom(0).setSize(10).setQuery(_BoolQueryBuilder).get();
		if (!scrollResp.isTimedOut()) {
			totall = scrollResp.getHits().getTotalHits();
			_log.info("查询的总条数=" + totall);
			for (SearchHit hit : scrollResp.getHits().getHits()) {
				_log.info(hit.getSourceAsString());
				JSONObject _sendInfo = JSON.parseObject(hit.getSourceAsString());
				_Map.put(_sendInfo.getString("id"), _sendInfo);

			}
		} else {
			_log.info(scrollResp.status().getStatus() + "");
		}
		return new ResponseEntity(_Map, HttpStatus.OK);
	}

	// 求最大值，最小值，AggregationBuilders的用法
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/minormax")
	@ResponseBody
	public ResponseEntity MinOrMax() {
		Map<String, Double> _Map = new HashMap<String, Double>();
		SearchResponse sr = this.client.prepareSearch("home_platform_background").setTypes("sendallinfo").setQuery(QueryBuilders.matchAllQuery()).addAggregation(AggregationBuilders.min("min").field("channelid")).get();
		if (!sr.isTimedOut()) {
			Min agg = sr.getAggregations().get("min");
			double value = agg.getValue();
			_log.info("min_" + value);
			_Map.put("min", value);
		} else {
			_log.info(sr.status().getStatus() + "");
		}

		SearchResponse sr1 = client.prepareSearch("home_platform_background").setTypes("sendallinfo").setQuery(QueryBuilders.matchAllQuery()).addAggregation(AggregationBuilders.max("max").field("channelid")).get();
		if (!sr1.isTimedOut()) {
			Max agg1 = sr1.getAggregations().get("max");
			double value1 = agg1.getValue();
			_log.info("max" + value1);
			_Map.put("max", value1);
		}
		{
			_log.info(sr1.status().getStatus() + "");
		}
		return new ResponseEntity(_Map, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/QueryAggregations")
	@ResponseBody
	public ResponseEntity QueryAggregations() {
		Map<String, Object> _Map = new HashMap<String, Object>();
		SearchResponse sr = this.client.prepareSearch("home_platform_background").setTypes("sendallinfo").setQuery(QueryBuilders.matchQuery("sendstatus", "0")).addAggregation(AggregationBuilders.terms("agg1").field("statusvalue")).addAggregation(AggregationBuilders.terms("agg2").field("channelid")).get();

		// 解析分组数据
		Terms agg1 = sr.getAggregations().get("agg1");
		List<? extends Bucket> _Aggregation = agg1.getBuckets();
		for (Bucket bucket : _Aggregation) {
			_Map.put(bucket.getKeyAsString(), bucket.getDocCount());
			_log.info(bucket.getDocCount() + "");
			_log.info(bucket.getKeyAsString());
		}
		Terms agg2 = sr.getAggregations().get("agg2");
		List<? extends Bucket> _Aggregation2 = agg2.getBuckets();
		for (Bucket bucket : _Aggregation2) {
			_Map.put(bucket.getKeyAsString(), bucket.getDocCount());
			_log.info(bucket.getDocCount() + "");
			_log.info(bucket.getKeyAsString());
		}

//			Histogram agg2 = sr.getAggregations().get("agg2");
		return new ResponseEntity(_Map, HttpStatus.OK);
	}

	// 同时执行预加载
	@GetMapping("/mult_query")
	public Map<String, Object> mult_query() {
		Map<String, Object> _Map = new HashMap<String, Object>();
		SearchRequestBuilder srb1 = this.client.prepareSearch("home_platform_background").setTypes("sendallinfo").setQuery(QueryBuilders.queryStringQuery("UNDELIVRD")).setSize(6);
		SearchRequestBuilder srb2 = this.client.prepareSearch("home_platform_background").setTypes("sendallinfo").setQuery(QueryBuilders.matchQuery("statusvalue", "UNDELIVRD")).setSize(6);

		MultiSearchResponse sr = this.client.prepareMultiSearch().add(srb1).add(srb2).get();

		for (MultiSearchResponse.Item item : sr.getResponses()) {
			SearchResponse response = item.getResponse();
			for (SearchHit hit : response.getHits().getHits()) {
				_log.info(hit.getSourceAsString());
				JSONObject _sendInfo = JSON.parseObject(hit.getSourceAsString());
				_Map.put(_sendInfo.getString("id"), _sendInfo);
			}
		}
		return _Map;
	}

	/***********
	 * Scroll 用法
	 * 
	 * @return
	 */
	@GetMapping("/ScrollQueryFetchSearchResult")
	public Map<String, Object> ScrollQueryFetchSearchResult() {
		Map<String, Object> _Map = new HashMap<String, Object>();
		QueryBuilder qb = QueryBuilders.termQuery("number", "15810934276");

		SearchResponse scrollResp = this.client.prepareSearch("home_platform_background").setTypes("sendallinfo").addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC).setScroll(new TimeValue(60000)).setQuery(qb).setSize(100).get(); // max of 100 hits will be returned for each scroll
		do {
			for (SearchHit hit : scrollResp.getHits().getHits()) {
				_log.info(hit.getSourceAsString());
				JSONObject _sendInfo = JSON.parseObject(hit.getSourceAsString());
				_Map.put(_sendInfo.getString("id"), _sendInfo);
			}

			scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
		} while (scrollResp.getHits().getHits().length != 0);
		return _Map;
	}

	/**************************
	 * 查询
	 * 
	 * @param number
	 * @param status
	 * @param id
	 * @return
	 */
	@GetMapping("/queryApi")
	public Map<String, Object> queryApi(@RequestParam(name = "number", defaultValue = "", required = false) String number, @RequestParam(name = "status", defaultValue = "", required = false) String status, @RequestParam(name = "id", defaultValue = "", required = false) String id) {
		Map<String, Object> _Map = new HashMap<String, Object>();
		SearchRequestBuilder _SearchRequestBuilder = client.prepareSearch("home_platform_background").setTypes("sendallinfo").setSearchType(SearchType.QUERY_THEN_FETCH);// Query;

		if (number != null && number.length() == 11) {
			_SearchRequestBuilder.setQuery(QueryBuilders.termQuery("number", number));
		}
		if (status != null && status.length() > 0) {
			_SearchRequestBuilder.setQuery(QueryBuilders.termQuery("status", status));
		}
		if (id != null && id.length() > 0) {
			_SearchRequestBuilder.setQuery(QueryBuilders.termQuery("id", id));
		}
		SearchResponse response = _SearchRequestBuilder.setFrom(0).setSize(60).setExplain(true).get();
		SearchHits _queryApi1 = response.getHits();
		for (SearchHit searchHit : _queryApi1) {
			_log.info(searchHit.getSourceAsString());
			JSONObject _sendInfo = JSON.parseObject(searchHit.getSourceAsString());
			_Map.put(_sendInfo.getString("id"), _sendInfo);
		}
		return _Map;
	}

	// Update By Query API
	public static void Update_By_Query_API(TransportClient client) {
//		UpdateByQueryRequestBuilder updateByQuery = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
//		updateByQuery.source("twitter")
//		.abortOnVersionConflict(true);
//		BulkByScrollResponse response = updateByQuery.get();
//		System.out.println(response.getStatus());
		UpdateByQueryRequestBuilder updateByQuery = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
		updateByQuery.source("twitter").size(100).source().addSort("postDate", SortOrder.DESC);
		BulkByScrollResponse response = updateByQuery.get();
		System.out.println(response.toString());

	}

//	Bulk_API
	public static void Bulk_API(TransportClient client) throws Exception {
		BulkRequestBuilder bulkRequest = client.prepareBulk();

		// either use client#prepare, or use Requests# to directly build index/delete
		// requests
		bulkRequest.add(client.prepareIndex("twitter", "tweet", "1").setSource(XContentFactory.jsonBuilder().startObject().field("user", "kimchy").field("postDate", new Date()).field("message", "trying out Elastic真是好search").endObject()));

		bulkRequest.add(client.prepareIndex("twitter", "tweet", "2").setSource(XContentFactory.jsonBuilder().startObject().field("user", "kimchy").field("postDate", new Date()).field("message", "another post").endObject()));

		BulkResponse bulkResponse = bulkRequest.get();
		if (bulkResponse.hasFailures()) {
			System.out.println("process failures by iterating through each bulk response item");
		}
	}

	// 多文件查询
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
	@GetMapping("/deleteById")
	public String delete(@RequestParam(name = "id", defaultValue = "") String id) {
		// 删除
		// DeleteResponse response = client.prepareDelete("wangheng",
		// "wangheng1", "1").get();
		// System.out.println(response.getResult());
		// Delete By Query API
		// addIndex(client);
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		queryBuilder.must(QueryBuilders.termQuery("id", id));

		BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(this.client).filter(queryBuilder).source("sendallinfo").get();
		long deleted = response.getDeleted();
		System.out.println(deleted);
		System.out.println(response.getStatus());
		return response.toString();
	}

	public static void main(String[] args) throws Exception {
//	TransportClient client = client();
//	// 添加
//	// addIndex(client);
//	// 更新
//	// updaet(client);
//	// 多get
////	multGet(client);
//	//Bulk_API
////	Bulk_API(client);
//	//Update By Query API
////	Update_By_Query_API(client);
////	queryApi1(client);
////	ScrollQueryFetchSearchResult(client);
////	mult_query(client);
////	QUeryAggregations(client);
////	minormax(client);
//	QueryBuilders.queryStringQuery("min");
//	QueryBuilders.matchQuery("sendstatus", "0");
//	QueryBuilders.termQuery("sendstatus", "0");
//	QueryBuilders.multiMatchQuery("0", "sendstatus","sendstatus");
//	QueryBuilders.commonTermsQuery("sendstatus", "0");
//	QueryBuilders.rangeQuery("channelid").from(0).to(10).includeLower(false).includeUpper(true).lte(100).gte(900);
//	Simple(client);
//	
////	BoolQueryBuilder _QueryBuilders = QueryBuilders.boolQuery();
////	_QueryBuilders.must(QueryBuilders.matchQuery("sendstatus", "0"));
////	_QueryBuilders.must(QueryBuilders.matchQuery("number", "15810934276"));
////	_QueryBuilders.must(QueryBuilders.rangeQuery("createtime").lte("10").gte("100"));
		TermsAggregationBuilder _TermsAggregationBuilder = AggregationBuilders.terms("aggsass2").field("srcnumber");
		System.out.println(_TermsAggregationBuilder.toString());
//	
//
	}

}
