/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ambari.server.state;

import java.util.List;
import java.util.Map;

import org.apache.ambari.server.AmbariException;
import org.apache.ambari.server.controller.ClusterResponse;

public interface Cluster {

  /**
   * Get the cluster ID
   */
  public long getClusterId();

  /**
   * Get the Cluster Name
   */
  public String getClusterName();

  /**
   * Set the Cluster Name
   */
  public void setClusterName(String clusterName);

  /**
   * Add a service to a cluster
   * @param service
   */
  public void addService(Service service) throws AmbariException;

  /**
   * Get a service
   * @param serviceName
   * @return
   */
  public Service getService(String serviceName) throws AmbariException;

  /**
   * Get all services
   * @return
   */
  public Map<String, Service> getServices();

  /**
   * Get all ServiceComponentHosts on a given host
   * @param hostname
   * @return
   */
  public List<ServiceComponentHost> getServiceComponentHosts(String hostname);

  /**
   * Get Stack Version
   * @return
   */
  public StackVersion getDesiredStackVersion();

  /**
   * Set stack version
   * @param stackVersion
   */
  public void setDesiredStackVersion(StackVersion stackVersion);

  public Map<String, Config> getConfigsByType(String configType);

  public Config getConfig(String configType, String versionTag);

  public void addConfig(Config config);

  public ClusterResponse convertToResponse();

  public void refresh();

  public void debugDump(StringBuilder sb);

  Service addService(String serviceName) throws AmbariException;
}
