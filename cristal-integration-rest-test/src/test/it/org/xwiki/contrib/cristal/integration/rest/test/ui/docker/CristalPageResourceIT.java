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
package org.xwiki.contrib.cristal.integration.rest.test.ui.docker;

import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.jupiter.api.Test;
import org.xwiki.contrib.cristal.integration.rest.resources.CristalPageResource;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.rest.model.jaxb.Page;
import org.xwiki.test.docker.junit5.UITest;
import org.xwiki.test.ui.TestUtils;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Validate behaviors on Cristal's Page REST resource.
 *
 * @version $Id$
 */
@UITest
class CristalPageResourceIT
{
    @Test
    void testPageRevision(TestUtils setup) throws Exception
    {
        // Create page and edit its content.
        DocumentReference testDocument = new DocumentReference("xwiki", "Cristal", "TestPage");
        setup.createPage(testDocument, "Hello World");
        Page testPage = setup.rest().page(testDocument);
        testPage.setContent("Hello Cristal!");
        setup.rest().save(testPage);

        // Fetch first revision of the page.
        GetMethod get = setup.rest().executeGet(CristalPageResource.class, Map.of("format", new String[] {"jsonld"},
            "revision", new String[] {"1.1"}), testDocument);

        assertEquals(HttpStatus.SC_OK, get.getStatusCode());
        JsonObject getResponse = Json.createReader(get.getResponseBodyAsStream()).readObject();

        assertEquals("1.1", getResponse.getString("version"));
        assertEquals("Hello World", getResponse.getString("text"));
        assertEquals("<p>Hello World</p>", getResponse.getString("html"));

        // Fetch last revision of the page.
        get = setup.rest().executeGet(CristalPageResource.class, Map.of("format", new String[] {"jsonld"}),
            testDocument);

        assertEquals(HttpStatus.SC_OK, get.getStatusCode());
        getResponse = Json.createReader(get.getResponseBodyAsStream()).readObject();

        assertEquals("2.1", getResponse.getString("version"));
        assertEquals("Hello Cristal!", getResponse.getString("text"));
        assertEquals("<p>Hello Cristal!</p>", getResponse.getString("html"));
    }
}