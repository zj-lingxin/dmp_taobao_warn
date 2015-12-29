package com.asto.dmp.taobaowarn.dao.impl

import com.asto.dmp.taobaowarn.base.Constants

trait BizDao {
  //小数保留位数
  val retainBits = 3
  val getColorLevel = (scores: Double) => if (scores >= 5) Constants.App.WARN_LEVEL_RED else if(scores < 5 && scores >= 4) Constants.App.WARN_LEVEL_ORANGE else Constants.App.WARN_LEVEL_GREEN

  protected def shopInfoIsLegal(shopInfo: Array[Any], indexRange: (Int,Int)): Boolean = {
    val numberRegex = "(-|\\+)?[0-9]+(\\.[0-9]+)?"
    var isLegalType = true
    if (shopInfo.length == indexRange._2 + 1) {
      (indexRange._1 to indexRange._2).foreach(i => isLegalType = isLegalType && shopInfo(i).toString.matches(numberRegex))
    } else {
      isLegalType = false
    }
    isLegalType
  }

}
