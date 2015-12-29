package com.asto.dmp.taobaowarn.base

object Constants {

  /** App中的常量与每个项目相关 **/
  object App {
    val NAME = "淘宝预警"
    val LOG_WRAPPER = "##########"
    val YEAR_MONTH_DAY_FORMAT = "yyyy-MM-dd"
    val YEAR_MONTH_FORMAT = "yyyy-MM"
    val DIR = s"${Hadoop.DEFAULT_FS}/taobao_warn"
    var TODAY: String = _
    var SHOP_TYPE: String = _
    var TIMESTAMP: Long = _
    val ERROR_LOG: StringBuffer = new StringBuffer("")
    var MESSAGES: StringBuffer = new StringBuffer("")
    //var SHOP_TYPE_B_CODE = 1
    var SHOP_TYPE_C_CODE = 0

    val WARN_LEVEL_RED = "红"
    val WARN_LEVEL_ORANGE = "橙"
    val WARN_LEVEL_GREEN = "绿"
  }
  
  object Hadoop {
    val JOBTRACKER_ADDRESS = "appcluster"
    val DEFAULT_FS = s"hdfs://$JOBTRACKER_ADDRESS"
  }

  /** 输入文件路径 **/
  object InputPath {
    val SEPARATOR = "\t"

    //离线目录
    private val OFFLINE_DIR = s"${App.DIR}/offline/input/${App.TODAY}/${App.TIMESTAMP}"
    val C_SHOP_INFO = s"$OFFLINE_DIR/c_shop_info"
    val B_SHOP_INFO = s"$OFFLINE_DIR/b_shop_info"
  }

  /** 输出文件路径 **/
  object OutputPath {
    val SEPARATOR = "\t"
    private val OFFLINE_DIR = s"${App.DIR}/offline/output/${App.TODAY}/${App.TIMESTAMP}"
    val B_SHOP_SCORES = s"$OFFLINE_DIR/b_shop_scores"
    val B_SHOP_RESULT = s"$OFFLINE_DIR/b_shop_result"
    val B_SHOP_CLOSED = s"$OFFLINE_DIR/b_shop_closed"
    val C_SHOP_SCORES = s"$OFFLINE_DIR/c_shop_scores"
    val C_SHOP_RESULT = s"$OFFLINE_DIR/c_shop_result"
    val C_SHOP_CLOSED = s"$OFFLINE_DIR/c_shop_closed"
  }

  /** 表的模式 **/
  object Schema {
    //C店铺信息：       店铺ID,	  店铺名称,	  当前主营,	 店铺分类	,  行业分类	,   当前保证金余额,	  卖家信用,	       DSR差值,	DSR平均,	退款率,      退款率行业水平对比	,           退款速度,      退款速度行业平均水平对比,	     主营占比	,      好评率,	      周评价,	  月评价,	   半年评价,	周评价趋势(评价下滑趋势周/月*4.28),月评价趋势(月评价/半年评价*6),是否关店
    val C_SHOP_INFO = "shop_id,shop_name,main_biz,shop_type,indus_type,deposit_balance,sellers_credit,dsr_diff,dsr_avg,refund_rate,refund_rate_indus_level_cont,refund_speed,refund_speed_indus_level_cont,main_biz_rate,applause_rate,week_eval,month_eval,half_year_eval,week_eval_trend,month_eval_trend,shop_closed"

    //B店铺信息：       店铺ID,	 店铺名称,	 当前主营, 当前保证金余额,	  DSR差值,	 DSR平均,退款率,	     退款率行业水平对比,	            退款速度,	     退款速度行业平均水平对比,	       销售,	客单价,              是否关店
    val B_SHOP_INFO = "shop_id,shop_name,main_biz,deposit_balance,dsr_diff,dsr_avg,refund_rate,refund_rate_indus_level_cont,refund_speed,refund_speed_indus_level_cont,sales,per_customer_trans,shop_closed"

  }
}
