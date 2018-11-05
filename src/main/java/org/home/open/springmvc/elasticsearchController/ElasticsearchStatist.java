package org.home.open.springmvc.elasticsearchController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filter.InternalFilter;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.home.open.springmvc.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/***********
 * 练习index
 *
 * @author wh
 *
 *         创建日期：2018年4月27日 创建时间：下午3:11:39
 ************
 */
@RestController
public class ElasticsearchStatist {
	Logger _log = LoggerFactory.getLogger("System");
	@Autowired
	private TransportClient client;

	/**************
	 * 统计客户的状态码分组
	 * 
	 * @param servicecode
	 * @param beginsmsmtsubmittime
	 * @param endsmsmtsubmittime
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/cpuserStatusTerms")
	@ResponseBody
	public ResponseEntity cpuserStatusTerms(@RequestParam(name = "servicecode", defaultValue = "", required = true) String servicecode, @RequestParam(name = "beginsubmittime", defaultValue = "", required = true) String beginsubmittime, @RequestParam(name = "endsubmittime", defaultValue = "", required = true) String endsubmittime)  {
		// 将传递的时间格式yyyy-mm-dd h-m-s转化成2018-09-30T13:32:37+0800
		String begin = DateUtils.dateToUtc(beginsubmittime);
		String end = DateUtils.dateToUtc(endsubmittime);
		// 1:提交总数
		BoolQueryBuilder _BoolQueryBuilder = QueryBuilders.boolQuery();
		TermQueryBuilder _TermQueryBuilder = QueryBuilders.termQuery("servicecode", servicecode);
		RangeQueryBuilder _RangeQueryBuilder = QueryBuilders.rangeQuery("submittime").lte(end).gte(begin);// 大于小于用法
		_BoolQueryBuilder.must(_TermQueryBuilder).must(_RangeQueryBuilder);
		
		Map<String, Object> _Map = new HashMap<String, Object>();
		SearchResponse sr = this.client.prepareSearch("home_platform_background").setTypes("smsreportinfo").setFrom(0).setSize(10000).setQuery(_BoolQueryBuilder).addAggregation(AggregationBuilders.terms("status").size(10000).field("statusvalue")).get();

		// 解析分组数据
		Terms agg1 = sr.getAggregations().get("status");
		List<? extends Bucket> _Aggregation = agg1.getBuckets();
		for (Bucket bucket : _Aggregation) {
			_Map.put(bucket.getKeyAsString(), bucket.getDocCount());
			_log.info("  "+bucket.getKeyAsString() + "-"+bucket.getDocCount());
		}
		
		return new ResponseEntity(_Map, HttpStatus.OK);
	}
	/**************
	 * 统计通道的状态码分组
	 * 
	 * @param servicecode
	 * @param beginsmsmtsubmittime
	 * @param endsmsmtsubmittime
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/channelStatusTerms")
	@ResponseBody
	public ResponseEntity channelStatusTerms(@RequestParam(name = "channelid", defaultValue = "", required = true) String channelid, @RequestParam(name = "beginsubmittime", defaultValue = "", required = true) String beginsubmittime, @RequestParam(name = "endsubmittime", defaultValue = "", required = true) String endsubmittime)  {
		// 将传递的时间格式yyyy-mm-dd h-m-s转化成2018-09-30T13:32:37+0800
		String begin = DateUtils.dateToUtc(beginsubmittime);
		String end = DateUtils.dateToUtc(endsubmittime);
		// 1:提交总数
		BoolQueryBuilder _BoolQueryBuilder = QueryBuilders.boolQuery();
		TermQueryBuilder _TermQueryBuilder = QueryBuilders.termQuery("channelid", channelid);
		RangeQueryBuilder _RangeQueryBuilder = QueryBuilders.rangeQuery("submittime").lte(end).gte(begin);// 大于小于用法
		_BoolQueryBuilder.must(_TermQueryBuilder).must(_RangeQueryBuilder);
		
		Map<String, Object> _Map = new HashMap<String, Object>();
		SearchResponse sr = this.client.prepareSearch("home_platform_background").setTypes("smsreportinfo").setFrom(0).setSize(10000).setQuery(_BoolQueryBuilder).addAggregation(AggregationBuilders.terms("status").size(10000).field("statusvalue")).get();

		// 解析分组数据
		Terms agg1 = sr.getAggregations().get("status");
		List<? extends Bucket> _Aggregation = agg1.getBuckets();
		for (Bucket bucket : _Aggregation) {
			_Map.put(bucket.getKeyAsString(), bucket.getDocCount());
			_log.info("  "+bucket.getKeyAsString() + "-"+bucket.getDocCount());
		}
		

		return new ResponseEntity(_Map, HttpStatus.OK);
	}
	/**************
	 * 统计客户的发送量通过过滤查询汇总
	 * 
	 * @param servicecode
	 * @param beginsmsmtsubmittime
	 * @param endsmsmtsubmittime
	 * @return
	 */
	@GetMapping("/cpUserStatisNew")
	public Map<String, Long> cpUserStatisNew(@RequestParam(name = "servicecode", defaultValue = "", required = true) String servicecode, @RequestParam(name = "beginsubmittime", defaultValue = "", required = true) String beginsubmittime, @RequestParam(name = "endsubmittime", defaultValue = "", required = true) String endsubmittime) {
		long beigntime = System.currentTimeMillis();
		// 前提条件是开始时间和结束时间都有
		// 1:客户提交总数= 查询条件servicecode=000165 historymtinfo
		// 2：提交成功的：status=0
		// 3：提交失败的：-status=0
		// 4：状态总数= 查询条件servicecode=000165 smsreportinfo
		// 5：状态成功的：sendstatus=0
		// 6：状态失败的：-sendstatus=0
		// 7：未知的：提交总数-状态总数

		// 将传递的时间格式yyyy-mm-dd h-m-s转化成2018-09-30T13:32:37+0800
		String begin = DateUtils.dateToUtc(beginsubmittime);
		String end = DateUtils.dateToUtc(endsubmittime);
		Map<String, Long> statisMap = new HashMap<String, Long>();
		// 1:提交总数
		BoolQueryBuilder _BoolQueryBuilder = QueryBuilders.boolQuery();
		TermQueryBuilder _TermQueryBuilder = QueryBuilders.termQuery("servicecode", servicecode);
		RangeQueryBuilder _RangeQueryBuilder = QueryBuilders.rangeQuery("submittime").lte(end).gte(begin);// 大于小于用法
		_BoolQueryBuilder.must(_TermQueryBuilder).must(_RangeQueryBuilder);
		QueryStringQueryBuilder _qQueryBuilder1 = QueryBuilders.queryStringQuery("status:0");
		QueryStringQueryBuilder _qQueryBuilder2 = QueryBuilders.queryStringQuery("-status:0");
		FilterAggregationBuilder _FilterAggregationBuilder1 = AggregationBuilders.filter("sendSuccessCount", _qQueryBuilder1);
		FilterAggregationBuilder _FilterAggregationBuilder2 = AggregationBuilders.filter("sendFailCount", _qQueryBuilder2);
		SearchResponse scrollResp = this.client.prepareSearch("home_platform_background").setTypes("historymtinfo").setFrom(0).setSize(10000).setQuery(_BoolQueryBuilder).addAggregation(_FilterAggregationBuilder1).addAggregation(_FilterAggregationBuilder2).get();
		Long submitTotal = 0L;
		if (!scrollResp.isTimedOut()) {
			submitTotal = scrollResp.getHits().getTotalHits();
			statisMap.put("submitTotal", submitTotal);
			_log.info("客户" + servicecode + "提交总数=" + submitTotal);
			Aggregations _Aggregations = scrollResp.getAggregations();
			for (Aggregation aggregation : _Aggregations) {
				InternalFilter _InternalFilter = (InternalFilter) aggregation;
				statisMap.put(_InternalFilter.getName(), _InternalFilter.getDocCount());
				_log.info("客户" + _InternalFilter.getName() + "数量=" + _InternalFilter.getDocCount());
			}

		} else {
			_log.info(scrollResp.status().getStatus() + "");
		}
		// 2 状态总数
		BoolQueryBuilder _BoolQueryBuilderStatus = QueryBuilders.boolQuery();
		_BoolQueryBuilderStatus.must(_TermQueryBuilder).must(_RangeQueryBuilder);
		QueryStringQueryBuilder _qQueryBuilder3 = QueryBuilders.queryStringQuery("sendstatus:0");
		QueryStringQueryBuilder _qQueryBuilder4 = QueryBuilders.queryStringQuery("-sendstatus:0");
		FilterAggregationBuilder _FilterAggregationBuilder3 = AggregationBuilders.filter("statusSuccessTotal", _qQueryBuilder3);
		FilterAggregationBuilder _FilterAggregationBuilder4 = AggregationBuilders.filter("statusFailTotal", _qQueryBuilder4);
		scrollResp = this.client.prepareSearch("home_platform_background").setTypes("smsreportinfo").setFrom(0).setSize(10000).setQuery(_BoolQueryBuilderStatus).addAggregation(_FilterAggregationBuilder3).addAggregation(_FilterAggregationBuilder4).get();
		Long statusTotal = 0L;
		if (!scrollResp.isTimedOut()) {
			statusTotal = scrollResp.getHits().getTotalHits();
			statisMap.put("statusTotal", statusTotal);
			_log.info("客户" + servicecode + "状态总数=" + statusTotal);
			Aggregations _Aggregations = scrollResp.getAggregations();
			for (Aggregation aggregation : _Aggregations) {
				InternalFilter _InternalFilter = (InternalFilter) aggregation;
				statisMap.put(_InternalFilter.getName(), _InternalFilter.getDocCount());
				_log.info("客户" + _InternalFilter.getName() + "数量=" + _InternalFilter.getDocCount());
			}

		} else {
			_log.info(scrollResp.status().getStatus() + "");
		}
		// 计算未知量
		statisMap.put("statusUnKnowTotal", submitTotal - statusTotal);
		_log.info("客户" + servicecode + "查询完耗时=" + (System.currentTimeMillis() - beigntime) + "毫秒");
		return statisMap;
	}

