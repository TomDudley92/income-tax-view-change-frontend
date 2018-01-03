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

package forms.validation

import forms.validation.models.{FieldError, SummaryError, TargetIds}
import play.api.data.validation.Invalid

object ErrorMessageFactory {

  val FieldErrorLoc = 0
  val SummaryErrorLoc = 1
  val TargetIdsLoc = 2

  def error(errKey: String, errArgs: String*): Invalid = {
    val fieldError = FieldError(errKey, errArgs)
    val summaryError = SummaryError(errKey, errArgs)
    error(fieldError, summaryError)
  }

  def error(fieldError: FieldError, summaryError: SummaryError): Invalid =
    Invalid("", fieldError, summaryError)

}
