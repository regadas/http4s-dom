/*
 * Copyright 2021 http4s.org
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

package org.http4s.dom

import cats.effect.IO
import cats.effect.unsafe.implicits._
import org.scalajs.dom.ExtendableEvent
import org.scalajs.dom.ServiceWorkerGlobalScope

object TestServiceWorker {

  def main(args: Array[String]): Unit = {

    ServiceWorkerGlobalScope
      .self
      .addEventListener[ExtendableEvent](
        "activate",
        (event: ExtendableEvent) =>
          event.waitUntil(
            ServiceWorkerGlobalScope.self.clients.claim()
          )
      )

    ServiceWorker.addFetchEventListener(IO.pure(TestRoutes.routes)).void.unsafeRunSync()
  }

}