	/******************
	 * 统计通道的发送未知量通过分组过滤
	 * 
	 * @param channelid
	 * @param beginsmsmtsubmittime
	 * @param endsmsmtsubmittime
	 * @return
	 */
	@GetMapping("/channelStatisNew")
	public Map<String, Long> channelStatisNew(@RequestParam(name = "channelid", defaultValue = "", required = true) String channelid, @RequestParam(name = "beginsubmittime", defaultValue = "", required = true) String beginsubmittime, @RequestParam(name = "endsubmittime", defaultValue = "", required = true) String endsubmittime) {
		long beigntime = System.currentTimeMillis();
		// 前提条件是开始时间和结束时间都有
		// 1:提交总数= 查询条件channelid=1 historymtinfo
		// 2：提交成功的：status=0
		// 3：提交失败的:-status=0
		// 4：状态总数= 查询条件channelid=1 smsreportinfo
		// 5：状态成功的：-sendstatus=0
		// 6：状态失败的：状态总数-成功的
		// 7：未知的：提交总数-状态总数

		// 将传递的时间格式yyyy-mm-dd h-m-s转化成2018-09-30T13:32:37+0800
		String begin = DateUtils.dateToUtc(beginsubmittime);
		String end = DateUtils.dateToUtc(endsubmittime);
		Map<String, Long> statisMap = new HashMap<String, Long>();
		// 1:提交总数
		BoolQueryBuilder _BoolQueryBuilder = QueryBuilders.boolQuery();
		TermQueryBuilder _TermQueryBuilder = QueryBuilders.termQuery("channelid", channelid);
		RangeQueryBuilder _RangeQueryBuilder = QueryBuilders.rangeQuery("createtime").lte(end).gte(begin);// 大于小于用法
		_BoolQueryBuilder.must(_TermQueryBuilder).must(_RangeQueryBuilder);
		QueryStringQueryBuilder _qQueryBuilder1 = QueryBuilders.queryStringQuery("status:0");
		QueryStringQueryBuilder _qQueryBuilder2 = QueryBuilders.queryStringQuery("-status:0");
		FilterAggregationBuilder _FilterAggregationBuilder1 = AggregationBuilders.filter("sendSuccessCount", _qQueryBuilder1);
		FilterAggregationBuilder _FilterAggregationBuilder2 = AggregationBuilders.filter("sendFailCount", _qQueryBuilder2);
		SearchResponse scrollResp = this.client.prepareSearch("home_platform_background").setTypes("historymtinfo").setFrom(0).setSize(10000).setQuery(_BoolQueryBuilder).addAggregation(_FilterAggregationBuilder1).addAggregation(_FilterAggregationBuilder2).get();
		Long submitTotal = 0L;
		if (!scrollResp.isTimedOut()) {
			submitTotal = scrollResp.getHits().getTotalHits();
			statisMap.put("submitTotal", submitTotal);
			_log.info("通道ID=" + channelid + "提交总数=" + submitTotal);
			Aggregations _Aggregations = scrollResp.getAggregations();
			for (Aggregation aggregation : _Aggregations) {
				InternalFilter _InternalFilter = (InternalFilter) aggregation;
				statisMap.put(_InternalFilter.getName(), _InternalFilter.getDocCount());
				_log.info("通道" + _InternalFilter.getName() + "数量=" + _InternalFilter.getDocCount());
			}
		} else {
			_log.info(scrollResp.status().getStatus() + "");
		}
		// 3 状态总数
		BoolQueryBuilder _BoolQueryBuilderStatus = QueryBuilders.boolQuery();
		RangeQueryBuilder _RangeQueryBuilderStatus = QueryBuilders.rangeQuery("sendtime").lte(end).gte(begin);// 大于小于用法
		_BoolQueryBuilderStatus.must(_TermQueryBuilder).must(_RangeQueryBuilderStatus);
		QueryStringQueryBuilder _qQueryBuilder3 = QueryBuilders.queryStringQuery("sendstatus:0");
		QueryStringQueryBuilder _qQueryBuilder4 = QueryBuilders.queryStringQuery("-sendstatus:0");
		FilterAggregationBuilder _FilterAggregationBuilder3 = AggregationBuilders.filter("statusSuccessTotal", _qQueryBuilder3);
		FilterAggregationBuilder _FilterAggregationBuilder4 = AggregationBuilders.filter("statusFailTotal", _qQueryBuilder4);
		scrollResp = this.client.prepareSearch("home_platform_background").setTypes("smsreportinfo").setFrom(0).setSize(10000).setQuery(_BoolQueryBuilderStatus).addAggregation(_FilterAggregationBuilder3).addAggregation(_FilterAggregationBuilder4).get();
		Long statusTotal = 0L;
		if (!scrollResp.isTimedOut()) {
			statusTotal = scrollResp.getHits().getTotalHits();
			statisMap.put("statusTotal", statusTotal);
			_log.info("通道ID=" + channelid + "状态总数=" + statusTotal);
			Aggregations _Aggregations = scrollResp.getAggregations();
			for (Aggregation aggregation : _Aggregations) {
				InternalFilter _InternalFilter = (InternalFilter) aggregation;
				statisMap.put(_InternalFilter.getName(), _InternalFilter.getDocCount());
				_log.info("通道" + _InternalFilter.getName() + "数量=" + _InternalFilter.getDocCount());
			}

		} else {
			_log.info(scrollResp.status().getStatus() + "");
		}
		// 计算未知量
		statisMap.put("statusUnKnowTotal", submitTotal - statusTotal);
		_log.info("通道ID" + channelid + "查询完耗时=" + (System.currentTimeMillis() - beigntime) + "毫秒");
		return statisMap;
	}

