/*
 * Copyright (c) 2018 Uber Technologies, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package motif.daggercomparison.motif;

public class RootController {

    private final RootScope scope;
    private final RootView view;

    public RootController(RootScope scope, RootView view) {
        this.scope = scope;
        this.view = view;
    }

    public void onStart() {
        LoggedInController controller = scope.loggedIn(view).controller();
        controller.onStart();
    }
}