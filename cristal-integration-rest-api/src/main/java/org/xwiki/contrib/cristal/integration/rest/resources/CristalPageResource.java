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
package org.xwiki.contrib.cristal.integration.rest.resources;

import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.xwiki.rest.XWikiRestException;

/**
 * Provides wiki resources to a Cristal client.
 *
 * @since 1.0.0
 * @version $Id$
 */
@Path("/cristal/wikis/{wikiName}/spaces/{spaceName: .+}/pages/{pageName}")
public interface CristalPageResource
{
    /**
     * Returns a page resource.
     *
     * @param wikiName - the name of the wiki containing the page
     * @param spaceName - the space that stores the page
     * @param pageName - the name of the page
     * @param format - the format expected for the response (either "jsonld", or empty to retrieve the resource as-is)
     * @param revision - the page revision to request (default: latest available)
     * @return the page resource
     */
    @GET Response getPage(
        @PathParam("wikiName") String wikiName,
        @PathParam("spaceName") @Encoded String spaceName,
        @PathParam("pageName") String pageName,
        @QueryParam("format") String format,
        @QueryParam("revision") String revision
    ) throws XWikiRestException;
}