	/***********************
	 * 统计客户的发送未知量通过四个查询
	 * 
	 * @param servicecode
	 * @param beginsmsmtsubmittime
	 * @param endsmsmtsubmittime
	 * @return
	 */
	@GetMapping("/cpUserStatis")
	public Map<String, Long> cpUserStatis(@RequestParam(name = "servicecode", defaultValue = "", required = true) String servicecode, @RequestParam(name = "beginsubmittime", defaultValue = "", required = true) String beginsmsmtsubmittime, @RequestParam(name = "endsubmittime", defaultValue = "", required = true) String endsmsmtsubmittime) {
		long beigntime = System.currentTimeMillis();
		// 前提条件是开始时间和结束时间都有
		// 1:客户提交总数= 查询条件servicecode=000165 historymtinfo
		// 2：提交成功的：status=0
		// 3：提交失败的=提交总数-成功
		// 4：状态总数= 查询条件servicecode=000165 smsreportinfo
		// 5：状态成功的：sendstatus=0
		// 6：状态失败的：状态总数-成功的
		// 7：未知的：提交总数-状态总数

		// 将传递的时间格式yyyy-mm-dd h-m-s转化成2018-09-30T13:32:37+0800
		String begin = DateUtils.dateToUtc(beginsmsmtsubmittime);
		String end = DateUtils.dateToUtc(endsmsmtsubmittime);
		Map<String, Long> statisMap = new HashMap<String, Long>();
		// 1:提交总数
		BoolQueryBuilder _BoolQueryBuilder = QueryBuilders.boolQuery();
		TermQueryBuilder _TermQueryBuilder = QueryBuilders.termQuery("servicecode", servicecode);
		RangeQueryBuilder _RangeQueryBuilder = QueryBuilders.rangeQuery("submittime").lte(end).gte(begin);// 大于小于用法
		_BoolQueryBuilder.must(_TermQueryBuilder).must(_RangeQueryBuilder);
		SearchResponse scrollResp = this.client.prepareSearch("home_platform_background").setTypes("historymtinfo").setFrom(0).setSize(10000).setQuery(_BoolQueryBuilder).get();
		Long submitTotal = 0L;
		if (!scrollResp.isTimedOut()) {
			submitTotal = scrollResp.getHits().getTotalHits();
			statisMap.put("submitTotal", submitTotal);
			_log.info("客户" + servicecode + "提交总数=" + submitTotal);
		} else {
			_log.info(scrollResp.status().getStatus() + "");
		}
		// 2：提交成功和失败
		TermQueryBuilder _TermQueryStatus = QueryBuilders.termQuery("status", 0);
		_BoolQueryBuilder.must(_TermQueryStatus);
		scrollResp = this.client.prepareSearch("home_platform_background").setTypes("historymtinfo").setFrom(0).setSize(10000).setQuery(_BoolQueryBuilder).get();
		Long submitSuccess = 0L;
		if (!scrollResp.isTimedOut()) {
			submitSuccess = scrollResp.getHits().getTotalHits();
			statisMap.put("submitSuccessTotal", submitSuccess);
			_log.info("客户" + servicecode + "提交成功总数=" + submitTotal);
			statisMap.put("submitFailTotal", (submitTotal - submitSuccess));
			_log.info("客户" + servicecode + "提交失败总数=" + (submitTotal - submitSuccess));

		} else {
			_log.info(scrollResp.status().getStatus() + "");
		}
		// 3 状态总数
		BoolQueryBuilder _BoolQueryBuilderStatus = QueryBuilders.boolQuery();
		_BoolQueryBuilderStatus.must(_TermQueryBuilder).must(_RangeQueryBuilder);
		scrollResp = this.client.prepareSearch("home_platform_background").setTypes("smsreportinfo").setFrom(0).setSize(10000).setQuery(_BoolQueryBuilderStatus).get();
		Long statusTotal = 0L;
		if (!scrollResp.isTimedOut()) {
			statusTotal = scrollResp.getHits().getTotalHits();
			statisMap.put("statusTotal", statusTotal);
			_log.info("客户" + servicecode + "状态总数=" + statusTotal);

		} else {
			_log.info(scrollResp.status().getStatus() + "");
		}
		// 状态成功和失败未知
		TermQueryBuilder _TermQuerySendStatus = QueryBuilders.termQuery("sendstatus", 0);
		_BoolQueryBuilderStatus.must(_TermQuerySendStatus);
		Long statusSuccessTotal = 0L;
		Long statusFailTotal = 0L;
		Long statusUnKnowTotal = 0L;
		scrollResp = this.client.prepareSearch("home_platform_background").setTypes("smsreportinfo").setFrom(0).setSize(10000).setQuery(_BoolQueryBuilderStatus).get();
		if (!scrollResp.isTimedOut()) {
			statusSuccessTotal = scrollResp.getHits().getTotalHits();
			statisMap.put("statusSuccessTotal", statusSuccessTotal);
			statusFailTotal = statusTotal - statusSuccessTotal;
			statisMap.put("statusFailTotal", statusFailTotal);
			statusUnKnowTotal = submitTotal - statusTotal;
			statisMap.put("statusUnKnowTotal", statusUnKnowTotal);
			_log.info("客户" + servicecode + "状态成功总数=" + statusSuccessTotal);
			_log.info("客户" + servicecode + "状态失败总数=" + statusFailTotal);
			_log.info("客户" + servicecode + "状态未知总数=" + statusUnKnowTotal);
			_log.info("客户" + servicecode + "查询完耗时=" + (System.currentTimeMillis() - beigntime) + "毫秒");
		} else {
			_log.info(scrollResp.status().getStatus() + "");
		}
		return statisMap;
	}

