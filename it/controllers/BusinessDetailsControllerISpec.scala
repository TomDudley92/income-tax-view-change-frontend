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

package controllers

import assets.BaseIntegrationTestConstants.{testMtditid, testPropertyIncomeId, testSelfEmploymentId}
import assets.BusinessDetailsIntegrationTestConstants._
import assets.IncomeSourceIntegrationTestConstants._
import assets.ReportDeadlinesIntegrationTestConstants.multipleReportDeadlinesDataSuccessModel
import assets.messages.{BusinessDetailsMessages => messages}
import helpers.servicemocks.IncomeTaxViewChangeStub
import helpers.ComponentSpecBase
import play.api.http.Status.{INTERNAL_SERVER_ERROR, OK, SEE_OTHER}
import utils.ImplicitDateFormatter._

class BusinessDetailsControllerISpec extends ComponentSpecBase {

  "Calling the BusinessDetailsController.getBusinessDetails" when {

    "isAuthorisedUser with an active enrolment and has at least 1 business" should {

      "return the correct page with a valid total" in {

        And("I wiremock stub a successful Income Source Details response with Business and Property income")
        IncomeTaxViewChangeStub.stubGetIncomeSourceDetailsResponse(testMtditid)(OK, singleBusinessResponse)

        When("I call GET /report-quarterly/income-and-expenses/view/business-details")
        val res = IncomeTaxViewChangeFrontend.getBusinessDetails(0)

        verifyIncomeSourceDetailsCall(testMtditid)

        Then("the view displays the correct title, username and links")
        res should have(
          httpStatus(OK),
          pageTitle("business"),
          elementTextByID(id = "reporting-period")(messages.reportingPeriod(b1AccountingStart,b1AccountingEnd)),
          elementTextByID(id = "cessation-date")(messages.cessationDate(b1CessationDate)),
          elementTextByID(id = "address-details")(messages.addressDetails),
          elementTextByID(id = "trading-name")(messages.tradingName),
          elementTextByID(id = "trading-name-business")(b1TradingName),
          elementTextByID(id = "business-address")(messages.businessAddress),
          elementTextByID(id = "address-line-1")(b1AddressLine1),
          elementTextByID(id = "address-line-2")(b1AddressLine2),
          elementTextByID(id = "address-line-3")(b1AddressLine3),
          elementTextByID(id = "address-line-4")(b1AddressLine4),
          elementTextByID(id = "address-line-5")(b1AddressLine5),
          elementTextByID(id = "additional-information")(messages.additionalInformation),
          elementTextByID(id = "accounting-method")(messages.accountingMethod)
        )
      }
    }

    "isAuthorisedUser with an active enrolment, but has no business" should {

      "return an internal server error" in {

        And("I wiremock stub a successful Income Source Details response with Property Only income")
        IncomeTaxViewChangeStub.stubGetIncomeSourceDetailsResponse(testMtditid)(OK, propertyOnlyResponse)

        When("I call GET /report-quarterly/income-and-expenses/view/business-details")
        val res = IncomeTaxViewChangeFrontend.getBusinessDetails(0)

        verifyIncomeSourceDetailsCall(testMtditid)

        Then("an ISE is displayed")
        res should have(
          httpStatus(INTERNAL_SERVER_ERROR)
        )
      }
    }

    "isAuthorisedUser with an active enrolment, but the api returns an error response" should {

      "return an internal server error" in {

        And("I wiremock stub a successful Income Source Details response with single Business income")
        IncomeTaxViewChangeStub.stubGetIncomeSourceDetailsResponse(testMtditid)(INTERNAL_SERVER_ERROR, errorResponse)

        When("I call GET /report-quarterly/income-and-expenses/view/business-details")
        val res = IncomeTaxViewChangeFrontend.getBusinessDetails(0)

        verifyIncomeSourceDetailsCall(testMtditid)

        Then("an ISE is displayed")
        res should have(
          httpStatus(INTERNAL_SERVER_ERROR)
        )
      }
    }

    unauthorisedTest("/account-details/0")
  }
}
