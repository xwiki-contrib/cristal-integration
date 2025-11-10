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

import { ComponentInit as XWikiRouterComponentInit } from "./components/router";
import { ComponentInit as XWikiSwitchComponentInit } from "./ui/XWikiSwitchUIExtension";
import { CristalAppLoader, defaultComponentsList } from "@xwiki/cristal-lib";
import { ComponentInit as AuthenticationXWikiComponentInit } from "@xwiki/cristal-authentication-xwiki";
import { ComponentInit as BrowserComponentInit } from "@xwiki/cristal-browser-default";
import { ComponentInit as VueDSComponentInit } from "@xwiki/cristal-dsvuetify";
import { ComponentInit as XWikiPageHierarchyComponentInit } from "@xwiki/cristal-hierarchy-xwiki";
import { ComponentInit as XWikiPageHistoryComponentInit } from "@xwiki/cristal-history-xwiki";
import { ComponentInit as XWikiLinkSuggestComponentInit } from "@xwiki/cristal-link-suggest-xwiki";
import { ComponentInit as ModelReferenceXWikiComponentInit } from "@xwiki/cristal-model-reference-xwiki";
import { ComponentInit as ModelRemoteURLXWikiComponentInit } from "@xwiki/cristal-model-remote-url-xwiki";
import { ComponentInit as XWikiNavigationTreeComponentInit } from "@xwiki/cristal-navigation-tree-xwiki";
import { ComponentInit as BrowserSettingsComponentInit } from "@xwiki/cristal-settings-browser";

CristalAppLoader.init(
  [
    "skin",
    "dsvuetify",
    "macros",
    "storage",
    "extension-menubuttons",
    "sharedworker",
  ],
  async () => ({
    "xwiki": {
      name: "xwiki",
      configType: "XWiki",
      serverRendering: false,
      offline: false,
      baseURL: `${window.location.origin}/xwiki`,
      baseRestURL: "/rest/cristal/page?media=json",
      homePage: "Main.WebHome",
      designSystem: "vuetify",
      editor: "blocknote"
    },
  }),
  true,
  false,
  "xwiki",
  async (container) => {
    await defaultComponentsList(container);
    new BrowserComponentInit(container);
    new AuthenticationXWikiComponentInit(container);
    new BrowserSettingsComponentInit(container);
    new XWikiRouterComponentInit(container);
    new XWikiSwitchComponentInit(container);
  },
  async (container) => {
    new VueDSComponentInit(container);

    new XWikiLinkSuggestComponentInit(container);
    new XWikiPageHierarchyComponentInit(container);
    new XWikiNavigationTreeComponentInit(container);
    new XWikiPageHistoryComponentInit(container);
    new ModelRemoteURLXWikiComponentInit(container);
    new ModelReferenceXWikiComponentInit(container);

    const { ComponentInit } = await import("@xwiki/cristal-editors-blocknote");
    new ComponentInit(container);
  },
);
