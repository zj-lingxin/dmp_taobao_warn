package com.asto.dmp.taobaowarn.dao.impl

import com.asto.dmp.taobaowarn.base.Constants

object BizDaoForBShop extends BizDao {
  val scoresRules = (condition: Boolean) => if (condition) 1 else 0

  /**
   * 获取销售额的得分
   * 销售额>=30000&&<50000为3
   * 销售额<30000为5
   * 否则为0
   */
  val getSalesScore = (sales: Double) => if (sales >= 30000 && sales < 50000) 3 else if (sales < 30000) 5 else 0

  /**
   * 获取保证金余额的得分
   * 当前保证金<50000为1否则为0
   */
  val getDepositBalanceScore = (depositBalance: Double) => scoresRules(depositBalance < 50000)

  /**
   * 获取DSR差值的得分
   * DSR差值>9.2为1否则为0
   */
  val getDsrDiffScore = (dsrDiff: Double) => scoresRules(dsrDiff > 9.2)

  /**
   * 获取DSR平均的得分
   * DSR平均<0.22为1否则为0
   */
  val getDsrAvgScore = (dsrAvg: Double) => scoresRules(dsrAvg < 0.22)

  /**
   * 获取退款率的得分
   * 退款率>0.14为1否则为0
   */
  val getRefundRateScore = (refundRate: Double) => scoresRules(refundRate > 0.14)

  /**
   * 获取退款率行业水平对比的得分
   * 退款率行业水平对比>1.5为1否则为0
   */
  val getRefundRateIndusLevelContScore = (refundRateIndusLevelCont: Double) => scoresRules(refundRateIndusLevelCont > 1.5)

  /**
   * 获取退款速度的得分
   * 退款速度>3为1否则为0
   */
  val getRefundSpeedScore = (refundSpeed: Double) => scoresRules(refundSpeed > 3)

  /**
   * 获取退款速度行业平均水平对比的得分
   * 退款速度行业平均水平对比>1为1否则为0
   */
  val getRefundSpeedIndusLevelContScore = (refundSpeedIndusLevelCont: Double) => scoresRules(refundSpeedIndusLevelCont > 1)

  /**
   * 获取客单价的得分
   * 客单价<230为1否则为0
   */
  val getPerCustomerTrans = (perCustomerTrans: Double) => scoresRules(perCustomerTrans < 230)

  def getScores = {
    BaseDao.getBShopInfoProps("shop_id,sales,deposit_balance,dsr_diff,dsr_avg,refund_rate,refund_rate_indus_level_cont,refund_speed,refund_speed_indus_level_cont,per_customer_trans", "shop_closed = '0'")
      .filter(shopInfoIsLegal(_, (1, 9))) //过滤掉一些异常数据，如sales不是Double或Int类型的数据
      .map(a => (
        a(0).toString,
        getSalesScore(a(1).toString.toDouble),
        getDepositBalanceScore(a(2).toString.toDouble),
        getDsrDiffScore(a(3).toString.toDouble),
        getDsrAvgScore(a(4).toString.toDouble),
        getRefundRateScore(a(5).toString.toDouble),
        getRefundRateIndusLevelContScore(a(6).toString.toDouble),
        getRefundSpeedScore(a(7).toString.toDouble),
        getRefundSpeedIndusLevelContScore(a(8).toString.toDouble),
        getPerCustomerTrans(a(9).toString.toDouble))
      ).map(t => (t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._2 + t._3 + t._4 + t._5 + t._6 + t._7 + t._8 + t._9 + t._10)).persist()
  }

  def getClosedShop = {
    BaseDao.getBShopInfoProps("shop_id", "shop_closed = '1'").map(a => a(0).toString)
  }

  def getResult = {
    val closedShop = getClosedShop.map(a => (a(0).toString, Constants.App.WARN_LEVEL_RED ))
    val openShop = getScores.map(t => (t._1, getColorLevel(t._11)))
    closedShop.union(openShop)
  }

}
