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
package org.xwiki.contrib.cristal.integration.test.ui.docker;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.xwiki.test.docker.junit5.TestReference;
import org.xwiki.test.docker.junit5.UITest;
import org.xwiki.test.ui.TestUtils;
import org.xwiki.test.ui.po.DrawerMenu;
import org.xwiki.test.ui.po.ViewPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Validate behaviors of Cristal integration in XWiki's drawer.
 *
 * @since 1.5.0
 * @version $Id$
 */
@UITest
class DrawerExtensionIT
{
    @Test
    void switchToCristal(TestUtils setup, TestReference reference)
    {
        ViewPage testPage = setup.createPage(reference, "Cristal page content!", "New Cristal Page");

        DrawerMenu drawer = testPage.getDrawerMenu();
        drawer.toggle();
        drawer.clickEntry("Switch to Cristal");

        WebElement cristalRoot = setup.getDriver().findElement(By.id("xwCristalApp"));
        WebElement docTitle = cristalRoot.findElement(By.className("doc-title"));
        WebElement docContent = cristalRoot.findElement(By.className("doc-content"));

        assertEquals("New Cristal Page", docTitle.getText());
        assertEquals("Cristal page content!", docContent.getText());
    }
}
