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
package org.xwiki.contrib.cristal.integration.resource.internal;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.xwiki.component.annotation.Component;
import org.xwiki.container.Container;
import org.xwiki.context.Execution;
import org.xwiki.context.ExecutionContext;
import org.xwiki.contrib.cristal.integration.resource.CristalResourceReference;
import org.xwiki.resource.AbstractResourceReferenceHandler;
import org.xwiki.resource.ResourceReference;
import org.xwiki.resource.ResourceReferenceHandlerChain;
import org.xwiki.resource.ResourceReferenceHandlerException;
import org.xwiki.resource.ResourceType;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiContextInitializer;
import com.xpn.xwiki.web.Utils;

/**
 * Resource handler to interact with internal Cristal deployment.
 *
 * @version $Id$
 * @since 1.1.0
 */
@Component
@Named("cristal")
@Singleton
public class CristalResourceReferenceHandler extends AbstractResourceReferenceHandler<ResourceType>
{
    @Inject
    private XWikiContextInitializer xWikiContextInitializer;

    @Inject
    private Container container;

    @Inject
    private Execution execution;

    @Override
    public List<ResourceType> getSupportedResourceReferences()
    {
        return List.of(CristalResourceReference.TYPE);
    }

    @Override
    public void handle(ResourceReference reference, ResourceReferenceHandlerChain chain)
        throws ResourceReferenceHandlerException
    {
        String templateName = "cristal";

        ExecutionContext executionContext = this.execution.getContext();
        if (executionContext == null) {
            executionContext = new ExecutionContext();
        }

        try {
            XWikiContext context = this.xWikiContextInitializer.initialize(executionContext);
            Utils.parseTemplate(templateName, true, context);
        } catch (Exception e) {
            throw new ResourceReferenceHandlerException(
                String.format("Error while rendering template [%s]: [%s].",
                    templateName, ExceptionUtils.getRootCauseMessage(e)), e);
        }

        chain.handleNext(reference);
    }
}
