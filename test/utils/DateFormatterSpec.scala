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

package utils

import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalDateTime}

class DateFormatterSpec extends TestSupport with ImplicitDateFormatter {

  "The implicit date formatter" should {

    "format string dates" in {
      "2017-04-01".toLocalDate shouldBe LocalDate.of(2017, 4, 1)
    }

    "format months with single digit values" in {
      "2017-6-30".toLocalDate shouldBe LocalDate.of(2017, 6, 30)
    }

    "format days with single digit values" in {
      "2017-6-1".toLocalDate shouldBe LocalDate.of(2017, 6, 1)
    }

    "format string DateTimes" in {
      "2017-04-01T11:23:45.123Z".toLocalDateTime shouldBe LocalDateTime.parse("2017-04-01T11:23:45.123Z", DateTimeFormatter.ISO_DATE_TIME)
    }
  }

  "The implicit date formatter" should {
    "change localDate's to full dates" in {
      "2017-04-01".toLocalDate.toLongDate shouldBe "1 April 2017"
    }

    "change LocalDateTime to long date output" in {
      "2017-04-01T11:23:45.123Z".toLocalDateTime.toLongDateTime shouldBe "1 April 2017"
    }
  }
}