	// 统计客户的发送未知量
	@GetMapping("/channelStatis")
	public Map<String, Long> channelStatis(@RequestParam(name = "channelid", defaultValue = "", required = true) String channelid, @RequestParam(name = "beginsubmittime", defaultValue = "", required = true) String beginsmsmtsubmittime, @RequestParam(name = "endsubmittime", defaultValue = "", required = true) String endsmsmtsubmittime) {
		long beigntime = System.currentTimeMillis();
		// 前提条件是开始时间和结束时间都有
		// 1:提交总数= 查询条件channelid=1 historymtinfo
		// 2：提交成功的：status=0
		// 3：提交失败的=提交总数-成功
		// 4：状态总数= 查询条件channelid=1 smsreportinfo
		// 5：状态成功的：sendstatus=0
		// 6：状态失败的：状态总数-成功的
		// 7：未知的：提交总数-状态总数

		// 将传递的时间格式yyyy-mm-dd h-m-s转化成2018-09-30T13:32:37+0800
		String begin = DateUtils.dateToUtc(beginsmsmtsubmittime);
		String end = DateUtils.dateToUtc(endsmsmtsubmittime);
		Map<String, Long> statisMap = new HashMap<String, Long>();
		// 1:提交总数
		BoolQueryBuilder _BoolQueryBuilder = QueryBuilders.boolQuery();
		TermQueryBuilder _TermQueryBuilder = QueryBuilders.termQuery("channelid", channelid);
		RangeQueryBuilder _RangeQueryBuilder = QueryBuilders.rangeQuery("createtime").lte(end).gte(begin);// 大于小于用法
		_BoolQueryBuilder.must(_TermQueryBuilder).must(_RangeQueryBuilder);
		SearchResponse scrollResp = this.client.prepareSearch("home_platform_background").setTypes("historymtinfo").setFrom(0).setSize(10000).setQuery(_BoolQueryBuilder).get();
		Long submitTotal = 0L;
		if (!scrollResp.isTimedOut()) {
			submitTotal = scrollResp.getHits().getTotalHits();
			statisMap.put("submitTotal", submitTotal);
			_log.info("通道ID=" + channelid + "提交总数=" + submitTotal);
		} else {
			_log.info(scrollResp.status().getStatus() + "");
		}
		// 2：提交成功和失败
		TermQueryBuilder _TermQueryStatus = QueryBuilders.termQuery("status", 0);
		_BoolQueryBuilder.must(_TermQueryStatus);
		scrollResp = this.client.prepareSearch("home_platform_background").setTypes("historymtinfo").setFrom(0).setSize(10000).setQuery(_BoolQueryBuilder).get();
		Long submitSuccess = 0L;
		if (!scrollResp.isTimedOut()) {
			submitSuccess = scrollResp.getHits().getTotalHits();
			statisMap.put("submitSuccessTotal", submitSuccess);
			_log.info("通道ID=" + channelid + "提交成功总数=" + submitTotal);
			statisMap.put("submitFailTotal", (submitTotal - submitSuccess));
			_log.info("通道ID=" + channelid + "提交失败总数=" + (submitTotal - submitSuccess));

		} else {
			_log.info(scrollResp.status().getStatus() + "");
		}
		// 3 状态总数
		BoolQueryBuilder _BoolQueryBuilderStatus = QueryBuilders.boolQuery();
		RangeQueryBuilder _RangeQueryBuilderStatus = QueryBuilders.rangeQuery("sendtime").lte(end).gte(begin);// 大于小于用法
		_BoolQueryBuilderStatus.must(_TermQueryBuilder).must(_RangeQueryBuilderStatus);
		scrollResp = this.client.prepareSearch("home_platform_background").setTypes("smsreportinfo").setFrom(0).setSize(10000).setQuery(_BoolQueryBuilderStatus).get();
		Long statusTotal = 0L;
		if (!scrollResp.isTimedOut()) {
			statusTotal = scrollResp.getHits().getTotalHits();
			statisMap.put("statusTotal", statusTotal);
			_log.info("通道ID=" + channelid + "状态总数=" + statusTotal);

		} else {
			_log.info(scrollResp.status().getStatus() + "");
		}
		// 状态成功和失败未知
		TermQueryBuilder _TermQuerySendStatus = QueryBuilders.termQuery("sendstatus", 0);
		_BoolQueryBuilderStatus.must(_TermQuerySendStatus);
		Long statusSuccessTotal = 0L;
		Long statusFailTotal = 0L;
		Long statusUnKnowTotal = 0L;
		scrollResp = this.client.prepareSearch("home_platform_background").setTypes("smsreportinfo").setFrom(0).setSize(10000).setQuery(_BoolQueryBuilderStatus).get();
		if (!scrollResp.isTimedOut()) {
			statusSuccessTotal = scrollResp.getHits().getTotalHits();
			statisMap.put("statusSuccessTotal", statusSuccessTotal);
			statusFailTotal = statusTotal - statusSuccessTotal;
			statisMap.put("statusFailTotal", statusFailTotal);
			statusUnKnowTotal = submitTotal - statusTotal;
			statisMap.put("statusUnKnowTotal", statusUnKnowTotal);
			_log.info("通道ID=" + channelid + "状态成功总数=" + statusSuccessTotal);
			_log.info("通道ID=" + channelid + "状态失败总数=" + statusFailTotal);
			_log.info("通道ID=" + channelid + "状态未知总数=" + statusUnKnowTotal);
			_log.info("通道ID=" + channelid + "查询完耗时=" + (System.currentTimeMillis() - beigntime) + "毫秒");
		} else {
			_log.info(scrollResp.status().getStatus() + "");
		}
		return statisMap;
	}

