package com.asto.dmp.taobaowarn.dao.impl

import com.asto.dmp.taobaowarn.base.Constants
import com.asto.dmp.taobaowarn.util.Utils

object BizDaoForCShop extends BizDao {
  /**
   * 获取保证金余额的得分
   * 当前保证金<2000为0.524否则为0
   */
  val getDepositBalanceScore = (depositBalance: Double) => if (depositBalance < 2000) 0.524 else 0
  /**
   * 获取DSR差值的得分
   * DSR差值>12.7为0.49否则为0
   */
  val getDsrDiffScore = (dsrDiff: Double) => if (dsrDiff > 12.7) 0.49 else 0

  /**
   * 获取DSR平均的得分
   * DSR平均<-0.33&&>94为0.59否则为0
   */
  val getDsrAvgScore = (dsrAvg: Double) => if (dsrAvg < -0.33 && dsrAvg > 94) 0.59 else 0

  /**
   * 获取退款率的得分
   * 退款率>6.4为0.68否则为0
   */
  val getRefundRateScore = (refundRate: Double) => if (refundRate > 6.4) 0.68 else 0

  /**
   * 获取退款率行业水平对比的得分
   * 退款率行业水平对比 > 3.8为0.66否则为0
   */
  val getRefundRateIndusLevelContScore = (refundRateIndusLevelCont: Double) => if (refundRateIndusLevelCont > 3.8) 0.66 else 0

  /**
   * 获取退款速度的得分
   * 退款速度>4.6为0.75否则为0
   */
  val getRefundSpeedScore = (refundSpeed: Double) => if (refundSpeed > 4.6) 0.75 else 0

  /**
   * 获取退款速度行业平均水平对比的得分
   * 退款速度行业平均水平对比>1.27为0.72否则为0
   */
  val getRefundSpeedIndusLevelContScore = (refundSpeedIndusLevelCont: Double) => if (refundSpeedIndusLevelCont > 1.27) 0.72 else 0

  /**
   * 主营业务占比
   * 主营业务占比>97为0.75否则为0
   */
  val getMainBizRateScore = (mainBizRate: Double) => if (mainBizRate > 97) 0.75 else 0

  /**
   * 好评率
   * 好评率<99.3为0.67否则为0
   */
  val getApplauseRateScore = (applauseRate: Double) => if (applauseRate < 99.3) 0.67 else 0

  /**
   * 当前卖家信用
   * 当前卖家信用<5932为0.54否则为0
   */
  val getSellersCreditScore = (sellersCredit: Double) => if (sellersCredit < 5932) 0.54 else 0

  /**
   * 周评价
   * 周评价<10为0.91否则为0
   */
  val getWeekEvalScore = (weekEval: Double) => if (weekEval < 10) 0.91 else 0

  /**
   * 月评价
   * 月评价<180为0.88否则为0
   */
  val getMonthEvalScore = (monthEval: Double) => if (monthEval < 180) 0.88 else 0

  /**
   *
   * 半年评价
   * 半年评价<1285为0.77否则为0
   */
  val getHalfYearEvalScore = (halfYearEval: Double) => if (halfYearEval < 1285) 0.77 else 0

  /**
   * 周评价趋势
   * 周评价趋势<0.005 为0.9否则为0
   */
  val getWeekEvalTrendScore = (weekEvalTrend: Double) => if (weekEvalTrend < 0.005) 0.9 else 0

  /**
   * 月评价趋势
   * 月评价趋势<0.4为0.81否则为0
   */
  val getMonthEvalTrendScore = (monthEvalTrend: Double) => if (monthEvalTrend < 0.4) 0.81 else 0

  def getScores = {
    BaseDao.getCShopInfoProps("shop_id,deposit_balance,dsr_diff,dsr_avg,refund_rate,refund_rate_indus_level_cont,refund_speed,refund_speed_indus_level_cont,main_biz_rate,applause_rate,sellers_credit,week_eval,month_eval,half_year_eval,week_eval_trend,month_eval_trend", "shop_closed = '0'")
      .filter(shopInfoIsLegal(_, (1, 15))) //过滤掉一些异常数据，如sales不是Double或Int类型的数据
      .map(a => (
        a(0).toString,
        getDepositBalanceScore(a(1).toString.toDouble),
        getDsrDiffScore(a(2).toString.toDouble),
        getDsrAvgScore(a(3).toString.toDouble),
        getRefundRateScore(a(4).toString.toDouble),
        getRefundRateIndusLevelContScore(a(5).toString.toDouble),
        getRefundSpeedScore(a(6).toString.toDouble),
        getRefundSpeedIndusLevelContScore(a(7).toString.toDouble),
        getMainBizRateScore(a(8).toString.toDouble),
        getApplauseRateScore(a(9).toString.toDouble),
        getSellersCreditScore(a(10).toString.toDouble),
        getWeekEvalScore(a(11).toString.toDouble),
        getMonthEvalScore(a(12).toString.toDouble),
        getHalfYearEvalScore(a(13).toString.toDouble),
        getWeekEvalTrendScore(a(14).toString.toDouble),
        getMonthEvalTrendScore(a(15).toString.toDouble)
      )).map(t => (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15, t._16, Utils.retainDecimal(t._2 + t._3 + t._4 + t._5 + t._6 + t._7 + t._8 + t._9 + t._10 + t._11 + t._12 + t._13 + t._14 + t._15 + t._16)))
  }

  def getClosedShop = {
    BaseDao.getCShopInfoProps("shop_id", "shop_closed = '1'").map(_(0).toString)
  }

  def getResult = {
    val closedShop = getClosedShop.map(a => (a(0).toString, Constants.App.WARN_LEVEL_RED ))
    val openShop = getScores.map(t => (t._1, getColorLevel(t._17)))
    closedShop.union(openShop)
  }
}
