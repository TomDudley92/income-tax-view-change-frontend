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

package controllers

import javax.inject.{Inject, Singleton}

import config.{FrontendAppConfig, ItvcErrorHandler}
import controllers.predicates.{AuthenticationPredicate, IncomeSourceDetailsPredicate, NinoPredicate, SessionTimeoutPredicate}
import play.api.i18n.MessagesApi
import play.api.mvc.{Action, AnyContent}
import services.IncomeSourceDetailsService
import scala.concurrent.Future

@Singleton
class BusinessDetailsController @Inject()(implicit val config: FrontendAppConfig,
                                          implicit val messagesApi: MessagesApi,
                                          val checkSessionTimeout: SessionTimeoutPredicate,
                                          val authenticate: AuthenticationPredicate,
                                          val retrieveNino: NinoPredicate,
                                          val retrieveIncomeSources: IncomeSourceDetailsPredicate,
                                          val incomeSourceDetailsService: IncomeSourceDetailsService,
                                          val itvcErrorHandler: ItvcErrorHandler
                                        ) extends BaseController {

  val getBusinessDetails: Int => Action[AnyContent]= id =>
    (checkSessionTimeout andThen authenticate andThen retrieveNino andThen retrieveIncomeSources).async {

      implicit user =>
        if (config.features.accountDetailsEnabled()) {
          incomeSourceDetailsService.getBusinessDetails(user.nino, id).map {
            case Right(Some((bizDeets, _))) => Ok(views.html.businessDetailsView(bizDeets))
            case _ => itvcErrorHandler.showInternalServerError
          }
        } else {
          Future.successful(Redirect(controllers.routes.HomeController.home()))
        }

    }

}
