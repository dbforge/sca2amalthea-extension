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

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.SAXXMLHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


public class SCAXMLHandler extends SAXXMLHandler {


  private static final String PROJECT = "Project";

  /**
   * @param xmlResource
   * @param helper
   * @param options
   */
  public SCAXMLHandler(final XMLResource xmlResource, final XMLHelper helper, final Map<?, ?> options) {
    super(xmlResource, helper, options);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void startElement(final String uri, final String localName, final String qName, final Attributes attributes)
      throws SAXException {
    setAttributes(attributes);
    if (!qName.endsWith("s") && !qName.contains(PROJECT) && !qName.endsWith("me") && !qName.endsWith("seq") && !qName.endsWith("entry")) {
      startElement(uri, localName + "s", qName + "s");
    }
    else if (qName.contains(PROJECT) || qName.endsWith("me") || qName.endsWith("el") || qName.endsWith("seq") || qName.endsWith("entry")) {
      startElement(uri, localName, qName);
    }
  }

  @Override
  public void endElement(final String uri, final String localName, final String name) {

    if (name.endsWith("s") || name.contains(PROJECT)) {
      return;
    }

    this.elements.pop();
    Object type = this.types.pop();
    if (type.equals(OBJECT_TYPE)) {
      if (this.text == null) {
        this.objects.pop();
        this.mixedTargets.pop();
      }
      else {
        EObject object = this.objects.popEObject();
        if ((this.mixedTargets.peek() != null) && ((object.eContainer() != null) || this.suppressDocumentRoot ||
            (this.recordUnknownFeature && (this.eObjectToExtensionMap.containsValue(object) ||
                (((InternalEObject) object).eDirectResource() != null))))) {
          handleMixedText();
          this.mixedTargets.pop();
        }
        else {
          if (this.text.length() != 0) {
            handleProxy((InternalEObject) object, this.text.toString().trim());
          }
          this.mixedTargets.pop();
          this.text = null;
        }
      }
    }
    else if (this.isIDREF) {
      this.objects.pop();
      this.mixedTargets.pop();
      if (this.text != null) {
        setValueFromId(this.objects.peekEObject(), (EReference) type, this.text.toString());
        this.text = null;
      }
      this.isIDREF = false;
    }
    else if (isTextFeatureValue(type)) {
      EObject eObject = this.objects.popEObject();
      this.mixedTargets.pop();
      if (eObject == null) {
        eObject = this.objects.peekEObject();
      }
      setFeatureValue(eObject, (EStructuralFeature) type, this.text == null ? null : this.text.toString());
      this.text = null;
    }

    if (this.isSimpleFeature) {
      this.types.pop();
      this.objects.pop();
      this.mixedTargets.pop();
      this.isSimpleFeature = false;
    }
    this.helper.popContext(this.prefixesToFactories);
  }
}
