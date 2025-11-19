/**
 * See the LICENSE file distributed with this work for additional
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

import { inject, injectable } from "inversify";
import type { CristalApp } from "@xwiki/cristal-api";
import type {
  AuthenticationManager,
  UserDetails,
} from "@xwiki/cristal-authentication-api";
import type { Container } from "inversify";

/**
 * Custom authentication manager to handle authentication through XWiki.
 * @since 1.2.0
 */
@injectable()
class XWikiAuthenticationManager implements AuthenticationManager {
  constructor(@inject("CristalApp") private readonly cristalApp: CristalApp) {}

  async start(): Promise<void> {
    const loginUrl = new URL(
      `${this.cristalApp.getWikiConfig().baseURL}/bin/login/XWiki/XWikiLogin`
    );
    loginUrl.searchParams.set("xredirect", window.location.href);
    window.location.href = loginUrl.toString();
  }

  async callback(): Promise<void> {
    console.warn("No callback registered for basic XWiki auth.");
  }

  async getUserDetails(): Promise<UserDetails> {
    // TODO: Support user details (CRISTALINT-18).
    throw "User details are not supported yet.";
  }

  async logout(): Promise<void> {
    await fetch(
      `${this.cristalApp.getWikiConfig().baseURL}/bin/logout/XWiki/XWikiLogout`
    );
  }

  async getAuthorizationHeader(): Promise<string | undefined> {
    return undefined;
  }

  async isAuthenticated(): Promise<boolean> {
    const loginRequest = await fetch(
      `${this.cristalApp.getWikiConfig().baseURL}/bin/login/XWiki/XWikiLogin`
    );
    return loginRequest.redirected;
  }
}

export class ComponentInit {
  constructor(container: Container) {
    container
      .bind<AuthenticationManager>("AuthenticationManager")
      .to(XWikiAuthenticationManager)
      .inSingletonScope()
      .whenNamed("XWiki");
  }
}

