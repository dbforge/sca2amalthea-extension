/**
 ********************************************************************************
 * Copyright (c) 2017-2020 Robert Bosch GmbH and others.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Robert Bosch GmbH - initial API and implementation
 ********************************************************************************
 */
package org.eclipse.app4mc.sca2amalthea.serialization;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.impl.XMLLoadImpl;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SCAXMLLoadImpl extends XMLLoadImpl {

  /**
   * @param helper
   */
  public SCAXMLLoadImpl(final XMLHelper helper) {
    super(helper);
    // TODO Auto-generated constructor stub
  }


  @Override
  protected DefaultHandler makeDefaultHandler() {
    SCAXMLHandler handler = new SCAXMLHandler(this.resource, this.helper, this.options);

    return handler;
  }

  @Override
  protected SAXParser makeParser() throws ParserConfigurationException, SAXException {
    // Create an instance of org.apache.xerces.parsers.SAXParser
    /*
     * !! Important Note !! We must override makeParser() - even if we wouldn't have any functional changes to apply -
     * in order to make sure that SAXParserFactory.newInstance() gets invoked from this plug-in which has a dependency
     * to the org.apache.xerces plug-in and all its classes on the classpath. Otherwise we wouldn't obtain an instance
     * of org.apache.xerces.jaxp.SAXParserFactoryImpl as intended but fall back to the default implementation
     * com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl.
     */
    SAXParserFactory factory = SAXParserFactory.newInstance();
    return factory.newSAXParser();
  }

  @Override
  protected void handleErrors() throws IOException {
    // avoid throwing exception even if errors occur during load
  }

}
