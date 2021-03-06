/*
 * Copyright (c) 2019, Okta, Inc. and/or its affiliates. All rights reserved.
 * The Okta software accompanied by this notice is provided pursuant to the Apache License,
 * Version 2.0 (the "License.")
 *
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and limitations under the
 * License.
 */
package com.okta.oidc.util;

import com.okta.oidc.storage.Persistable;

public class PersistableMock implements Persistable {
    private String mData;

    PersistableMock(String data) {
        this.mData = data;
    }

    public String getData() {
        return mData;
    }

    @Override
    public String getKey() {
        return RESTORE.getKey();
    }

    @Override
    public String persist() {
        return this.mData;
    }

    public static final Persistable.Restore<PersistableMock> RESTORE = new Persistable.Restore<PersistableMock>() {
        private final String KEY = "PersistableMock";

        public String getKey() {
            return KEY;
        }

        @Override
        public PersistableMock restore(String data) {
            if (data != null) {
                return new PersistableMock(data);
            }
            return null;
        }
    };
}
