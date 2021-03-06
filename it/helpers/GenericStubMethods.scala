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
package helpers

import assets.BaseIntegrationTestConstants.{testNino,testMtditid}
import helpers.servicemocks._
import play.api.libs.json.{JsNull, JsValue}

trait GenericStubMethods extends CustomMatchers {

  def isAuthorisedUser(bool: Boolean): Unit = {
    if(bool){
      Given("I wiremock stub an isAuthorisedUser user response")
      AuthStub.stubAuthorised()
    } else {
      Given("I wiremock stub an unatuhorised user response")
      AuthStub.stubUnauthorised()
    }
  }

  def stubUserDetails(): Unit = {
    And("I wiremock stub a response from the User Details service")
    UserDetailsStub.stubGetUserDetails()
  }

  def stubUserDetailsError(): Unit= {
    And("I wiremock stub a Error Response from the User Details service")
    UserDetailsStub.stubGetUserDetailsError()
  }

  def verifyIncomeSourceDetailsCall(mtditid: String): Unit = {
    Then(s"Verify that Income Source Details has been called for MTDITID = $mtditid")
    IncomeTaxViewChangeStub.verifyGetIncomeSourceDetails(mtditid)
  }

  def verifyReportDeadlinesCall(nino: String, incomeSourceIds: String*): Unit = {
    for(incomeSourceId <- incomeSourceIds) {
      Then(s"Verify that Report Deadlines has been called for incomeSourceID = $incomeSourceId")
      IncomeTaxViewChangeStub.verifyGetReportDeadlines(incomeSourceId, nino)
    }
  }

  def verifyFinancialTransactionsCall(mtditid: String): Unit = {
    Then("Verify that Financial Transactions has been called")
    FinancialTransactionsStub.verifyGetFinancialTransactions(mtditid)
  }

  def verifyLastTaxCalculationCall(nino: String, taxYear: String): Unit = {
    Then(s"Verify that Last Tax Calculation has been called for NINO = $nino and CalcID = $taxYear")
    IncomeTaxViewChangeStub.verifyGetLastTaxCalc(nino, taxYear)
  }

  def verifyCalculationDataCall(nino: String, calcId: String): Unit = {
    Then(s"Verify that Calculation Data has been called for NINO = $nino and CalcID = $calcId")
    SelfAssessmentStub.verifyGetCalcData(nino, calcId)
  }

}
