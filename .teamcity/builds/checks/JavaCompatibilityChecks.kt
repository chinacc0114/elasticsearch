/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package builds.checks

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle

val jdks = listOf("java11", "openjdk14", "openjdk15", "zulu11", "corretto11", "adoptopenjdk11")

val javaCompatibilityChecks = jdks.map { jdk ->
    BuildType {
        id("JavaCompatibilityCheck_${jdk.toUpperCase()}")
        name = jdk
        description = "Java compatibility testing for \"${jdk}\" JDK"

        params {
            param("env.RUNTIME_JAVA_HOME", "/var/lib/jenkins/.java/${jdk}")
        }

        steps {
            gradle {
                useGradleWrapper = true
                gradleParams = "%gradle.params%"
                tasks = "check"
            }
        }
    }
}