	/************
	 * 删除文档根据特服号时间段
	 * 
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	@GetMapping("/deleteDocSendall")
	public void deleteDocSendall(@RequestParam(name = "servicecode", defaultValue = "", required = true) String servicecode, @RequestParam(name = "beginsubmittime", defaultValue = "", required = true) String beginsubmittime, @RequestParam(name = "endsubmittime", defaultValue = "", required = true) String endsubmittime) throws InterruptedException, ExecutionException {
		String index = "sendallinfo";
		String type = "sendallinfo";
		String begin = DateUtils.dateToUtc(beginsubmittime);
		String end = DateUtils.dateToUtc(endsubmittime);
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type);
		// 分页
		searchRequestBuilder.setFrom(0).setSize(1000).setScroll(new TimeValue(60000));

		BoolQueryBuilder _BoolQueryBuilder = QueryBuilders.boolQuery();
		if (servicecode != null && !"".equals(servicecode)) {
			TermQueryBuilder _TermQueryBuilder = QueryBuilders.termQuery("servicecode", servicecode);
			_BoolQueryBuilder.must(_TermQueryBuilder);
		}
		RangeQueryBuilder _RangeQueryBuilder = QueryBuilders.rangeQuery("createtime").lte(end).gte(begin);// 大于小于用法
		_BoolQueryBuilder.must(_RangeQueryBuilder);

		searchRequestBuilder.setQuery(_BoolQueryBuilder);
		SearchResponse response = searchRequestBuilder.execute().get();

		do {
			if (response.getHits().getHits().length == 0) {
				break;
			}
			for (SearchHit hit : response.getHits()) {
				String id = hit.getId();
				_log.info("删除的ID=" + id);
				bulkRequest.add(client.prepareDelete(index, type, id).request());
			}
			BulkResponse bulkResponse = bulkRequest.get();
			if (bulkResponse.hasFailures()) {
				for (BulkItemResponse item : bulkResponse.getItems()) {
					_log.info(item.getFailureMessage());
				}
			} else {
				_log.info("delete ok");
			}

			response = client.prepareSearchScroll(response.getScrollId()).setScroll(new TimeValue(120000)).execute().actionGet();
		} while (response.getHits().getHits().length != 0);

	}

	/************
	 * 删除文档根据特服号时间段
	 * 
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	@GetMapping("/deleteDocSmsReport")
	public void deleteDocSmsReport(@RequestParam(name = "servicecode", defaultValue = "", required = true) String servicecode, @RequestParam(name = "beginsubmittime", defaultValue = "", required = true) String beginsubmittime, @RequestParam(name = "endsubmittime", defaultValue = "", required = true) String endsubmittime) throws InterruptedException, ExecutionException {
		String index = "smsreportinfo";
		String type = "smsreportinfo";
		String begin = DateUtils.dateToUtc(beginsubmittime);
		String end = DateUtils.dateToUtc(endsubmittime);
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type);
		// 分页
		searchRequestBuilder.setFrom(0).setSize(1000).setScroll(new TimeValue(60000));

		BoolQueryBuilder _BoolQueryBuilder = QueryBuilders.boolQuery();
		if (servicecode != null && !"".equals(servicecode)) {
			TermQueryBuilder _TermQueryBuilder = QueryBuilders.termQuery("servicecode", servicecode);
			_BoolQueryBuilder.must(_TermQueryBuilder);
		}
		RangeQueryBuilder _RangeQueryBuilder = QueryBuilders.rangeQuery("submittime").lte(end).gte(begin);// 大于小于用法
		_BoolQueryBuilder.must(_RangeQueryBuilder);

		searchRequestBuilder.setQuery(_BoolQueryBuilder);
		SearchResponse response = searchRequestBuilder.execute().get();

		do {
			if (response.getHits().getHits().length == 0) {
				break;
			}
			for (SearchHit hit : response.getHits()) {
				String id = hit.getId();
				_log.info("删除的ID=" + id);
				bulkRequest.add(client.prepareDelete(index, type, id).request());
			}
			BulkResponse bulkResponse = bulkRequest.get();
			if (bulkResponse.hasFailures()) {
				for (BulkItemResponse item : bulkResponse.getItems()) {
					_log.info(item.getFailureMessage());
				}
			} else {
				_log.info("delete ok");
			}

			response = client.prepareSearchScroll(response.getScrollId()).setScroll(new TimeValue(120000)).execute().actionGet();
		} while (response.getHits().getHits().length != 0);

	}

	/************
	 * 删除文档根据特服号时间段
	 * 
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	@GetMapping("/deleteDocHistory")
	public void deleteDocByServiceCode(@RequestParam(name = "servicecode", defaultValue = "", required = true) String servicecode, @RequestParam(name = "beginsubmittime", defaultValue = "", required = true) String beginsubmittime, @RequestParam(name = "endsubmittime", defaultValue = "", required = true) String endsubmittime) throws InterruptedException, ExecutionException {
		String index = "historymtinfo";
		String type = "historymtinfo";
		String begin = DateUtils.dateToUtc(beginsubmittime);
		String end = DateUtils.dateToUtc(endsubmittime);
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type);
		// 分页
		searchRequestBuilder.setFrom(0).setSize(1000).setScroll(new TimeValue(60000));

		BoolQueryBuilder _BoolQueryBuilder = QueryBuilders.boolQuery();
		if (servicecode != null && !"".equals(servicecode)) {
			TermQueryBuilder _TermQueryBuilder = QueryBuilders.termQuery("servicecode", servicecode);
			_BoolQueryBuilder.must(_TermQueryBuilder);
		}
		RangeQueryBuilder _RangeQueryBuilder = QueryBuilders.rangeQuery("submittime").lte(end).gte(begin);// 大于小于用法
		_BoolQueryBuilder.must(_RangeQueryBuilder);

		searchRequestBuilder.setQuery(_BoolQueryBuilder);
		SearchResponse response = searchRequestBuilder.execute().get();

		do {
			if (response.getHits().getHits().length == 0) {
				break;
			}
			for (SearchHit hit : response.getHits()) {
				String id = hit.getId();
				_log.info("删除的ID=" + id);
				bulkRequest.add(client.prepareDelete(index, type, id).request());
			}
			BulkResponse bulkResponse = bulkRequest.get();
			if (bulkResponse.hasFailures()) {
				for (BulkItemResponse item : bulkResponse.getItems()) {
					_log.info(item.getFailureMessage());
				}
			} else {
				_log.info("delete ok");
			}

			response = client.prepareSearchScroll(response.getScrollId()).setScroll(new TimeValue(120000)).execute().actionGet();
		} while (response.getHits().getHits().length != 0);

	}

}
