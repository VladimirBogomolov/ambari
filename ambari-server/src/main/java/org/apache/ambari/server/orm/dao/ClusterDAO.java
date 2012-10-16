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

package org.apache.ambari.server.orm.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import org.apache.ambari.server.orm.entities.ClusterEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.persistence.jpa.JpaEntityManager;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

public class ClusterDAO {
  private static final Log log = LogFactory.getLog(ClusterDAO.class);

  @Inject
  Provider<EntityManager> entityManagerProvider;

  /**
   * Looks for Cluster by ID
   * @param id ID of Cluster
   * @return Found entity or NULL
   */
  public ClusterEntity findById(long id) {
    return entityManagerProvider.get().find(ClusterEntity.class, id);
  }

  public ClusterEntity findByName(String clusterName) {
    TypedQuery<ClusterEntity> query = entityManagerProvider.get().createNamedQuery("clusterByName", ClusterEntity.class);
    query.setParameter("clusterName", clusterName);
    try {
      return query.getSingleResult();
    } catch (NoResultException ignored) {
      return null;
    }
  }

  public List<ClusterEntity> findAll() {
    TypedQuery<ClusterEntity> query = entityManagerProvider.get().createNamedQuery("allClusters", ClusterEntity.class);
    try {
      return query.getResultList();
    } catch (NoResultException ignored) {
    }
    return null;
  }

  /**
   * Create Cluster entity in Database
   * @param clusterEntity entity to create
   */
  @Transactional
  public void create(ClusterEntity clusterEntity) {
    entityManagerProvider.get().persist(clusterEntity);
  }

  /**
   * Retrieve entity data from DB
   * @param clusterEntity entity to refresh
   */
  public void refresh(ClusterEntity clusterEntity) {
    entityManagerProvider.get().refresh(clusterEntity);
  }

  @Transactional
  public ClusterEntity merge(ClusterEntity clusterEntity) {
    return entityManagerProvider.get().merge(clusterEntity);
  }

  @Transactional
  public void remove(ClusterEntity clusterEntity) {
    entityManagerProvider.get().remove(clusterEntity);
  }

  @Transactional
  public void removeByName(String clusterName) {
    remove(findByName(clusterName));
  }

  @Transactional
  public void rename(ClusterEntity clusterEntity, String newName) {
    entityManagerProvider.get().unwrap(JpaEntityManager.class).getUnitOfWork().setShouldPerformDeletesFirst(true);
    remove(clusterEntity);
    entityManagerProvider.get().detach(clusterEntity);
    clusterEntity.setClusterName(newName);
    create(clusterEntity);
  }

}
