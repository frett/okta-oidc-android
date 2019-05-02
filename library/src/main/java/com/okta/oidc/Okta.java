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

package com.okta.oidc;

import androidx.annotation.ColorInt;

import com.okta.oidc.clients.AuthClient;
import com.okta.oidc.clients.AuthClientFactory;
import com.okta.oidc.clients.AuthClientFactoryImpl;
import com.okta.oidc.clients.SyncAuthClient;
import com.okta.oidc.clients.SyncAuthClientFactoryImpl;
import com.okta.oidc.clients.web.SyncWebAuthClient;
import com.okta.oidc.clients.web.SyncWebAuthClientFactory;
import com.okta.oidc.clients.web.WebAuthClient;
import com.okta.oidc.clients.web.WebAuthClientFactory;

import java.util.concurrent.Executor;

/**
 * A collection of builders for creating different type of authentication clients.
 * {@link WebAuthClient}
 * {@link AuthClient}
 * {@link SyncAuthClient}
 */
public class Okta {
    /**
     * The asynchronous web authentication client builder.
     */
    public static class WebAuthBuilder extends OktaBuilder<WebAuthClient, WebAuthBuilder> {
        private Executor mCallbackExecutor;
        private int mCustomTabColor;
        private String[] mSupportedBrowsers;

        /**
         * Sets a executor for use for callbacks. Default behaviour will execute
         * callbacks on the UI thread.
         *
         * @param executor custom executor
         * @return current builder
         */
        public WebAuthBuilder withCallbackExecutor(Executor executor) {
            mCallbackExecutor = executor;
            return this;
        }

        /**
         * Sets the color for custom tab.
         *
         * @param customTabColor the custom tab color for the browser
         * @return current builder
         */
        public WebAuthBuilder withTabColor(@ColorInt int customTabColor) {
            mCustomTabColor = customTabColor;
            return this;
        }

        /**
         * Sets the supported browsers to use. The default is Chrome. Can use other
         * custom tab enabled browsers.
         *
         * @param browsers the package name of the browsers.
         * @return current builder
         */
        public WebAuthBuilder supportedBrowsers(String[] browsers) {
            mSupportedBrowsers = browsers;
            return this;
        }

        @Override
        WebAuthBuilder toThis() {
            return this;
        }

        /**
         * Create WebAuthClient client.
         *
         * @return the authenticate client {@link WebAuthClient}
         */
        @Override
        public WebAuthClient create() {
            super.withAuthenticationClientFactory(new WebAuthClientFactory(mCallbackExecutor,
                    mCustomTabColor, mSupportedBrowsers));
            return createAuthClient();
        }
    }

    /**
     * The synchronous web authentication client builder.
     */
    public static class SyncWebAuthBuilder extends
            OktaBuilder<SyncWebAuthClient, SyncWebAuthBuilder> {
        private int mCustomTabColor;
        private String[] mSupportedBrowsers;

        /**
         * Sets the color for custom tab.
         *
         * @param customTabColor the custom tab color for the browser
         * @return current builder
         */
        public SyncWebAuthBuilder withTabColor(@ColorInt int customTabColor) {
            mCustomTabColor = customTabColor;
            return this;
        }

        /**
         * Sets the supported browsers to use. The default is Chrome. Can use other
         * custom tab enabled browsers.
         *
         * @param browsers the package name of the browsers.
         * @return current builder
         */
        public SyncWebAuthBuilder supportedBrowsers(String... browsers) {
            mSupportedBrowsers = browsers;
            return this;
        }

        @Override
        SyncWebAuthBuilder toThis() {
            return this;
        }

        /**
         * Create SyncWebAuthClient client.
         *
         * @return the authenticate client {@link SyncWebAuthClient}
         */
        @Override
        public SyncWebAuthClient create() {
            super.withAuthenticationClientFactory(
                    new SyncWebAuthClientFactory(mCustomTabColor, mSupportedBrowsers));
            return createAuthClient();
        }
    }

    /**
     * The asynchronous authentication client builder using sessionTokens.
     */
    public static class AuthBuilder extends OktaBuilder<AuthClient, AuthBuilder> {
        private Executor mCallbackExecutor;

        /**
         * Sets a executor for use for callbacks. Default behaviour will execute
         * callbacks on the UI thread.
         *
         * @param executor custom executor
         * @return current builder
         */
        public AuthBuilder withCallbackExecutor(Executor executor) {
            mCallbackExecutor = executor;
            return toThis();
        }

        @Override
        AuthBuilder toThis() {
            return this;
        }

        /**
         * Create AuthClient.
         *
         * @return the authenticate client {@link AuthClient}
         */
        @Override
        public AuthClient create() {
            super.withAuthenticationClientFactory(
                    new AuthClientFactoryImpl(this.mCallbackExecutor));
            return createAuthClient();
        }
    }

    /**
     * The synchronous authentication client builder using sessionTokens.
     */
    public static class SyncAuthBuilder extends OktaBuilder<SyncAuthClient, SyncAuthBuilder> {

        @Override
        SyncAuthBuilder toThis() {
            return this;
        }

        /**
         * Create SyncAuthClient.
         *
         * @return the authenticate client {@link SyncAuthClient}
         */
        @Override
        public SyncAuthClient create() {
            super.withAuthenticationClientFactory(new SyncAuthClientFactoryImpl());
            return createAuthClient();
        }
    }


    /**
     * A generic type Builder.
     *
     * @param <A> the generic type parameter
     */
    public static class Builder<A> extends OktaBuilder<A, Builder<A>> {
        /**
         * Constructor.
         */
        public Builder() {
        }

        @Override
        public A create() {
            return createAuthClient();
        }


        @Override
        public Builder<A> withAuthenticationClientFactory(AuthClientFactory<A> authClientFactory) {
            return super.withAuthenticationClientFactory(authClientFactory);
        }

        @Override
        Builder<A> toThis() {
            return this;
        }
    }
}