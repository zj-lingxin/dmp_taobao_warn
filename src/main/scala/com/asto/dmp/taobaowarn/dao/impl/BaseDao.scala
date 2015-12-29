package com.asto.dmp.taobaowarn.dao.impl

import com.asto.dmp.taobaowarn.base.Constants
import com.asto.dmp.taobaowarn.dao.{Dao, SQL}

object BaseDao extends Dao {  

  def getBShopInfoProps(selectStr: String, whereStr: String = null) = {
    getProps(Constants.InputPath.B_SHOP_INFO, Constants.Schema.B_SHOP_INFO, "b_shop_info",  SQL().select(selectStr).where(whereStr))
  }

  def getCShopInfoProps(selectStr: String, whereStr: String = null) = {
    getProps(Constants.InputPath.C_SHOP_INFO, Constants.Schema.C_SHOP_INFO, "c_shop_info",  SQL().select(selectStr).where(whereStr).appendWhere(s"shop_type = '${Constants.App.SHOP_TYPE_C_CODE}'"))
  }
}
