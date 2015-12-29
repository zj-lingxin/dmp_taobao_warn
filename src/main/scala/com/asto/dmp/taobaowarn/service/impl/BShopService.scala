package com.asto.dmp.taobaowarn.service.impl

import com.asto.dmp.taobaowarn.base.Constants
import com.asto.dmp.taobaowarn.dao.impl.BizDaoForBShop
import com.asto.dmp.taobaowarn.service.Service
import com.asto.dmp.taobaowarn.util.FileUtils

class BShopService extends Service {
  override def runServices() = {
    FileUtils.saveAsTextFileForString(BizDaoForBShop.getClosedShop, Constants.OutputPath.B_SHOP_CLOSED)
    FileUtils.saveAsTextFile(BizDaoForBShop.getScores, Constants.OutputPath.B_SHOP_SCORES)
    FileUtils.saveAsTextFile(BizDaoForBShop.getResult, Constants.OutputPath.B_SHOP_RESULT)
  }
}