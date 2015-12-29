package com.asto.dmp.taobaowarn.service.impl

import com.asto.dmp.taobaowarn.base.Constants
import com.asto.dmp.taobaowarn.dao.impl.{BizDaoForBShop, BizDaoForCShop}
import com.asto.dmp.taobaowarn.service.Service
import com.asto.dmp.taobaowarn.util.FileUtils

class CShopService extends Service {
  override def runServices() = {
    FileUtils.saveAsTextFileForString(BizDaoForCShop.getClosedShop, Constants.OutputPath.C_SHOP_CLOSED)
    FileUtils.saveAsTextFile(BizDaoForCShop.getScores, Constants.OutputPath.C_SHOP_SCORES)
    FileUtils.saveAsTextFile(BizDaoForCShop.getResult, Constants.OutputPath.C_SHOP_RESULT)
  }
}
