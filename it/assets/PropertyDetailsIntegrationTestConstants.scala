/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package assets

import models.core.AccountingPeriodModel
import models.incomeSourceDetails.PropertyDetailsModel
import play.api.libs.json.{JsValue, Json}
import assets.BaseIntegrationTestConstants.testPropertyIncomeId
import utils.ImplicitDateFormatter._

object PropertyDetailsIntegrationTestConstants {

  val propertyAccountingStart = "2017-01-01"
  val propertyAccountingEnd = "2017-12-31"

  val property: PropertyDetailsModel = PropertyDetailsModel(
    incomeSourceId = testPropertyIncomeId,
    accountingPeriod = AccountingPeriodModel(
      start = propertyAccountingStart,
      end = propertyAccountingEnd
    ),
    contactDetails = None,
    propertiesRented = None,
    cessation = None,
    paperless = None
  )

  val propertySuccessResponse: JsValue = Json.obj(
    "incomeSourceId" -> testPropertyIncomeId,
    "accountingPeriod" -> Json.obj(
      "start" -> propertyAccountingStart,
      "end" -> propertyAccountingEnd
    )
  )

}