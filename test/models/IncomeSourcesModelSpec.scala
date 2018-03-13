/*
 * Copyright 2018 HM Revenue & Customs
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

package models

import org.scalatest.Matchers
import uk.gov.hmrc.play.test.UnitSpec
import assets.TestConstants._
import assets.TestConstants.IncomeSources._
import assets.TestConstants.BusinessDetails._
import assets.TestConstants.PropertyDetails._

class IncomeSourcesModelSpec extends UnitSpec with Matchers {

  "The IncomeSourceDetailsModel" when {
    "the user has both businesses and property income sources" should {
      //Test Business details
      s"have a businesses ID of $testSelfEmploymentId" in {
        bothIncomeSourceSuccessMisalignedTaxYear.businessIncomeSources.head.selfEmploymentId shouldBe testSelfEmploymentId
      }
      s"have the businesses accounting period start date of ${testBusinessAccountingPeriod.start}" in {
        bothIncomeSourceSuccessMisalignedTaxYear.businessIncomeSources.head.accountingPeriod.start shouldBe testBusinessAccountingPeriod.start
      }
      s"have the businesses accounting period end date of ${testBusinessAccountingPeriod.end}" in {
        bothIncomeSourceSuccessMisalignedTaxYear.businessIncomeSources.head.accountingPeriod.end shouldBe testBusinessAccountingPeriod.end
      }
      s"should have the trading name of 'Test Business'" in {
        bothIncomeSourceSuccessMisalignedTaxYear.businessIncomeSources.head.tradingName shouldBe testTradeName
      }
      //Test Property details
      s"have the property accounting period start date of ${testPropertyAccountingPeriod.start}" in {
        bothIncomeSourceSuccessMisalignedTaxYear.propertyIncomeSource.get.accountingPeriod.start shouldBe testPropertyAccountingPeriod.start
      }
      s"have the property accounting period end date of ${testPropertyAccountingPeriod.end}" in {
        bothIncomeSourceSuccessMisalignedTaxYear.propertyIncomeSource.get.accountingPeriod.end shouldBe testPropertyAccountingPeriod.end
      }
      s"return ${testPropertyAccountingPeriod.start} as the result for 'earliestAccountingPeriodStart'" in {
        bothIncomeSourceSuccessMisalignedTaxYear.earliestAccountingPeriodStart(2018) shouldBe testPropertyAccountingPeriod.start
      }
      s"return ${testPropertyAccountingPeriod.start} as the result for 'earliestAccountingPeriodStart' when both Accounting Periods have the same start" in {
        bothIncomeSourcesSuccessBusinessAligned.earliestAccountingPeriodStart(2018) shouldBe testPropertyAccountingPeriod.start
      }
    }

    "the user has just a businesses income source" should {
      s"have a businesses ID of $testSelfEmploymentId" in {
        businessIncomeSourceSuccess.businessIncomeSources.head.selfEmploymentId shouldBe testSelfEmploymentId
      }
      s"have the businesses accounting period start date of ${testBusinessAccountingPeriod.start}" in {
        businessIncomeSourceSuccess.businessIncomeSources.head.accountingPeriod.start shouldBe testBusinessAccountingPeriod.start
      }
      s"have the businesses accounting period end date of ${testBusinessAccountingPeriod.end}" in {
        businessIncomeSourceSuccess.businessIncomeSources.head.accountingPeriod.end shouldBe testBusinessAccountingPeriod.end
      }
      s"should have the trading name of 'Test Business'" in {
        businessIncomeSourceSuccess.businessIncomeSources.head.tradingName shouldBe testTradeName
      }
      //Test Property details
      s"should not have property details" in {
        businessIncomeSourceSuccess.propertyIncomeSource shouldBe None
      }
      s"return ${testBusinessAccountingPeriod.start} as the result for 'earliestAccountingPeriodStart'" in {
        businessIncomeSourceSuccess.earliestAccountingPeriodStart(2019) shouldBe testBusinessAccountingPeriod.start
      }
    }
    "the user has just a property income source" should {
      //Test Property details
      s"have the property accounting period start date of ${testPropertyAccountingPeriod.start}" in {
        propertyIncomeSourceSuccess.propertyIncomeSource.get.accountingPeriod.start shouldBe testPropertyAccountingPeriod.start
      }
      s"have the property accounting period end date of ${testPropertyAccountingPeriod.end}" in {
        propertyIncomeSourceSuccess.propertyIncomeSource.get.accountingPeriod.end shouldBe testPropertyAccountingPeriod.end
      }
      //Test Business Details
      "should not have businesses details" in {
        propertyIncomeSourceSuccess.businessIncomeSources shouldBe List.empty
      }
      s"return ${testPropertyAccountingPeriod.start} as the result for 'earliestAccountingPeriodStart'" in {
        propertyIncomeSourceSuccess.earliestAccountingPeriodStart(2018) shouldBe testPropertyAccountingPeriod.start
      }
    }
    "the user has no income source" should {
      "return None for both businesses and property sources" in {
        noIncomeSourceSuccess.propertyIncomeSource shouldBe None
        noIncomeSourceSuccess.businessIncomeSources shouldBe List.empty
      }
    }
    "the user has both income sources, but only a businesses income source for 2019 then 'earliestAccountingPeriodStart'" should {
      "return the businesses income source accounting period start date" in {
        bothIncomeSourceSuccessMisalignedTaxYear.earliestAccountingPeriodStart(2019) shouldBe testBusinessAccountingPeriod.start
      }
    }
  }

}
