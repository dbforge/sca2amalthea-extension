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
package org.eclipse.app4mc.sca2amalthea.llvm.srcfiles.support;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.app4mc.sca.logging.manager.Logmanager;
import org.eclipse.app4mc.sca2amalthea.llvm.Activator;


/**
 */
public class PathsToListConverter implements ISourceFileListProvider {

  private final String pathsList;
  private final String fileExtension;

  /**
   * @param pathsList {@link String}
   * @param fileExtension {@link String}
   */
  public PathsToListConverter(final String pathsList, final String fileExtension) {
    super();
    this.pathsList = pathsList;
    this.fileExtension = fileExtension;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> generateSourceFileList() {
    List<String> filesList = new ArrayList<String>();
    String[] pathList = this.pathsList.split(File.pathSeparator);
    for (String pathStr : pathList) {
      try (Stream<Path> stream = Files.walk(Paths.get(pathStr))) {
        List<Path> fileList = stream.filter(x -> Files.isRegularFile(x))
            .filter(x -> x.toString().endsWith(this.fileExtension)).collect((Collectors.toList()));
        for (Path path : fileList) {
          filesList.add(path.toFile().getAbsolutePath());
        }
      }
      catch (IOException e) {
        Logmanager.getInstance().logException(this.getClass(), e, Activator.PLUGIN_ID);
        return filesList;
      }

    }
    return filesList;
  }

  /**
   * @return the pathsList
   */
  public String getPathsList() {
    return this.pathsList;
  }

  /**
   * @return the fileExtension
   */
  public String getFileExtension() {
    return this.fileExtension;
  }

  /**
   * @param path {@link String}
   * @return {@link List}
   * @throws IOException Exception if there is an io related exception
   */
  public static List<String> getListFromFile(final String path) throws IOException {
    FileReader fileReader;
    BufferedReader bufferedReader;

    fileReader = new FileReader(path);
    bufferedReader = new BufferedReader(fileReader);

    String s;
    List<String> list = new ArrayList<String>();

    while ((s = bufferedReader.readLine()) != null) {
      list.add(s);
    }
    fileReader.close();
    bufferedReader.close();
    return list;
  }

}
