/*
 * Zed Attack Proxy (ZAP) and its related class files.
 *
 * ZAP is an HTTP/HTTPS proxy for assessing web application security.
 *
 * Copyright 2023 The ZAP Development Team
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
package org.zaproxy.addon.dev.auth.passwordAddedJson;

import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.parosproxy.paros.network.HttpHeader;
import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.addon.dev.TestPage;
import org.zaproxy.addon.dev.TestProxyServer;
import org.zaproxy.addon.network.server.HttpMessageHandlerContext;

public class PasswordAddedJsonVerificationPage extends TestPage {

    private static final Logger LOGGER =
            LogManager.getLogger(PasswordAddedJsonVerificationPage.class);

    public PasswordAddedJsonVerificationPage(TestProxyServer server) {
        super(server, "user");
    }

    @Override
    public void handleMessage(HttpMessageHandlerContext ctx, HttpMessage msg) {
        String token = msg.getRequestHeader().getHeader(HttpHeader.AUTHORIZATION);
        String user = getParent().getUser(token);
        LOGGER.debug("Token: {} user: {}", token, user);

        JSONObject response = new JSONObject();
        if (user != null) {
            response.put("result", "OK");
            response.put("user", user);
        } else {
            response.put("result", "FAIL");
        }
        this.getServer().setJsonResponse(response, msg);
    }

    @Override
    public PasswordAddedJsonDir getParent() {
        return (PasswordAddedJsonDir) super.getParent();
    }
}
