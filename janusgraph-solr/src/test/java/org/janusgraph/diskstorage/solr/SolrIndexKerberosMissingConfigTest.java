// Copyright 2018 JanusGraph Authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.janusgraph.diskstorage.solr;

import org.janusgraph.diskstorage.PermanentBackendException;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
public class SolrIndexKerberosMissingConfigTest {

    @Container
    protected static JanusGraphSolrContainer solrContainer = new JanusGraphSolrContainer();

	/**
	 * This test needs to stand alone because the java.security.auth.login.config
	 * property is JVM wide and is required for the KDC to run.
     */
	@Test
	public void testIndexInitializationFailsWhenMissingJavaSecurityAuthLoginConfig() {
        System.clearProperty("java.security.auth.login.config");
        assertThrows(PermanentBackendException.class, () -> new SolrIndex(solrContainer.getLocalSolrTestConfigOverwriteKerberos()));
    }
}
