/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.contrib.cristal.integration.headless.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.component.phase.Initializable;
import org.xwiki.component.phase.InitializationException;
import org.xwiki.security.authservice.internal.AuthServiceConfiguration;

import com.xpn.xwiki.XWikiException;

/**
 * Initialization component for external Cristal instances.
 *
 * @version $Id$
 */
@Component
@Named("CristalHeadlessInitializer")
@Singleton
public class CristalHeadlessInitializer implements Initializable
{
    @Inject
    private AuthServiceConfiguration authServiceConfiguration;

    @Override
    public void initialize() throws InitializationException
    {
        String oidcAuthServiceId = "oidc-provider-bridge";

        // Check that the current auth service is OIDC, and change it otherwise.
        try {
            if (!this.authServiceConfiguration.getAuthService().equals(oidcAuthServiceId)) {
                this.authServiceConfiguration.setAuthServiceId(oidcAuthServiceId);
            }
        } catch (XWikiException e) {
            throw new InitializationException("Error when trying to set auth service to OIDC.", e);
        }
    }
}
